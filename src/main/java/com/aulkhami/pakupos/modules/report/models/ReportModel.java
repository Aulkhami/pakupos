package com.aulkhami.pakupos.modules.report.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public class ReportModel implements Model {

    private final ObjectProperty<BigDecimal> totalSales = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final IntegerProperty totalOrders = new SimpleIntegerProperty(0);
    private final ObservableList<Order> transactions = FXCollections.observableArrayList();

    private final ObjectProperty<LocalDate> filterFromDate = new SimpleObjectProperty<>(LocalDate.now());
    private final ObjectProperty<LocalDate> filterToDate = new SimpleObjectProperty<>(LocalDate.now());

    public ObjectProperty<BigDecimal> totalSalesProperty() {
        return totalSales;
    }

    public BigDecimal getTotalSales() {
        return totalSales.get();
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales.set(totalSales);
    }

    public IntegerProperty totalOrdersProperty() {
        return totalOrders;
    }

    public int getTotalOrders() {
        return totalOrders.get();
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders.set(totalOrders);
    }

    public ObservableList<Order> transactionsProperty() {
        return transactions;
    }

    public ObjectProperty<LocalDate> filterFromDateProperty() {
        return filterFromDate;
    }

    public ObjectProperty<LocalDate> filterToDateProperty() {
        return filterToDate;
    }
}
