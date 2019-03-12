package ru.mail.polis.open.task3;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

/**
 * Для проверки класса на корректность следует использовать тесты.
 * Команда {@code ./gradlew clean build} должна завершаться корректно.
 * <p>
 * При решении задания следует обратить внимание на использование ключевых слов {@code final} и {@code static}
 * <p>
 * В результирующим PR нужно предоставить
 * - Код решения
 * Реализовать все методы
 * Весь код внутри CorrectBracketSequencePredicate
 * Сигнатуры класса, конструктора и метода следует оставить неизменными
 * Комментарии оставить неизменными
 * Можно добавлять дополнительные методы
 * - Тесты
 * Внутри package ru.mail.polis.open.task3
 * В нём будут видны public / protected / package_private методы
 */
public final class CorrectBracketSequenceChecker {

    private CorrectBracketSequenceChecker() {
        quantityOfFailedAttempts = 0;
        quantityOfSuccessfulAttempts = 0;
        /* todo: append code if needed */
    }

    private static final char OPENED_ROUND_BRACKET = '(';
    private static final char CLOSED_ROUND_BRACKET = ')';
    private static final char OPENED_FIGURE_BRACKET = '{';
    private static final char CLOSED_FIGURE_BRACKET = '}';
    private static final char OPENED_SQUARE_BRACKET = '[';
    private static final char CLOSED_SQUARE_BRACKET = ']';

    static Integer quantityOfSuccessfulAttempts;
    static Integer quantityOfFailedAttempts;
    static String lastCorrectSequence;
    static Stack<Character> stackOfCharacter = new Stack<>();

    /**
     * Метод проверяющий скобочную последовательность на правильность.
     * <p>
     * Пустая строка
     * — правильная скобочная последовательность.
     * Правильная скобочная последовательность, взятая в скобки одного типа
     * — правильная скобочная последовательность.
     * Правильная скобочная последовательность,
     * к которой слева или справа приписана правильная скобочная последовательность
     * — правильная скобочная последовательность.
     * <p>
     * Последовательности из больше чем ста символов или с символами не скобок — некорректные.
     * <p>
     * Скобки бывают:
     * 1. Круглые '(', ')'
     * 2. Квадратные '[', ']'
     * 3. Фигурные '{', '}'
     *
     * @param sequence — входная строка
     * @return {@code true} — если скобочная последовательность корректна и {@code false} иначе
     * @throws IllegalArgumentException если в строке содержатся символы, не являющиеся скобками
     *                                  или если входная строка содержит больше ста символов
     */
    public static boolean checkSequence(@Nullable String sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if ((sequence.charAt(i) == OPENED_ROUND_BRACKET) ||
                    (sequence.charAt(i) == OPENED_FIGURE_BRACKET) ||
                    (sequence.charAt(i) == OPENED_SQUARE_BRACKET)) {

                stackOfCharacter.push(sequence.charAt(i));
            } else if ((sequence.charAt(i) == CLOSED_ROUND_BRACKET) ||
                    (sequence.charAt(i) == CLOSED_FIGURE_BRACKET) ||
                    (sequence.charAt(i) == CLOSED_SQUARE_BRACKET)) {

                switch (sequence.charAt(i)) {
                    case CLOSED_ROUND_BRACKET : {
                        if ((stackOfCharacter.pop() != OPENED_ROUND_BRACKET) ||
                                (stackOfCharacter.empty())) {
                            quantityOfFailedAttempts++;
                            return false;
                        }
                        break;
                    } case CLOSED_FIGURE_BRACKET : {
                        if ((stackOfCharacter.pop() != OPENED_FIGURE_BRACKET) ||
                                (stackOfCharacter.empty())) {
                            quantityOfFailedAttempts++;
                            return false;
                        }
                        break;
                    } case CLOSED_SQUARE_BRACKET : {
                        if ((stackOfCharacter.pop() != OPENED_SQUARE_BRACKET) ||
                                (stackOfCharacter.empty())) {
                            quantityOfFailedAttempts++;
                            return false;
                        }
                    }
                }
            } else if (sequence == "") {
                lastCorrectSequence = sequence;
                quantityOfSuccessfulAttempts++;
                return true;
            }
            else {
                throw new IllegalArgumentException("Unknown a symbol : " + sequence.charAt(i));
            }
        }

        lastCorrectSequence = sequence;
        quantityOfSuccessfulAttempts++;
        return true;
    }

    /**
     * Возвращает количество проверок, в результате которых выяснилось,
     * что входная строка является правильной скобочной последовательностью.
     *
     * @return количество удачных проверок
     */
    public static int getSuccessChecksCount() {
        return quantityOfSuccessfulAttempts;
    }

    /**
     * Возвращает количество проверок, в результате которых выяснилось,
     * что входная строка не является правильной скобочной последовательностью.
     *
     * @return количество неудачных проверок
     */
    public static int getFailChecksCount() {
        return quantityOfFailedAttempts;
    }

    /**
     * Возвращает последнюю последовательность, проверка которой завершилась успешно.
     *
     * @return последняя правильная скобочная последовательность или null если такой ещё не было
     */
    public static @Nullable String getLastSuccessSequence() {
        if (quantityOfSuccessfulAttempts > 0) {
            return lastCorrectSequence;
        } else {
            return null;
        }
    }
}
