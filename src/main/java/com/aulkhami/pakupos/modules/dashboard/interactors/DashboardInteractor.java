package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.services.DashboardService;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.modules.pos.repositories.OrderRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardInteractor extends MenuBarInteractor {

    private final DashboardModel model;
    private final DashboardService dashboardService;
    private final AuthService authService;

    public DashboardInteractor(DashboardModel model) {
        this.model = model;
        this.dashboardService = new DashboardService();
        this.authService = new AuthService();
    }

    public void loadDashboardData() {
        try {
            java.math.BigDecimal todaySales = dashboardService.getTotalSalesToday();
            model.setSalesAmount(todaySales != null ? todaySales.longValue() : 0L);
            model.setSalesCount(dashboardService.getOrderCountToday());

            List<OrderResponseDTO> orders = dashboardService.getRecentTransactions(5);
            model.getRecentTransactions().setAll(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navReports() {
        try {
            App.navigate("report");
        } catch (IOException e) {
            AlertHelper.showError(
                    "System Error",
                    "Could not load Reports screen."
            );
            e.printStackTrace();
        }
    }

    public void logout() {
        authService.logout();
    }
}
