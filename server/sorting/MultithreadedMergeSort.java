package server.sorting;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MultithreadedMergeSort extends RecursiveAction {
    private final int[] array;
    private static final int SEQUENTIAL_THRESHOLD = 150_000;

    private final int start;
    private final int end;

    public MultithreadedMergeSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < SEQUENTIAL_THRESHOLD) {
            MergeSort.sort(array, start, end);
            return;
        }

        int mid = (start + end) / 2;

        // Sort the two halves concurrently
        MultithreadedMergeSort leftTask = new MultithreadedMergeSort(array, start, mid);
        MultithreadedMergeSort rightTask = new MultithreadedMergeSort(array, mid + 1, end);
        leftTask.fork();
        rightTask.fork();

        leftTask.join();
        rightTask.join();
        merge(array, start, mid + 1, end);
    }

    // Merges two sorted subarrays into a single sorted subarray
    private static void merge(int[] array, int start, int mid, int end) {
        int[] temp = new int[end - start];

        int i = start;
        int j = mid;
        int k = 0;
        while (i < mid && j < end) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i < mid) {
            temp[k++] = array[i++];
        }
        while (j < end) {
            temp[k++] = array[j++];
        }

        // Copy the merged subarray back to the original array
        System.arraycopy(temp, 0, array, start, end - start);
    }

    public static void sort(int[] array) {
        ForkJoinPool pool = new ForkJoinPool(ForkJoinPool.getCommonPoolParallelism() - 1);
        pool.invoke(new MultithreadedMergeSort(array, 0, array.length - 1));
        pool.shutdown();
        pool.close();
    }
}
