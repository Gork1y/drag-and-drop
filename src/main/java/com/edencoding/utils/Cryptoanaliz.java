package com.edencoding.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cryptoanaliz {
    private final int real;
    private static List<Character> listTemplate;

    {
        listTemplate = List.of('А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
                'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', //32
                'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', //32
                '.', ',', '”', ':', '-', '!', '?', ' '); //8

    }

    public Cryptoanaliz(int real) {
        this.real = real;
    }

    public char[] encrypt(int key, char[] charTemplate) {
        char[] result = Arrays.copyOf(charTemplate, real);
        for (int i = 0; i < real; i++) {

            if (listTemplate.contains(charTemplate[i])) {

                int orderNumber = listTemplate.indexOf(charTemplate[i]);
                int newOrderNumber = orderNumber + key;

                if (newOrderNumber < listTemplate.size()) {
                    result[i] = listTemplate.get(newOrderNumber);
                }

                if (newOrderNumber >= listTemplate.size()) {
                    newOrderNumber = (newOrderNumber % listTemplate.size());
                    result[i] = listTemplate.get(newOrderNumber);
                }
            }
        }
        return result;
    }


    public char[] decrypt(int keyReverse, char[] charTemplate) {
        char[] result = Arrays.copyOf(charTemplate, real);
        for (int i = 0; i < real; i++) {
            if (listTemplate.contains(charTemplate[i])) {
                int orderNumber = listTemplate.indexOf(charTemplate[i]); // порядковый номер зашифрованной буквы
                int newOrderNumber = orderNumber - keyReverse; // порядковый номер после дешифрования

                if ((newOrderNumber >= 0) && (newOrderNumber < listTemplate.size())) {
                    result[i] = listTemplate.get(newOrderNumber);
                } else {
                    newOrderNumber = listTemplate.size() + newOrderNumber;
                    result[i] = listTemplate.get(newOrderNumber);
                }
            }

        }
        return result;
    }
}


