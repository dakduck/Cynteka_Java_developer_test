package org.gurova.example;

/***
 * @author Irina Gurova
 */
public class Utils {
    /***
     * Метод определяет, найдено ли лучшее совпадение для проверяемого слова
     * @param chosenMaxValue Значение коэфф. схожести
     * @param chosenMaxIndex Индекс выбранного слова из второго множества
     * @param comparisonsMap Массив сопоставлений коэффициентов каждого слова с каждым
     * @return Возвращает true, если это лучшая пара, иначе - false
     */
    public static boolean isItPureMax(float chosenMaxValue, int chosenMaxIndex, float[][] comparisonsMap) {
        for (int i = 0; i < comparisonsMap.length; i++) {
            if (comparisonsMap[i][chosenMaxIndex] > chosenMaxValue) {
                return false;
            }
        }
        return true;
    }

    /***
     * Метод проверяет, есть ли для проверяемого слова схожие слова из второго множества
     * @param currentWordCoeffs Массив с коэфф. схожести
     * @return Если сумма коэффициентов равна нулю, то для проверяемого слова нет схожих
     */
    public static boolean isSumOfComparisonsZero(float[] currentWordCoeffs) {
        float sum = 0f;
        for (float n :
                currentWordCoeffs) {
            sum += n;
        }
        return Float.compare(sum, 0f) == 0;
    }

}
