package zipfilecrack;

public class ExecutionTimer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getElapsedTimeSeconds() {
        return (endTime - startTime) / 1_000_000_000.0; // Chuyển đổi từ nanô giây sang giây
    }

    public void printElapsedTime() {
        System.out.println("Thời gian chạy: " + getElapsedTimeSeconds() + " giây.");
    }
}
