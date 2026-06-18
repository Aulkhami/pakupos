package com.aulkhami.pakupos.modules.pos.repositories;

import com.aulkhami.pakupos.database.DatabaseConnection;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import com.aulkhami.pakupos.app.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    public Order save(Order order) {
        String sql = "INSERT INTO orders (customer_id, customer_name, total_amount, payment_method, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (order.getUserId() != null) {
                ps.setLong(1, order.getUserId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }

            ps.setString(2, order.getCustomerName());
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getPaymentMethod());
            ps.setString(5, order.getStatus() != null ? order.getStatus().name().toLowerCase() : OrderStatus.PENDING.name().toLowerCase());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not save order", e);
        }
        return order;
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("customer_id"));
        order.setCustomerName(rs.getString("customer_name"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setPaymentMethod(rs.getString("payment_method"));
        try {
            order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
        } catch (Exception e) {
            order.setStatus(OrderStatus.COMPLETED);
        }
        order.setCreatedAt(rs.getTimestamp("created_at"));
        return order;
    }

    public List<Order> findOrdersByDateRange(Timestamp start, Timestamp end) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE created_at >= ? AND created_at <= ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> findOrdersByToday() {
        Timestamp start = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDate.now().atTime(23, 59));
        return findOrdersByDateRange(start, end);
    }

    public BigDecimal getTotalSalesInDateRange(Timestamp start, Timestamp end) {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE status = 'completed' AND created_at >= ? AND created_at <= ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal(1);
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalSalesToday() {
        Timestamp start = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDate.now().atTime(23, 59));
        return getTotalSalesInDateRange(start, end);
    }

    public int getOrderCountInDateRange(Timestamp start, Timestamp end) {
        String sql = "SELECT COUNT(*) FROM orders WHERE created_at >= ? AND created_at <= ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getOrderCountToday() {
        Timestamp start = Timestamp.valueOf(LocalDate.now().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDate.now().atTime(23, 59));
        return getOrderCountInDateRange(start, end);
    }
}
