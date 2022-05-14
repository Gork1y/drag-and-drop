package com.edencoding.utils;

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

    public boolean findCorrectVersion(char[] charTemplate) {

        int count = 0;
        int max = 35;// минимально допустимое значение пробелов в файле который расшифровываем, если поставить меньше работать не будет! НЕ УДАЛЯТЬ!!!
        boolean check = false;

        for (char c : charTemplate) {

            // проходимся по массиву с целью выявить наличие пробелов " "
            if (c == ' ') {
                // если пробелы есть то увеличиваем счетчик
                count = count + 1;
            }
            if (count >= max) {
                //writer.write(buffer,0, real);
                check = true;
                break;
            }
            // если значение счетчика переваливает за 35, то записываем массив в файл
        }
        return check;
    }
}

// TODO Добавить метод который проверяет зашифрованные значения и выдает их в UI;

