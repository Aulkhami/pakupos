package com.aulkhami.pakupos.modules.report.services;

import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private final OrderRepository orderRepository;

    public ReportService() {
        this.orderRepository = new OrderRepository();
    }

    public BigDecimal getTotalSales(LocalDate start, LocalDate end) {
        return orderRepository.getTotalSalesInDateRange(
                Timestamp.valueOf(start.atStartOfDay()),
                Timestamp.valueOf(end.atTime(23, 59)));
    }

    public int getOrderCount(LocalDate start, LocalDate end) {
        return orderRepository.getOrderCountInDateRange(
                Timestamp.valueOf(start.atStartOfDay()),
                Timestamp.valueOf(end.atTime(23, 59)));
    }

    public List<OrderResponseDTO> getTransactions(LocalDate start, LocalDate end) {
        List<Order> orders = orderRepository.findOrdersByDateRange(
                Timestamp.valueOf(start.atStartOfDay()),
                Timestamp.valueOf(end.atTime(23, 59)));
        return orders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }
}
