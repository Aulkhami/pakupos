module com.aulkhami.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.aulkhami.mavenproject1 to javafx.fxml;
    opens com.aulkhami.mavenproject1.controllers to javafx.fxml;
    opens com.aulkhami.mavenproject1.models.entities to javafx.fxml;
    opens com.aulkhami.mavenproject1.views to javafx.fxml;
    opens com.aulkhami.mavenproject1.views.components to javafx.fxml;

    exports com.aulkhami.mavenproject1;
    exports com.aulkhami.mavenproject1.controllers;
    exports com.aulkhami.mavenproject1.models.entities;
}
