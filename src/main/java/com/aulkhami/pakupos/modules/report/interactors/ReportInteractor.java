package com.aulkhami.pakupos.modules.report.interactors;

import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.modules.dashboard.services.DashboardService;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.report.models.ReportModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class ReportInteractor extends MenuBarInteractor {

    private final ReportModel model;
    private final DashboardService dashboardService = new DashboardService();

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
    }

    public void loadReportData() {
        // TODO: Implement filterFrom and filterTo order period range
        BigDecimal totalSales = dashboardService.getTotalSalesToday();
        int totalOrders = dashboardService.getOrderCountToday();
        List<OrderResponseDTO> orders = dashboardService.getRecentTransactions(1000);

        model.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);
        model.setTotalOrders(totalOrders);
        model.transactionsProperty().setAll(orders);
    }
}
