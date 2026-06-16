package com.aulkhami.pakupos.app.report;

import com.aulkhami.pakupos.dao.OrderDAO;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.entities.Order;
import java.math.BigDecimal;
import java.util.List;

public class ReportInteractor implements Interactor {
    private final ReportModel model;
    private final OrderDAO orderDAO = new OrderDAO();

    public ReportInteractor(ReportModel model) {
        this.model = model;
    }

    public void loadReportData() {
        BigDecimal totalSales = orderDAO.getTotalSalesToday();
        int totalOrders = orderDAO.getOrderCountToday();
        List<Order> orders = orderDAO.findAll();

        model.setTotalSales(totalSales != null ? totalSales : BigDecimal.ZERO);
        model.setTotalOrders(totalOrders);
        model.getTransactions().setAll(orders);
    }
}
