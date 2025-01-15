package zipfilecrack;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Main {
    public static String processZipFile(String zipFilePath, int maxLength, int numThreads, TextArea outputArea) {
        PasswordCracker cracker = new PasswordCracker(numThreads, maxLength);
        ExecutionTimer timer = new ExecutionTimer();
        timer.start();

        PasswordGenerator.generatePasswords("", zipFilePath, cracker, maxLength);

        cracker.shutdown();
        cracker.awaitTermination();

        timer.stop();

        Platform.runLater(() -> outputArea.appendText("Thời gian chạy: " + timer.getElapsedTimeSeconds() + " giây.\n"));

        if (cracker.isFound()) {
            return "extracted"; // Đường dẫn mặc định cho thư mục giải nén
        } else {
            return null;
        }
    }
}
