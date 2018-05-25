package com.chw.zookeeper.suanfa;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author chw
 * 2018/5/25
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = {12,20,5,16,15,1,30,45,23,9};
        quickSort(a, 0, a.length - 1);

    }

    public static void quickSort(int[] arr, int low, int high) {

        if (low > high) {
            return;
        }

        int start = low;
        int end = high;
        int key = arr[start];


        while (start < end ){

            while (start < end && key <= arr[end]) {
                end --;
            }

            while (start < end && key >= arr[start]) {
                start ++;
            }

            if (start < end) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }
            System.out.println("start : " + start);
            System.out.println("end : " + end);
            System.out.println(Arrays.toString(arr));
        }

        arr[low] =arr[start];
        arr[start] = key;
        System.out.println(Arrays.toString(arr));

        quickSort(arr, low ,end-1);
        quickSort(arr, end+1 ,high);
    }

    public static void swap(int a, int b) {
        int temp = b;
        b = a;
        a = temp;
        System.out.println(a);
        System.out.println(b);
    }
    @Test
    public void test1() {
        int a = 1,b = 2;
        swap(a,b);

    }
}
