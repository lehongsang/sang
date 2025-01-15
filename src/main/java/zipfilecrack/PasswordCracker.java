package zipfilecrack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordCracker {
    private final ExecutorService executor;
    private volatile boolean found = false;

    public PasswordCracker(int numThreads) {
        executor = Executors.newFixedThreadPool(numThreads);
    }

    public boolean tryPassword(String password, String zipFilePath) {
        if (found) return false; // Dừng kiểm tra nếu đã tìm thấy mật khẩu
        if (ZipFileTask.tryPassword(password, zipFilePath)) {
            found = true;
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

    public boolean isFound() {
        return found;
    }
}