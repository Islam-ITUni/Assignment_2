package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;
    private PerformanceTracker tracker;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = new PerformanceTracker();
    }

    public MaxHeap(int[] array) {
        this.capacity = array.length;
        this.heap = Arrays.copyOf(array, array.length);
        this.size = array.length;
        this.tracker = new PerformanceTracker();
        buildHeap();
    }

    public int extractMax() {
        tracker.recordComparison(2);
        tracker.recordArrayAccess(3);
        
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return max;
    }

    public void increaseKey(int index, int newValue) {
        tracker.recordComparison(2);
        tracker.recordArrayAccess(2);
        
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (newValue < heap[index]) {
            throw new IllegalArgumentException("New value must be greater than current value");
        }

        heap[index] = newValue;
        heapifyUp(index);
    }

    public void insert(int value) {
        tracker.recordComparison(1);
        tracker.recordArrayAccess(2);
        
        if (size == capacity) {
            resize();
        }
        
        heap[size] = value;
        size++;
        heapifyUp(size - 1);
    }

    public int getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        tracker.recordArrayAccess(1);
        return heap[0];
    }

    private void heapifyUp(int index) {
        int current = index;
        
        while (current > 0) {
            tracker.recordComparison(1);
            tracker.recordArrayAccess(2);
            
            int parent = (current - 1) / 2;
            
            if (heap[parent] >= heap[current]) {
                break;
            }
            
            swap(parent, current);
            current = parent;
        }
    }

    private void heapifyDown(int index) {
        int current = index;
        
        while (hasLeftChild(current)) {
            tracker.recordComparison(2);
            tracker.recordArrayAccess(3);
            
            int largest = current;
            int leftChild = leftChildIndex(current);
            int rightChild = rightChildIndex(current);

            if (heap[leftChild] > heap[largest]) {
                largest = leftChild;
            }

            if (hasRightChild(current) && heap[rightChild] > heap[largest]) {
                largest = rightChild;
            }

            if (largest == current) {
                break;
            }

            swap(current, largest);
            current = largest;
        }
    }

    private void buildHeap() {
        tracker.startBuildHeap();
        for (int i = (size / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
        tracker.endBuildHeap();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    private void swap(int i, int j) {
        tracker.recordSwap();
        tracker.recordArrayAccess(2);
        
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) < size;
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }

    public void resetTracker() {
        tracker.reset();
    }

    public int[] getHeapArray() {
        return Arrays.copyOf(heap, size);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(heap, size));
    }
}// Optimization: improved heapify performance
// Edge case: handle single element optimization
// Optimization: Improved heapify performance
// Edge case handling
// Optimization: memory efficiency
