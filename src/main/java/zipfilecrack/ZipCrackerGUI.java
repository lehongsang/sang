package zipfilecrack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ZipCrackerGUI extends Application {
    private TextArea outputArea; // Khu vực hiển thị tiến trình

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

        chooseFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePathLabel.setText("Đường dẫn tệp: " + selectedFile.getAbsolutePath());
            }
        });

        // Nhập độ dài mật khẩu
        TextField maxLengthField = new TextField();
        maxLengthField.setPromptText("Nhập độ dài tối đa của mật khẩu");

        // Nút bắt đầu kiểm tra
        Button startButton = new Button("Bắt đầu kiểm tra");
        startButton.setDisable(true);

        // Khu vực hiển thị kết quả
        outputArea = new TextArea();
        outputArea.setEditable(false);

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
            if (zipFilePath.isEmpty() || maxLengthText.isEmpty()) {
                appendOutput("Vui lòng chọn tệp ZIP và nhập độ dài mật khẩu.");
                return;
            }

            try {
                int maxLength = Integer.parseInt(maxLengthText);

                new Thread(() -> {
                    appendOutput("Đang bắt đầu xử lý...");
                    Main.processZipFile(zipFilePath, maxLength, outputArea); // Gọi xử lý từ lớp Main
                }).start();

            } catch (NumberFormatException e) {
                appendOutput("Độ dài mật khẩu phải là một số nguyên.");
            }
        });

        // Bố cục giao diện
        VBox root = new VBox(10);
        root.getChildren().addAll(
                chooseFileButton,
                filePathLabel,
                new Label("Độ dài tối đa của mật khẩu:"),
                maxLengthField,
                startButton,
                new Label("Kết quả:"),
                outputArea
        );

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void appendOutput(String text) {
        outputArea.appendText(text + "\n");
    }
}
