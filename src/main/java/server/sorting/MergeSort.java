package server.sorting;

public class MergeSort {

    private static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] arrL = new int[n1];
        int[] arrR = new int[n2];

        for (int i = 0; i < n1; ++i)
            arrL[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            arrR[j] = arr[m + 1 + j];
        int i = 0;
        int j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (arrL[i] <= arrR[j]) {
                arr[k] = arrL[i];
                i++;
            } else {
                arr[k] = arrR[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = arrL[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = arrR[j];
            j++;
            k++;
        }
    }

    public static void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
}