package zipfilecrack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordTask implements Runnable {
    private final int start;
    private final int end;
    private final String current;
    private final String zipFilePath;

    public PasswordTask(int start, int end, String current, String zipFilePath) {
        this.start = start;
        this.end = end;
        this.current = current;
        this.zipFilePath = zipFilePath;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            String password = current + PasswordGenerator.CHARSET.charAt(i);
            if (ZipFileTask.tryPassword(password, zipFilePath)) {
                System.out.println("Password found: " + password);
                return; // Kết thúc nếu tìm thấy mật khẩu
            }
        }
    }
}
