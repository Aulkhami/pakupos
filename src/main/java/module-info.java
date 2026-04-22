module com.aulkhami.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.aulkhami.mavenproject1 to javafx.fxml;
    exports com.aulkhami.mavenproject1;
}
