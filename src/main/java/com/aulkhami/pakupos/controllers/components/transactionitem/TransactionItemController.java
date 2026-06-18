package com.aulkhami.pakupos.controllers.components.transactionitem;

import com.aulkhami.pakupos.controllers.Controller;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class TransactionItemController implements Controller {

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label amountLabel;

    private final Order order;
    private Region view;

    public TransactionItemController(Order order) {
        this.order = order;
    }

    @Override
    public Region getView() throws IOException {
        if (view == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aulkhami/pakupos/components/transaction-item.fxml"));
            loader.setController(this);
            view = loader.load();

            String name = order.getCustomerName() != null && !order.getCustomerName().trim().isEmpty()
                    ? order.getCustomerName()
                    : "Guest";
            String code = order.getOrderCode() != null ? order.getOrderCode() : "";
            customerNameLabel.setText(name + (code.isEmpty() ? "" : " - " + code));

            String payment = order.getPaymentMethod() != null ? order.getPaymentMethod().toUpperCase() : "CASH";
            detailsLabel.setText("Metode: " + payment + " • Status: " + (order.getStatus() != null ? order.getStatus().name() : "PENDING"));

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            amountLabel.setText(currencyFormat.format(order.getTotalAmount() != null ? order.getTotalAmount() : java.math.BigDecimal.ZERO).replace("Rp", "Rp "));
        }
        return view;
    }
}
