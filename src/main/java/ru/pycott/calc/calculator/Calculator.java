package ru.pycott.calc.calculator;

import java.util.*;

public class Calculator {
    /**
     * Принимает строку, содержащую арифметическое выражение и вычисляет его
     */
    // Основные математические операции и их приоритеты.
    private static final Map<String, Integer> MAIN_MATH_OPERATIONS;
    static {
        MAIN_MATH_OPERATIONS = new HashMap<>();
        MAIN_MATH_OPERATIONS.put("^", 1);
        MAIN_MATH_OPERATIONS.put("*", 2);
        MAIN_MATH_OPERATIONS.put("/", 2);
        MAIN_MATH_OPERATIONS.put("+", 3);
        MAIN_MATH_OPERATIONS.put("-", 3);
    }
    private static final String leftBracket = "(";
    private static final String rightBracket = ")";

    /**
     * Преобразует выражение из инфиксной нотации в обратную польскую нотацию (ОПН) по алгоритму Сортировочная
     * станция Эдскера Дейкстры. *
     *
     * @param expression выражение в инфиксной форме.
     * @return преобразованное выражение в ОПН.
     */
    private static String sortingStation(String expression) throws ExpressionException {
        if (expression == null || expression.length() == 0)
            throw new ExpressionException("Expression isn't specified.");
        boolean openedBracket = false;
        boolean operatorFound = false;

        // Выходная строка, разбитая на "символы" - операции и операнды
        List<String> out = new ArrayList<>();
        // Стек операций.
        Stack<String> stack = new Stack<>();

        // Удаление пробелов из выражения.
        expression = expression.replace(" ", "");

        // Множество "символов", не являющихся операндами (операции и скобки).
        Set<String> operationSymbols = new HashSet<>(MAIN_MATH_OPERATIONS.keySet());
        operationSymbols.add(leftBracket);
        operationSymbols.add(rightBracket);

        // Индекс, на котором закончился разбор строки на прошлой итерации.
        int index = 0;
        // Признак необходимости поиска следующего элемента.
        boolean findNext = true;

        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // Поиск следующего оператора или скобки.
            for (String operation : operationSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }
            // Оператор не найден.
            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {
                operatorFound = true;
                // Если оператору или скобке предшествует операнд, добавляем его в выходную строку.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }
                // Обработка операторов и скобок.
                // Открывающая скобка.
                switch (nextOperation) {
                    case leftBracket:
                        openedBracket = true;
                        stack.push(nextOperation);
                        break;
                    case rightBracket:
                        openedBracket = false;
                        while (!stack.peek().equals(leftBracket)) {
                            out.add(stack.pop());
                            if (stack.empty()) {
                                throw new ExpressionException("Unmatched brackets");
                            }
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                                (MAIN_MATH_OPERATIONS.get(nextOperation) >= MAIN_MATH_OPERATIONS.get(stack.peek()))) {
                            out.add(stack.pop());
                        }
                        stack.push(nextOperation);
                        break;
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }
        if (openedBracket) {
            throw new ExpressionException("Unmatched brackets");
        }
        if (!operatorFound){
            throw new ExpressionException("Did not find supported operators");
        }

        // Добавление в выходную строку операндов после последнего операнда.
        if (index != expression.length()) {
            out.add(expression.substring(index));
        }
        // Пробразование выходного списка к выходной строке.
        while (!stack.empty()){
            out.add(stack.pop());
        }

        StringBuilder result = new StringBuilder();
        if (!out.isEmpty())
            result.append(out.remove(0));
        while (!out.isEmpty())
            result.append(" ").append(out.remove(0));
        return result.toString();
    }

    /**
     * Вычисляет значение выражения, записанного в инфиксной нотации. Выражение может содержать скобки, числа с
     * плавающей точкой, 5 основных математических операндов.
     *
     * @param expression выражение.
     * @return результат вычисления.
     */
    public static Double calculateExpression(String expression) throws ExpressionException {
        String rpn = sortingStation(expression);
        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack<Double> stack = new Stack<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // Операнд.
            if (!MAIN_MATH_OPERATIONS.keySet().contains(token)) {
                stack.push(new Double(token));
            } else {
                Double operand2 = stack.pop();
                Double operand1 = stack.empty() ? 0.0 : stack.pop();
                switch (token) {
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "^":
                        stack.push(Math.pow(operand1, operand2));
                        break;
                }
            }
        }
        if (stack.size() != 1)
            throw new ExpressionException("Expression syntax error.");
        return stack.pop();
    }
}