package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Клас реализует выполнене базовые арифметические операции.
 */
public class Calculator {
    final static String REGEX_OPERATION = "[^+-\\/^]";
    final static String REGEX_NUMBER = "[^0-9]";

    final static String welcome = "Для вывода истории операции введите 'history', для отчистки итории введите 'clean'";

    /**
     * Метод для проверки вводимых значений
     *
     * @param symbol - введеные даные.
     * @return - true or false
     */
    private boolean checkSymbol(String symbol, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(symbol);
        return matcher.find();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String operation = getOperation(scanner);
        double a = getNumber(scanner, "первое");
        double b = getNumber(scanner, "второе");
        scanner.close();
        try {
            double result = calculate(operation, a, b);
            writeFile(String.valueOf(result));
            printResult(result);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Метод для ввода арифмитических операций и проверки его на валидность.
     *
     * @param scanner
     * @return - возвращает арифмитическую операцию в String формате
     */
    private String getOperation(Scanner scanner) {
        String operation;
        System.out.println("Введите '+' для сложения, '-' для вычитания, '*' для умножения, '/' для деления, " +
                "для возведения в степень '^' : ");
        operation = scanner.nextLine();
        while (checkSymbol(operation, REGEX_OPERATION)) {
            System.out.println("Присутсвуют символы отличающие от арифмитических операций, введите снова");
            operation = scanner.nextLine();
        }
        return operation;
    }

    /**
     * Метод для ввода числа и проверки его на валидность.
     *
     * @param scanner - сканер
     * @param order   - порядковый номер числа
     * @return - возвращает число в double формате
     */
    private double getNumber(Scanner scanner, String order) {
        String number;
        System.out.println("Введите " + order + " число:");
        number = scanner.nextLine();
        while (checkSymbol(number, REGEX_NUMBER)) {
            System.out.println("Присутсвуют символы отличающие от чисел, введите снова");
            number = scanner.nextLine();
        }
        return Double.parseDouble(number);
    }

    private double calculate(String operation, double a, double b) {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) throw new IllegalArgumentException("Деление на ноль!");
                return a / b;
            case "^":
                return Math.pow(a,b);
            default:
                throw new IllegalArgumentException("Неверная операция!");
        }
    }

    private void printResult(double result) {
        System.out.println("Результат: " + result);
    }

    /**
     * Метод для записи результата вычисления в текстовый файл
     * @param value - результат вычисления.
     */
    private void writeFile(String value){
        try {
            FileWriter writer = new FileWriter("history.txt",true);
            writer.write(value + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
    }
}
