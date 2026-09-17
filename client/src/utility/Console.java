package utility;

/**
 * Обеспечивает работу с консолью.
 */
public class Console {

    /**
     * Выводит сообщение в консоль.
     *
     * @param message сообщение для вывода
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Выводит сообщение об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public void printError(String message) {
        System.err.println("[ОШИБКА] " + message);
    }

    /**
     * Выводит сообщение без перехода на новую строку.
     *
     * @param message сообщение для вывода
     */
    public void print(String message) {
        System.out.print(message);
    }
}