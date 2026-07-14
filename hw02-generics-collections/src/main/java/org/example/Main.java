package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        //Написать метод, который меняет два элемента массива местами
        // (массив может быть любого ссылочного типа);

        Integer[] a = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(a));
        swap(a, 1, 2);
        System.out.println(Arrays.toString(a));

        String[] s = {"a", "b", "c", "d"};
        System.out.println(Arrays.toString(s));
        swap(s, 2, 3);
        System.out.println(Arrays.toString(s));

        //Написать метод, который преобразует массив в ArrayList;
        Double[] array1 = {1.1, 2.1, 3.2, 3.3};
        System.out.println("array: " + Arrays.toString(array1));
        System.out.println("arrayList: " + arrayToArrayList(array1));
        System.out.println();
        String[] array2 = {"test1", "test2", "test3", "test4"};
        System.out.println("array: " + Arrays.toString(array2));
        System.out.println("arrayList: " + arrayToArrayList(array2));

        // Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
        // Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
        // Посчитать, сколько раз встречается каждое слово.

        String[] mas = {"cat", "dog", "cat", "bird", "fish",
                "dog", "mouse", "cat", "elephant", "fish",
                "tiger", "bird", "lion", "cat", "mouse"};
        analyze(mas);

    }

    private static <T> void swap(T[] array, int a, int b) {
        T tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static <T> ArrayList<T> arrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    private static void analyze(String[] array) {
        HashMap<String, Integer> analysis = new HashMap<>();
        for (String a : array) {
            int v = analysis.getOrDefault(a, 0);
            analysis.put(a, v + 1);
        }

        for (String key : analysis.keySet()) {
            System.out.println("Word: " + key + " --- " + analysis.get(key) + " time(s)");
        }
    }
}
