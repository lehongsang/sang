package zipfilecrack;
import javafx.scene.control.TextArea;
public class Main {
    public static void processZipFile(String zipFilePath, int maxLength, TextArea outputArea) {
        ExecutionTimer timer = new ExecutionTimer();
        timer.start(); // Bắt đầu đo thời gian

        PasswordCracker cracker = new PasswordCracker(4);
        PasswordGenerator.generatePasswords("", zipFilePath, cracker);

        cracker.shutdown();
        cracker.awaitTermination();
        timer.stop(); // Dừng đo thời gian

        // Ghi kết quả ra giao diện
        String result = "Hoàn thành! Thời gian chạy: " + timer.getElapsedTimeSeconds() + " giây.";
        outputArea.appendText(result + "\n");
    }
}
