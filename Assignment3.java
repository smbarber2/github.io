package assignment3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

/**
 * @author Sarah Barber Assignment 3.
 *
 * This program executes three different quick sorts that have different pivots
 * in the array, along with a merge sort. All of these sorts are then compared
 * to see which is most efficient.
 * @since February 10, 2022. I have followed the UNCG Academic Integrity policy
 * on this assignment.
 */
public class Assignment3 {

    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = random.ints(24, 0, 100).toArray();           //Completely random array with 24 numbers

        System.out.println("____________________________________________________________________________________");
        System.out.println("Original array: " + Arrays.toString(arr) + "\n");

        quickSort(arr, 0, arr.length - 1, 1);       // quick sort using first element as pivot
        System.out.println("Sorted array (pivot on first): " + Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1, 2);       // sort using middle element as pivot
        System.out.println("Sorted array (pivot in the middle): " + Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1, 3);        // quick sort using random element as pivot
        System.out.println("Sorted array (random pivot): " + Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1, 4);        // quick sort using median of 3 as pivot
        System.out.println("Sorted array (median of 3): " + Arrays.toString(arr));

        System.out.println("____________________________________________________________________________________");

        performance();
        mergeSortArray();//Calls all methods to execute seperate parts of the problem

    }

    /**
     * Basic quick sort that can be used with different pivot and partition
     * placements
     */
    public static void quickSort(int[] arr, int left, int right, int pivot) {
        if (left < right) {
            int pivotIndex = getPivotIndex(arr, left, right, pivot);      //obtains the pivot
            int partitionIndex = partition(arr, left, right, pivotIndex);       //optains the partition

            quickSort(arr, left, partitionIndex - 1, pivot);
            quickSort(arr, partitionIndex + 1, right, pivot);
        }
    }

    /**
     * This method obtains the pivot index depending on the type of sort that
     * will be used
     */
    public static int getPivotIndex(int[] arr, int left, int right, int choice) {
        int pivotIndex = left;
        switch (choice) {
            case 1: // use first element as pivot
                return left;
            case 2: // use middle element as pivot
                pivotIndex = (left + right) / 2;
                break;
            case 3: // use random element as pivot
                pivotIndex = new Random().nextInt(right - left + 1) + left;
                break;
            case 4: // use median of 3 as pivot
                int mid = (left + right) / 2;
                if (arr[left] > arr[mid]) {     //if the left is greater than the middle, perform a swap.
                    swap(arr, left, mid);
                }
                if (arr[mid] > arr[right]) {    //if the middle is greater than the right, perform a swap.
                    swap(arr, mid, right);
                    if (arr[left] > arr[mid]) {     //if the left is greater than the middle, perform a swap.
                        swap(arr, left, mid);
                    }
                }
                pivotIndex = mid;
                break;
        }
        return pivotIndex;
    }

    /**
     * Finds the partition in the array in relation to the pivot
     */
    private static int partition(int[] arr, int left, int right, int pivot) {
        int pivotValue = arr[pivot];        //the pivotvalue is set as the location of the pivot within the array
        swap(arr, pivot, right);        //swaps the values
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {      //if the array location is less than the pivot, it swaps
                swap(arr, i, storeIndex);       //swaps the values
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);       //swaps the vlaues
        return storeIndex;
    }

    /**
     * Swaps values
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];        //Swaps two values
        arr[j] = temp;
    }

    /**
     * Checks to see if the array is sorted or not
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {      //If an element on the array is less than the one that comes before it, it is not sorted
                return false;
            }
        }
        return true;
    }

    /**
     * Runs benchmarks for each sort and sees how long it takes to perform each
     * sort while N grows
     */
    public static void performance() {
        Random random = new Random();
        int[] arr = random.ints(24, 0, 100).toArray();           //Completely random array with 24 numbers
        // Sort using each pivot choice and measure the time it takes for different array sizes
        int[] pivotOptions = {1, 2, 3, 4};          //The four sort options
        int[] arraySizes = {1000, 2000, 3000, 4000, 5000};      //The five sizes N will be at when testing
        int numTrials = 10;

        for (int pivot : pivotOptions) {
            System.out.println("____________________________________________________________________________________");
            System.out.println("*************************************BENCHMARKS*************************************");
            System.out.println("Pivot choice: " + pivot);
            for (int N : arraySizes) {
                int[] testArr = Arrays.copyOfRange(arr, 0, N);      //copies from the original array
                long totalTime = 0;
                for (int t = 0; t < numTrials; t++) {       //checks to make sure it hasn't run too many trials
                    int[] copyArr = Arrays.copyOf(testArr, testArr.length);
                    long start = System.nanoTime();                 //captures the time before the sort begins, with nanoTime being more precise than currentTimeMillies
                    quickSort(copyArr, 0, copyArr.length - 1, pivot);
                    long end = System.nanoTime();                   //captures the time after the sort, with nanoTime being more precise than currentTimeMillies
                    totalTime += end - start;

                }
                System.out.println("N: " + N + ", Average time (milliseconds): " + (totalTime / numTrials / 1000000.0) + "\n");     //prints out the total time it takes
            }
        }
    }

    /**
     * This is the merge sort array that is run alongside the quick sorts
     */
    public static void mergeSortArray() {
        Random random = new Random();
        int[] arr = random.ints(24, 0, 100).toArray();           //Completely random array with 24 numbers
        System.out.println("_________________________________Merge Sort_________________________________________");
        System.out.println("Unsorted array: " + Arrays.toString(arr));

        mergeSort(arr, 0, arr.length - 1);      //Merge sort values

        System.out.println("Sorted array: " + Arrays.toString(arr));

        if (isSorted(arr)) {
            System.out.println("The array is sorted.");         //if the merge sort passes through the isSorted() method successfully, it has been sorted
        } else {
            System.out.println("The array is not sorted.");
        }
        sortComparison();       //calls the sort comparison method
    }

    /**
     * sorts the array through divide and conquer
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {      //if the start is less than the right, execute the if statement
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);     //recurseivly parses through the array
            merge(arr, left, mid, right);
        }
    }

    /**
     * The actual merging of values within the given array to sort them
     */
    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];      //declares new values and arrays
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];       //left side of the array
        }

        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];        //right side of the array
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];      //if the left side is less than or equal to the right, keep it as left
                i++;
            } else {
                arr[k] = R[j];      //else, keep it as the right
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];      //as long as i is less than n1, keep it as left
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];      ////as long as i is less than n2, keep it as left
            j++;
            k++;
        }
    }

    /**
     * Compares the speeds of the two sorting methods to see which is fastest
     */
    public static void sortComparison() {
        Random random = new Random();
        int[] arr1 = random.ints(24, 0, 100).toArray();           //Completely random array with 24 numbers
        int[] arr2 = arr1.clone();

        long start1 = System.nanoTime();            //checks the time before the sort
        mergeSort(arr1, 0, arr1.length - 1);        //executes the sort
        long end1 = System.nanoTime();              //checks the time after the sort
        long timeMerge = end1 - start1;             //finds the total time taken
        System.out.println("------------------------------------------");
        System.out.println("MergeSort: " + timeMerge + " milliseconds");        //prints results

        long start2 = System.nanoTime();            //checks the time before the sort
        quickSort(arr2, 0, arr2.length - 1, 2);     //executes the sort
        long end2 = System.nanoTime();              //checks the time after the sort
        long timeQuick = end2 - start2;             //finds the total time taken
        System.out.println("QuickSort: " + timeQuick + " milliseconds");
        System.out.println("------------------------------------------");       //prints results

        if (timeQuick < timeMerge) {
            long timeComp = timeMerge - timeQuick;
            System.out.println("Quicksort is faster than Mergesort by: " + timeComp + " milliseconds.");        //if the quicksort is found to be faster, it prints the results
        } else {
            long timeComp = timeQuick - timeMerge;
            System.out.println("Mergesort is faster than Quicksort by: " + timeComp + " milliseconds.");        ////if the mergesort is found to be faster, it prints the results
        }
    }

    /**
     * Generates a random array to be used
     */
    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {        //entering the random values into the array
            arr[i] = rand.nextInt(size);
        }
        return arr;
    }

}
