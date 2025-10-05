package metrics;

import java.util.HashMap;
import java.util.Map;

public class PerformanceTracker {
    private Map<String, Long> metrics;
    private long startTime;
    private long endTime;

    public PerformanceTracker() {
        this.metrics = new HashMap<>();
        reset();
    }

    public void recordComparison(int count) {
        metrics.put("comparisons", metrics.get("comparisons") + count);
    }

    public void recordSwap() {
        metrics.put("swaps", metrics.get("swaps") + 1);
    }

    public void recordArrayAccess(int count) {
        metrics.put("arrayAccesses", metrics.get("arrayAccesses") + count);
    }

    public void startBuildHeap() {
        startTime = System.nanoTime();
    }

    public void endBuildHeap() {
        endTime = System.nanoTime();
        long duration = endTime - startTime;
        metrics.put("buildHeapTime", metrics.get("buildHeapTime") + duration);
    }

    public void startMerge() {
        startTime = System.nanoTime();
    }

    public void endMerge() {
        endTime = System.nanoTime();
        long duration = endTime - startTime;
        metrics.put("mergeTime", metrics.get("mergeTime") + duration);
    }

    public long getComparisons() {
        return metrics.get("comparisons");
    }

    public long getSwaps() {
        return metrics.get("swaps");
    }

    public long getArrayAccesses() {
        return metrics.get("arrayAccesses");
    }

    public long getBuildHeapTime() {
        return metrics.get("buildHeapTime");
    }

    public long getMergeTime() {
        return metrics.get("mergeTime");
    }

    public void reset() {
        metrics.put("comparisons", 0L);
        metrics.put("swaps", 0L);
        metrics.put("arrayAccesses", 0L);
        metrics.put("buildHeapTime", 0L);
        metrics.put("mergeTime", 0L);
    }

    public Map<String, Long> getAllMetrics() {
        return new HashMap<>(metrics);
    }

    public void printMetrics() {
        System.out.println("=== Performance Metrics ===");
        System.out.println("Comparisons: " + getComparisons());
        System.out.println("Swaps: " + getSwaps());
        System.out.println("Array Accesses: " + getArrayAccesses());
        System.out.println("Build Heap Time (ns): " + getBuildHeapTime());
        System.out.println("Merge Time (ns): " + getMergeTime());
    }
}