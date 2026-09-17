package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Управляет вводом данных с консоли.
 */
public class InputManager {

    private final Scanner scanner;

    /**
     * Создает менеджер ввода.
     */
    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Считывает строку из консоли.
     *
     * @return считанная строка или null, если ввод завершен
     */
    public String readLine() {
        try {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            } else {
                return null;
            }
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}