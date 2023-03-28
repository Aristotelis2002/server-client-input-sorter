package server.sorting;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MultithreadedQuickSort {

    private static final int SEQUENTIAL_THRESHOLD = 1000;

    public static void sort(int[] arr, int numThreads) {
        // Create a ForkJoinPool with the specified number of threads.
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new SortTask(arr, 0, arr.length - 1));

        pool.shutdown();
        pool.close();
    }

    private static class SortTask extends RecursiveAction {
        private int[] arr;
        private int lo;
        private int hi;

        public SortTask(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {
            if (hi - lo < SEQUENTIAL_THRESHOLD) {
                // Single thread sorting for small arrays
                QuickSort.sort(arr, lo, hi);
                return;
            }

            int mid = (lo + hi) / 2;
            invokeAll(new SortTask(arr, lo, mid), new SortTask(arr, mid + 1, hi));

            merge(arr, lo, mid, hi);
        }

        private void merge(int[] arr, int lo, int mid, int hi) {
            int[] temp = new int[hi - lo + 1];

            int i = lo;
            int j = mid + 1;
            int k = 0;

            while (i <= mid && j <= hi) {
                if (arr[i] < arr[j]) {
                    temp[k] = arr[i];
                    i++;
                } else {
                    temp[k] = arr[j];
                    j++;
                }
                k++;
            }

            while (i <= mid) {
                temp[k] = arr[i];
                k++;
                i++;
            }

            while (j <= hi) {
                temp[k] = arr[j];
                k++;
                j++;
            }

            for (int l = 0; l < temp.length; l++) {
                arr[lo + l] = temp[l];
            }
        }
    }
}
