package hw_lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String[] arr = new String[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(i);
        }

        //result task 1

        swapArrElem(arr, 3, 9);
        //System.out.println("Исходные элементы массива: " + Arrays.toString(arr));
        //System.out.println("Массив, где 1 и 3 элементы поменяны местами: " + Arrays.toString(swapArrElem(arr, 1, 3)));


        //result task 2

        convertToArrayList(arr);
        //System.out.println("Результат преобразования в ArrayList: " + convertToArrayList(arr));


       //task 3

        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i = 0; i < 10; i++) {
            orangeBox.add(new Orange());
            appleBox.add(new Apple());
        }

        Box<Apple> appleBox1 = new Box<>();

        appleBox.move(appleBox1);


        System.out.println("Вес коробки с яблоками: " + appleBox.getBoxWeight());
        System.out.println("Вес коробки с апельсинами: " + orangeBox.getBoxWeight());
        System.out.println("Вес коробок с яблоками и апельсинами равный: " + appleBox.compare(orangeBox));
        System.out.println("Вес коробки с яблоками после пересыпки: " + appleBox1.getBoxWeight());

    }


    //solution task 1

    private static <T> T[] swapArrElem(T[] arr, int elem1, int elem2) {
        try {
            if (arr != null) {
                T tmp = arr[elem1];
                arr[elem1] = arr[elem2];
                arr[elem2] = tmp;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return arr;
    }


    //solution task 2

    private static <E> ArrayList convertToArrayList(E[] arr) {
        ArrayList<E> convertToArrayList = new ArrayList<>(Arrays.asList(arr));
        return convertToArrayList;
    }
}
