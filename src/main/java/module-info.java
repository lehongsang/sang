module org.example.zipfilecrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires zip4j;
    opens zipfilecrack to javafx.fxml;
    exports zipfilecrack;
}