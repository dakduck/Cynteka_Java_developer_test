package org.gurova.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringMetricsExampleTest {

    @Test
    public void example01() {
        assertEquals("гвоздь:?\n" +
                "шуруп:шуруп 3х1.5\n" +
                "краска синяя:краска\n" +
                "ведро для воды:корыто для воды", StringMetrics.createComparativePairs("input1.txt", "output1.txt", true));
    }

    @Test
    public void example02() {
        assertEquals("Бетон с присадкой:Цемент", StringMetrics.createComparativePairs("input2.txt", "output2.txt", true));
    }

    @Test
    public void example03() {
        assertEquals("Бетон с присадкой:присадка бля бетона\n" +
                "доставка:?", StringMetrics.createComparativePairs("input3.txt", "output3.txt", true));
    }

    @Test
    public void example04() {
        assertEquals("Пила:?\nТорт:?", StringMetrics.createComparativePairs("input4.txt", "output4.txt", true));
    }

    @Test
    public void example05() {
        assertEquals("Пейзаж:Париж\n" +
                "Спираль:Патруль\n" +
                "Два:Доставка\n" +
                "Десять:?", StringMetrics.createComparativePairs("input5.txt", "output5.txt", true));
    }

}