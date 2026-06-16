package com.aulkhami.pakupos.app.pos;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.models.entities.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class POSModel implements Model {
    private final StringProperty customerName = new SimpleStringProperty("");
    private final ObservableList<Product> cart = FXCollections.observableArrayList();
    private final ObservableList<Product> catalog = FXCollections.observableArrayList();

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    public ObservableList<Product> getCart() {
        return cart;
    }

    public ObservableList<Product> getCatalog() {
        return catalog;
    }
}
