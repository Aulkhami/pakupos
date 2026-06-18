package com.aulkhami.pakupos.modules.dashboard.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.models.bindings.BigCurrencyBinding;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.pos.entities.Order;
import java.math.BigDecimal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel implements Model {

    private final ObjectProperty<BigDecimal> totalSales = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final IntegerProperty totalOrders = new SimpleIntegerProperty(0);
    private final ObservableList<OrderResponseDTO> recentTransactions = FXCollections.observableArrayList();

    private LongProperty salesAmount;
    private StringProperty salesAmountStr;

    private IntegerProperty salesCount;

    public DashboardModel() {
        salesAmount = new SimpleLongProperty(0);
        salesAmountStr = new SimpleStringProperty();
        salesAmountStr.bind(new BigCurrencyBinding(salesAmount));

        salesCount = new SimpleIntegerProperty(0);
    }

    public StringProperty salesAmountProperty() {
        return salesAmountStr;
    }

    public IntegerProperty salesCountProperty() {
        return salesCount;
    }

    public void setSalesAmount(long amount) {
        this.salesAmount.set(amount);
    }

    public void setSalesCount(int count) {
        this.salesCount.set(count);
    }

    public ObservableList<OrderResponseDTO> getRecentTransactions() {
        return recentTransactions;
    }
}
