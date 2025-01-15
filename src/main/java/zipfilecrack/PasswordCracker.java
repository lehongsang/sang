package zipfilecrack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PasswordCracker {
    private final ExecutorService executor;
    private final int numThreads;
    private final int maxLength;
    private final String CHARSET;  // Sử dụng CHARSET từ PasswordGenerator thông qua phương thức getCharset()
    private volatile boolean found = false;

    public PasswordCracker(int numThreads, int maxLength) {
        this.executor = Executors.newFixedThreadPool(numThreads);
        this.numThreads = numThreads;
        this.maxLength = maxLength;
        this.CHARSET = PasswordGenerator.getCharset();  // Gọi phương thức công khai getCharset() từ PasswordGenerator
    }

    public void generateTasks(String zipFilePath) {
        int chunkSize = CHARSET.length() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? CHARSET.length() : (i + 1) * chunkSize;
            executor.submit(new PasswordTask(start, end, "", zipFilePath));
        }
    }

    public void addPasswordTask(String password, String zipFilePath) {
        executor.submit(() -> {
            if (!found) {
                if (ZipFileTask.tryPassword(password, zipFilePath)) {
                    found = true;
                }
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
