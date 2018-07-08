package ru.job4j.zadachi;

public class MassivIzDvuh {
    public int[] sliyanie(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];

        int ind1 = 0;
        int ind2 = 0;

        for(int i = 0; i < result.length; i++) {
            if(ind1 >= arr1.length){
                result[i] = arr2[ind2];
                ind2++;
            }else if(ind2 >= arr2.length){
                result[i] = arr1[ind1];
                ind1++;
            }else if( arr1[ind1]<arr2[ind2]){
                result[i] = arr1[ind1];
                ind1++;
            }else{
                result[i] = arr2[ind2];
                ind2++;
            }
        }
        return result;
    }
}