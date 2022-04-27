package org.gurova.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/***
 * @author Irina Gurova
 */
@NoArgsConstructor
@Getter
@Setter
public class ReadData {
    /***
     * Количество слов из первого множества
     */
    private int n;
    /***
     * Количество слов из второго множества
     */
    private int m;
    /***
     * Слова первого множества
     */
    private String[] firstWords;
    /***
     * Слова второго множества
     */
    private String[] secondWords;

    /***
     * Метод создает содержимое выходного файла. Для слов из firstWords ставит в соответствие слова из secondWords
     * @param comparison Массив с индексами подходящих слов
     * @param firstWords Слова первого множества
     * @param secondWords Слова второго множества
     * @return Строка-результат. Если слову не нашлась подходящая пара, то напротив него указан знак вопроса.
     */
    public String createFileContent(int[] comparison, String[] firstWords, String[] secondWords) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < firstWords.length; i++) {
            if (comparison[i] >= 0) {
                if (i == firstWords.length - 1) {
                    str.append(firstWords[i] + ":" + secondWords[comparison[i]]);
                } else {
                    str.append(firstWords[i] + ":" + secondWords[comparison[i]] + "\n");
                }
            } else {
                if (i == firstWords.length - 1) {
                    str.append(firstWords[i] + ":?");
                } else {
                    str.append(firstWords[i] + ":?" + "\n");
                }
            }
        }

        return str.toString();
    }

    /***
     * Записывает в файл полученную строку
     * @param data Данные для записи
     * @param fileName Наименование выходного файла
     */
    public void writeToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /***
     * Считывает и сохраняет данные из файла
     * @param fileName Наименование файла с входными данными
     */
    public void readFromFile(String fileName) {
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(System.getProperty("line.separator"));
            this.setN(Integer.parseInt(scanner.next()));
            this.firstWords = new String[this.n];
            for (int i = 0; i < this.n; i++) {
                if (scanner.hasNext()) {
                    firstWords[i] = scanner.next();
                }
            }
            this.setM(Integer.parseInt(scanner.next()));
            this.secondWords = new String[this.m];
            for (int i = 0; i < this.m; i++) {
                if (scanner.hasNext()) {
                    secondWords[i] = scanner.next();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
