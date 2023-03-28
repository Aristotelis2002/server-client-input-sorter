package server.sorting;

public class QuickSort {

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        int pivot = getMedian(arr[low], arr[mid], arr[high]);
        int i = low; // index of smaller element
        int j = high;

        while (i <= j) {
            // If current element is smaller than pivot
            while (arr[i] < pivot) {
                i++;
            }

            // If current element is greater than pivot
            while (arr[j] > pivot) {
                j--;
            }

            // If we have found a pair of elements that need to be swapped
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        return i <= high ? i : high;
    }

    static int getMedian(int a, int b, int c) {
        if ((a < b && b < c) || (c < b && b < a)) {
            return b;
        }
        if ((b < a && a < c) || (c < a && a < b)) {
            return a;
        }
        return c;
    }

    // @Arguments: arr -> array to be sorted
    // low -> first index
    // high -> index of last element
    public static void sort(int[] arr, int low, int high) {
        if (low < high) {

            int pi = partition(arr, low, high);

            sort(arr, low, pi - 1);
            sort(arr, pi, high);
        }
    }
}
