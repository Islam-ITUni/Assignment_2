package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BenchmarkRunner {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== MaxHeap Benchmark Runner ===");
        
        while (true) {
            printMenu();
            int choice = getIntInput(scanner, "Enter your choice: ");
            
            switch (choice) {
                case 1:
                    testBasicOperations();
                    break;
                case 2:
                    runPerformanceBenchmark(scanner);
                    break;
                case 3:
                    testIncreaseKey();
                    break;
                case 4:
                    generateCSVData();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Test Basic Operations");
        System.out.println("2. Run Performance Benchmark");
        System.out.println("3. Test Increase-Key Operation");
        System.out.println("4. Generate CSV Data");
        System.out.println("5. Exit");
    }

    private static void generateCSVData() {
        System.out.println("=== Generating CSV Benchmark Data ===");
        System.out.println("Size,ConstructionTime(ms),ExtractionTime(ms),Comparisons,Swaps,ArrayAccesses");
        
        int[] sizes = {100, 1000, 10000, 100000};
        
        for (int size : sizes) {
            int[] data = new int[size];
            for (int i = 0; i < size; i++) {
                data[i] = random.nextInt(size * 10);
            }
            
            long startTime = System.nanoTime();
            MaxHeap heap = new MaxHeap(data);
            long constructionTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            while (!heap.isEmpty()) {
                heap.extractMax();
            }
            long extractionTime = System.nanoTime() - startTime;
            
            PerformanceTracker tracker = heap.getTracker();
            
            System.out.printf("%d,%.3f,%.3f,%d,%d,%d%n",
                size,
                constructionTime / 1e6,
                extractionTime / 1e6,
                tracker.getComparisons(),
                tracker.getSwaps(),
                tracker.getArrayAccesses()
            );
            
            heap.resetTracker();
        }
        System.out.println("=== CSV Data Generation Complete ===");
    }

    private static void testBasicOperations() {
        System.out.println("\n--- Testing Basic Operations ---");
        
        MaxHeap heap = new MaxHeap(10);
        int[] testData = {10, 5, 15, 3, 7, 12, 20};
        
        System.out.println("Inserting: " + Arrays.toString(testData));
        for (int value : testData) {
            heap.insert(value);
        }
        
        System.out.println("Heap after insertions: " + heap);
        System.out.println("Max element: " + heap.getMax());
        
        System.out.println("Extracting elements using extract-max:");
        while (!heap.isEmpty()) {
            System.out.print(heap.extractMax() + " ");
        }
        System.out.println();
        
        heap.getTracker().printMetrics();
        heap.resetTracker();
    }

    private static void runPerformanceBenchmark(Scanner scanner) {
        System.out.println("\n--- Performance Benchmark ===");
        int[] sizes = {100, 1000, 10000, 100000};
        
        for (int size : sizes) {
            System.out.println("\nTesting with n = " + size);
            
            int[] data = generateRandomArray(size);
            
            long startTime = System.nanoTime();
            MaxHeap heap = new MaxHeap(data);
            long constTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            while (!heap.isEmpty()) {
                heap.extractMax();
            }
            long extractTime = System.nanoTime() - startTime;
            
            PerformanceTracker tracker = heap.getTracker();
            
            System.out.println("Construction Time: " + constTime / 1e6 + " ms");
            System.out.println("Extraction Time: " + extractTime / 1e6 + " ms");
            System.out.println("Comparisons: " + tracker.getComparisons());
            System.out.println("Swaps: " + tracker.getSwaps());
            System.out.println("Array Accesses: " + tracker.getArrayAccesses());
            
            heap.resetTracker();
        }
    }

    private static void testIncreaseKey() {
        System.out.println("\n--- Testing Increase-Key Operation ---");
        
        MaxHeap heap = new MaxHeap(10);
        int[] data = {5, 10, 3, 8, 15};
        
        for (int value : data) {
            heap.insert(value);
        }
        
        System.out.println("Initial heap: " + heap);
        System.out.println("Increasing key at index 2 from 3 to 20");
        
        heap.increaseKey(2, 20);
        System.out.println("Heap after increase-key: " + heap);
        System.out.println("New max element: " + heap.getMax());
        
        heap.getTracker().printMetrics();
        heap.resetTracker();
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10);
        }
        return array;
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}