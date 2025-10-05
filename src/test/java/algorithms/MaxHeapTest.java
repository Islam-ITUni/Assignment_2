package algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

class MaxHeapTest {
    private MaxHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MaxHeap(10);
    }

    @Test
    void testEmptyHeap() {
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertThrows(NoSuchElementException.class, () -> heap.getMax());
        assertThrows(NoSuchElementException.class, () -> heap.extractMax());
    }

    @Test
    void testSingleElement() {
        heap.insert(42);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(42, heap.getMax());
        assertEquals(42, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testInsertAndExtractMax() {
        int[] elements = {10, 5, 15, 3, 7, 12, 20};
        for (int elem : elements) {
            heap.insert(elem);
        }

        assertEquals(20, heap.getMax());
        assertEquals(7, heap.size());

        int[] expectedOrder = {20, 15, 12, 10, 7, 5, 3};
        for (int expected : expectedOrder) {
            assertEquals(expected, heap.extractMax());
        }

        assertTrue(heap.isEmpty());
    }

    @Test
    void testConstructorWithArray() {
        int[] input = {3, 1, 6, 5, 2, 4};
        MaxHeap heapFromArray = new MaxHeap(input);

        assertEquals(6, heapFromArray.size());
        assertEquals(6, heapFromArray.getMax());

        int[] extracted = new int[6];
        for (int i = 0; i < 6; i++) {
            extracted[i] = heapFromArray.extractMax();
        }

        int[] expected = {6, 5, 4, 3, 2, 1};
        assertArrayEquals(expected, extracted);
    }

    @Test
    void testIncreaseKey() {
        int[] elements = {5, 10, 3, 8, 15};
        for (int elem : elements) {
            heap.insert(elem);
        }

        heap.increaseKey(2, 20);
        assertEquals(20, heap.getMax());

        assertEquals(20, heap.extractMax());
        assertEquals(15, heap.extractMax());
        assertEquals(10, heap.extractMax());
        assertEquals(8, heap.extractMax());
        assertEquals(5, heap.extractMax());
    }

    @Test
    void testIncreaseKeyInvalid() {
        heap.insert(5);
        heap.insert(10);
        
        assertThrows(IllegalArgumentException.class, () -> heap.increaseKey(0, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.increaseKey(5, 15));
    }

    @Test
    void testDuplicates() {
        int[] elements = {5, 5, 5, 3, 3, 8, 8};
        for (int elem : elements) {
            heap.insert(elem);
        }

        assertEquals(8, heap.extractMax());
        assertEquals(8, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(3, heap.extractMax());
        assertEquals(3, heap.extractMax());
    }

    @Test
    void testPerformanceMetrics() {
        heap.insert(10);
        heap.insert(5);
        heap.insert(15);
        
        heap.extractMax();
        heap.increaseKey(1, 20);
        
        assertTrue(heap.getTracker().getComparisons() > 0);
        assertTrue(heap.getTracker().getSwaps() > 0);
        assertTrue(heap.getTracker().getArrayAccesses() > 0);
    }
}// Edge case handling
