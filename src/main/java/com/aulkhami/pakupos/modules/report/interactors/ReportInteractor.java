package com.aulkhami.pakupos.modules.report.interactors;

import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import com.aulkhami.pakupos.modules.report.services.ReportService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class ReportInteractor extends MenuBarInteractor {

    private final ReportModel model;
    private final ReportService reportService = new ReportService();

    public ReportInteractor(ReportModel model) {
        this.model = model;
    }

    public void setFilterPeriod(String period) {
        switch (period) {
            case "periodDay": {
                model.filterFromDateProperty().set(LocalDate.now());
                break;
            }
            case "periodWeek": {
                model.filterFromDateProperty().set(LocalDate.now().minusDays(7));
                break;
            }
            case "periodMonth": {
                LocalDate n = LocalDate.now();
                YearMonth ym = YearMonth.of(n.getYear(), n.getMonth());
                model.filterFromDateProperty().set(ym.atDay(1));
                break;
            }
            case "periodYear": {
                LocalDate n = LocalDate.now();
                YearMonth ym = YearMonth.of(n.getYear(), 1);
                model.filterFromDateProperty().set(ym.atDay(1));
                break;
            }
            default:
                throw new AssertionError();
        }

        loadReportData();
    }

    public void loadReportData() {
        LocalDate start = model.filterFromDateProperty().get();
        LocalDate end = model.filterToDateProperty().get();
        BigDecimal totalSales = reportService.getTotalSales(start, end);
        int totalOrders = reportService.getOrderCount(start, end);
        List<OrderResponseDTO> orders = reportService.getTransactions(start, end);
        
        model.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);
        model.setTotalOrders(totalOrders);
        model.transactionsProperty().setAll(orders);
    }
}
