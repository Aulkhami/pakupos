/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.modules.dashboard.models;

import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.models.bindings.BigCurrencyBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Rakha
 */
public class DashboardModel implements Model {

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
}
