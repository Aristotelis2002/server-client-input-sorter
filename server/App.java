package server;

import server.sorting.MultithreadedMergeSort;

//Class for testing algorithms
public class App {
    @SuppressWarnings({ "checkstyle:indentation", "checkstyle:magicnumber" })
    public static void main(String[] args) {
        Integer.parseInt("23");
        int[] array = { 17, 78, 55, 86, 79, 34, 72, 31, 54, 5, 82, 59, 68, 74, 65, 54, 80, 43, 89, 9, 27, 49, 93, 4, 54,
                97, 77, 94, 93, 7, 28, 62, 37, 35, 49, 16, 21, 73, 99, 75, 30, 81, 35, 99, 55, 52, 53, 35, 47, 95, 96,
                26, 44, 42, 31, 51, 0, 91, 60, 97, 84, 19, 25, 47, 8, 12, 48, 25, 34, 73, 24, 61, 4, 58, 96, 55, 65, 0,
                60, 63, 1, 2, 0, 5, 6, 25 , 21,
                1, 48, 7, 49, 26, 4, 43, 57, 55, 34, 70, 52, 71, 41, 30, 70, 50, 94, 70, 27, 28, 95 };
        MultithreadedMergeSort.sort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d ", array[i]);
        }
    }
}
