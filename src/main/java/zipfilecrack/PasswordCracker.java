package zipfilecrack;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordCracker {
    private final ExecutorService executor;
    private volatile boolean found = false; // Dừng khi tìm thấy mật khẩu đúng

    public PasswordCracker(int numThreads) {
        executor = Executors.newFixedThreadPool(numThreads);
    }

    public boolean tryPassword(String password, String zipFilePath) {
        if (found) return false; // Dừng kiểm tra nếu đã tìm thấy mật khẩu
        System.out.println("\u0110ang kiểm tra mật khẩu: " + password);
        if (ZipFileTask.tryPassword(password, zipFilePath)) {
            found = true;
            System.out.println("Mật khẩu đúng: " + password);
            return true;
        }
        return false;
    }

    public void addPasswordTask(String password, String zipFilePath) {
        executor.submit(() -> {
            if (!found) {
                tryPassword(password, zipFilePath);
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void awaitTermination() {
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}