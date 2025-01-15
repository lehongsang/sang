package zipfilecrack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ZipCrackerGUI extends Application {
    private TextArea outputArea; // Khu vực hiển thị kết quả

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zip File Password Cracker");

        // Nút chọn tệp ZIP
        Button chooseFileButton = new Button("Chọn tệp ZIP");
        Label filePathLabel = new Label("Chưa chọn tệp nào.");
        FileChooser fileChooser = new FileChooser();

        // Thêm FileChooser vào để chọn file ZIP
        chooseFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePathLabel.setText("Đường dẫn tệp: " + selectedFile.getAbsolutePath());
            }
        });

        // Nhập độ dài mật khẩu
        TextField maxLengthField = new TextField();
        maxLengthField.setPromptText("Nhập độ dài tối đa của mật khẩu");

        // Nhập số lượng luồng thực hiện
        TextField numThreadsField = new TextField();
        numThreadsField.setPromptText("Nhập số luồng thực hiện");

        // Nút bắt đầu kiểm tra
        Button startButton = new Button("Bắt đầu kiểm tra");
        startButton.setDisable(true);

        // Khu vực hiển thị kết quả
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(150);

        // Kích hoạt nút khi tệp được chọn
        fileChooser.setTitle("Chọn tệp ZIP cần giải mã");
        chooseFileButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePathLabel.setText(selectedFile.getAbsolutePath());
                startButton.setDisable(false);
            }
        });

        // Hành động khi nhấn nút "Bắt đầu kiểm tra"
        startButton.setOnAction(event -> {
            String zipFilePath = filePathLabel.getText();
            String maxLengthText = maxLengthField.getText();
            String numThreadsText = numThreadsField.getText();
            if (zipFilePath.isEmpty() || maxLengthText.isEmpty() || numThreadsText.isEmpty()) {
                appendOutput("Vui lòng chọn tệp ZIP, nhập độ dài mật khẩu, và số luồng.");
                return;
            }

            try {
                int maxLength = Integer.parseInt(maxLengthText);
                int numThreads = Integer.parseInt(numThreadsText);  // Lấy số lượng luồng từ người dùng

                new Thread(() -> {
                    appendOutput("Đang bắt đầu xử lý...");
                    Main.processZipFile(zipFilePath, maxLength, numThreads, outputArea); // Truyền số luồng vào phương thức xử lý
                }).start();

            } catch (NumberFormatException e) {
                appendOutput("Độ dài mật khẩu và số luồng phải là các số nguyên.");
            }
        });

        // Bố cục giao diện sử dụng GridPane để sắp xếp thành hàng
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Chọn tệp ZIP:"), 0, 0);
        grid.add(chooseFileButton, 1, 0);
        grid.add(filePathLabel, 0, 1, 2, 1);  // Label hiển thị đường dẫn file
        grid.add(new Label("Độ dài tối đa của mật khẩu:"), 0, 2);
        grid.add(maxLengthField, 1, 2);
        grid.add(new Label("Số luồng thực hiện:"), 0, 3);
        grid.add(numThreadsField, 1, 3);
        grid.add(startButton, 1, 4);
        grid.add(new Label("Kết quả:"), 0, 5);
        grid.add(outputArea, 0, 6, 2, 1);

        VBox root = new VBox(20, grid);  // Tạo một VBox để chứa GridPane
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void appendOutput(String text) {
        outputArea.appendText(text + "\n");
    }
}
