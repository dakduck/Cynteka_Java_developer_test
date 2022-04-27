package org.gurova.example;

import org.simmetrics.StringMetric;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.gurova.example.Utils.isItPureMax;
import static org.gurova.example.Utils.isSumOfComparisonsZero;

/***
 * @author Irina Gurova
 */
public class StringMetrics {

    /***
     * Основной метод программы сопоставления схожих строк.
     * @param inputFileName Наименование входного файла
     * @param outputFileName Наименование выходного файла
     * @param isForTests Если true, то метод вернет финальную строку, которая вошла в выходной файл, для проверки тестами.
     * @return Либо возвращает получившуюся в файле запись, либо null
     */
    public static String createComparativePairs(String inputFileName, String outputFileName, boolean isForTests) {

        ReadData initialData = new ReadData();
        initialData.readFromFile(inputFileName);

        int[] relatedWordsFromFirstToSecond = findRelatedWords(initialData.getFirstWords(), initialData.getSecondWords());
        String relatedWordsFromFirstToSecondFileContent = initialData.createFileContent(relatedWordsFromFirstToSecond, initialData.getFirstWords(), initialData.getSecondWords());

        Set<Integer> indicesOfSecondWordsUsed = Arrays.stream(relatedWordsFromFirstToSecond)
                .boxed()
                .filter(x -> x >= 0)
                .collect(Collectors.toSet());
        String[] secondWordsNotUsedYet = new String[initialData.getSecondWords().length - indicesOfSecondWordsUsed.size()];
        int c = 0;
        for (int i = 0; i < initialData.getSecondWords().length; i++) {
            if (!indicesOfSecondWordsUsed.contains(i)) {
                secondWordsNotUsedYet[c] = initialData.getSecondWords()[i];
                c++;
            }
        }

        int[] notUsed = new int[secondWordsNotUsedYet.length];
        Arrays.fill(notUsed, -1);
        String second = initialData.createFileContent(notUsed, secondWordsNotUsedYet, initialData.getFirstWords());

        String s = relatedWordsFromFirstToSecondFileContent + (second.length() > 0 ? ("\n" + second) : "");
        initialData.writeToFile(s, outputFileName);

        if (isForTests) return s;
        return null;
    }

    /***
     * Метод находит схожие слова из двух множеств и возвращает их соответствие
     * @param firstWords Первое множество слов
     * @param secondWords Второе множество слов
     * @return Массив чисел, в котором на i-м месте стоит индекс слова из второго множества,
     * соответствующий i-му слову из первого множества
     */
    private static int[] findRelatedWords(String[] firstWords, String[] secondWords) {
        float[][] comparisonResults = new float[firstWords.length][secondWords.length];

        StringMetric metric = org.simmetrics.metrics.StringMetrics.jaro();

        for (int i = 0; i < firstWords.length; i++) {
            for (int j = 0; j < secondWords.length; j++) {
                // каждому слову из первого множ. вычисляется коэффициент схожести с каждым словом из второго множ.
                comparisonResults[i][j] = metric.compare(firstWords[i], secondWords[j]);
            }
        }

        int[] maxElementIndex = new int[comparisonResults.length];

        for (int i = 0; i < comparisonResults.length; i++) {
            // для каждого слова из первого множ. вычисляется слово из второго с наибольшим коэфф. схожести
            maxElementIndex[i] = findIndexOfMax(comparisonResults[i], comparisonResults, new HashSet<>());
        }

        return maxElementIndex;
    }

    /***
     * Метод поиска индекса масимально подходящего слова из второго множества слову из первого
     * @param currentWordCoeffs Массив с коэфф. схожести текущего слова из первого множ. со всеми словами из второго
     * @param comparisonsMap Массив сопоставлений коэффициентов каждого слова с каждым
     * @param exclude множество индексов тех слов, которые не должны быть рассмотрены. Изначально множество пусто.
     *                Так как сопоставление 1 к 1, то в это множ. попадают те слова, которые лучше подходят к другому слову.
     * @return Возвращает индекс подходящего слова
     */
    private static int findIndexOfMax(float[] currentWordCoeffs, float[][] comparisonsMap, Set<Integer> exclude) {
        int maxAt = 0;

        // если все индексы исключены или ни одно слово не подходит к данному, то для данного слова нет пары
        if (exclude.size() == currentWordCoeffs.length || isSumOfComparisonsZero(currentWordCoeffs)) {
            return -1;
        }

        // поиск максимального коэффициента
        for (int i = 0; i < currentWordCoeffs.length; i++) {
            if (!exclude.contains(i)) {
                maxAt = Float.compare(currentWordCoeffs[i], currentWordCoeffs[maxAt]) >= 0 ? i : maxAt;
            }
        }

        // проверка, является ли слово с выбранным индексом лучшим еще для какого-то слова
        if (!isItPureMax(currentWordCoeffs[maxAt], maxAt, comparisonsMap)) {
            // если да, то добавляем этот индекс в исключения и заново определяем лучшую пару
            exclude.add(maxAt);
            return findIndexOfMax(currentWordCoeffs, comparisonsMap, exclude);
        }

        return maxAt;
    }


}
