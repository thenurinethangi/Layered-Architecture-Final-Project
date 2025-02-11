module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires static lombok;
    requires org.controlsfx.controls;
    requires java.mail;
    requires org.slf4j;
    requires jasperreports;

    opens com.example.test.db to javafx.base;
    opens com.example.test to javafx.fxml, lombok;
    opens com.example.test.dto to javafx.base;
    opens com.example.test.controller to javafx.fxml;
    exports com.example.test;
    opens com.example.test.view.tdm to javafx.base;
    exports com.example.test.dao;
    opens com.example.test.dao to javafx.fxml, lombok;
}