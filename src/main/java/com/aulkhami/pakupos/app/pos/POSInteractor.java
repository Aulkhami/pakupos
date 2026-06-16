package com.aulkhami.pakupos.app.pos;

import com.aulkhami.pakupos.dao.OrderDAO;
import com.aulkhami.pakupos.dao.OrderItemDAO;
import com.aulkhami.pakupos.dao.ProductDAO;
import com.aulkhami.pakupos.enums.OrderStatus;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.entities.Order;
import com.aulkhami.pakupos.models.entities.OrderItem;
import com.aulkhami.pakupos.models.entities.Product;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.AlertHelper;
import com.aulkhami.pakupos.utils.SessionManager;
import java.util.List;

public class POSInteractor implements Interactor {
    private final POSModel model;
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    public POSInteractor(POSModel model) {
        this.model = model;
    }

    public void loadCatalog() {
        try {
            List<Product> products = productDAO.findAll();
            model.getCatalog().setAll(products);
        } catch (Exception e) {
            AlertHelper.showError(
                "Database Error",
                "Could not load products from database."
            );
            e.printStackTrace();
        }
    }

    public void addToCart(Product product) {
        model.getCart().add(product);
    }

    public void checkout() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            AlertHelper.showError(
                "Auth Error",
                "Session expired. Please login again."
            );
            return;
        }

        String customerName = model.getCustomerName();
        if (customerName == null || customerName.trim().isEmpty()) {
            AlertHelper.showError(
                "Validation Error",
                "Please enter customer name."
            );
            return;
        }

        if (model.getCart().isEmpty()) {
            AlertHelper.showError("Validation Error", "Cart is empty.");
            return;
        }

        try {
            // 1. Save Order
            Order order = new Order();
            order.setUserId(currentUser.getId());
            order.setCustomerName(customerName);
            order.setStatus(OrderStatus.COMPLETED);

            Order savedOrder = orderDAO.save(order);

            // 2. Save Order Items
            for (Product p : model.getCart()) {
                OrderItem item = new OrderItem();
                item.setOrderId(savedOrder.getId());
                item.setProductId(p.getId());
                item.setQuantity(1);
                item.setUnitPrice(p.getPrice());
                item.setSubtotal(p.getPrice());
                orderItemDAO.save(item);
            }

            AlertHelper.showSuccess(
                "POS",
                "Order placed successfully! Order Code: " +
                    savedOrder.getOrderCode()
            );
            model.getCart().clear();
            model.setCustomerName("");
        } catch (Exception e) {
            AlertHelper.showError(
                "System Error",
                "Failed to process checkout: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
