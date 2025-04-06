package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Клас реализует выполнене базовые арифметические операции.
 */
public class Calculator {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Calculator.class);
    final static String REGEX_OPERATION = "[^*+-\\/^]";
    final static String REGEX_NUMBER = "[^0-9]";

    final static String END = "Для вывода истории операции введите 'h', для отчистки итории введите 'c', " +
            "для выхода введите 'e' :";

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

    public void start() throws IOException {
        LOGGER.info("Приложение запущено");
        Scanner scanner = new Scanner(System.in);
        String operation = getOperation(scanner);
        double numberOne = getNumber(scanner, "первое");
        double numberTwo = getNumber(scanner, "второе");

        try {
            double result = calculate(operation, numberOne, numberTwo);
            writeFile(String.valueOf(result));
            printResult(result);
        } catch (IllegalArgumentException e) {

            LOGGER.error("Ошибка при вычислении", e);
        }
        System.out.println(END);
        endAction(scanner);
        scanner.close();

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
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Неверная операция!");
        }
    }

    private void printResult(double result) {
        System.out.println("Результат: " + result);
    }

    /**
     * Метод для записи результата вычисления в текстовый файл
     *
     * @param value - результат вычисления.
     */
    private void writeFile(String value) {
        try {
            FileWriter writer = new FileWriter("history.txt", true);
            writer.write(value + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
    }

    private void readFile(String fileName) throws IOException {
        System.out.println("---------История---------");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private void clearFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
        System.out.println("История очищена");
    }

    private void endAction(Scanner s) throws IOException {
        String value = s.nextLine();
        while (value.equals("h") && value.equals("c") && value.equals("e")) {
            System.out.println("Введено неправильное значение, введите заново");
            System.out.println(END);
            value = s.nextLine();
        }
        switch (value) {
            case "h":
                readFile("history.txt");
                System.out.println("Калькулятор закрыт");
                break;
            case "c":
                clearFile("history.txt");
                System.out.println("Калькулятор закрыт");
                break;
            case "e":
                System.out.println("Калькулятор закрыт");

        }
    }
}
