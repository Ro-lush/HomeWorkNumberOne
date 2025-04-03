package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Клас реализует выполнене базовые арифметические операции.
 */
public class Calculator {
    final static String REGEX_OPERATION = "[^+-\\/^]";  //"[1234567890+-\\/^]";
    final static String REGEX_NUMBER = "[^1234567890]";  //"[1234567890+-\\/^]";

    final static String welcome = "Для вывода истории операции введите 'history', для отчистки итории введите 'clean'";

    /**
     * Метод для проверки вводимых значений
     *
     * @param symbol - введеные даные.
     * @return - true or false
     */
    public boolean checkSymbol(String symbol) {
        final Pattern pattern = Pattern.compile(REGEX_OPERATION, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(symbol);

        if (matcher.find()) {
            System.out.println("Присутсвуют символы отличающие от арифмитических операций, введите снова");
        }
        return matcher.find();
    }
    public boolean checkNumbers(String symbol) {
        final Pattern pattern = Pattern.compile(REGEX_NUMBER, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(symbol);

        if (matcher.find()) {
            System.out.println("Присутсвуют символы отличающие от чисел, введите снова");
        }
        return matcher.find();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String operation = getOperation(scanner);
        /*double a = getNumber(scanner, "первое");
        double b = getNumber(scanner, "второе");*/
        scanner.close();
/*
        try {
            double result = calculate(operation, a, b);
            printResult(result);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }*/
    }

    private String getOperation(Scanner scanner) {
        String operation;
        System.out.println("Введите '+' для сложения, '-' для вычитания, '*' для умножения, '/' для деления, " +
                "для возведения в степень '^' : ");
        operation = scanner.nextLine();
        while (checkSymbol(operation)){
            operation = scanner.nextLine();
        }
        return operation;
    }

    private double getNumber(Scanner scanner, String order) {
        System.out.println("Введите " + order + " число:");
        return scanner.nextDouble();
    }

}
