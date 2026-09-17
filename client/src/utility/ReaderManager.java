package utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * Управляет чтением данных из консоли и файлов скриптов.
 */
public class ReaderManager {

    private BufferedReader currentReader;

    private final Stack<BufferedReader> readerStack = new Stack<>();

    private final Stack<String> scriptStack = new Stack<>();

    private final Console console;

    private final InputManager inputManager;

    /**
     * Создает менеджер источников ввода.
     *
     * @param console консоль
     * @param inputManager менеджер ввода
     */
    public ReaderManager(Console console, InputManager inputManager) {
        this.console = console;
        this.inputManager = inputManager;
        this.currentReader = null;
    }

    /**
     * Считывает строку из текущего источника ввода.
     *
     * @return считанная строка или null
     */
    public String readLine() {
        try {
            if (currentReader == null) {
                // Чтение с консоли
                String line = inputManager.readLine();
                if (line == null) {
                    console.println("\nВвод с клавиатуры завершён (Ctrl+D)");
                    return null;
                }
                return line;
            } else {
                // Чтение из файла
                String line = currentReader.readLine();
                if (line == null) {
                    popScript();
                    return readLine();
                }
                return line;
            }
        } catch (IOException e) {
            console.printError("Ошибка чтения: " + e.getMessage());
            return null;
        }
    }

    /**
     * Переключает ввод на файл скрипта.
     *
     * @param fileName имя файла скрипта
     * @return true, если файл успешно открыт
     */
    public boolean pushScript(String fileName) {
        if (scriptStack.contains(fileName)) {
            console.printError("Обнаружена рекурсия скриптов: " + fileName);
            return false;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            readerStack.push(currentReader);
            scriptStack.push(fileName);
            currentReader = reader;
            return true;
        } catch (FileNotFoundException e) {
            console.printError("Файл скрипта не найден: " + fileName);
            return false;
        }
    }

    /**
     * Завершает чтение текущего скрипта.
     */
    public void popScript() {
        try {
            if (currentReader != null) {
                currentReader.close();
            }
        } catch (IOException e) {
            console.printError("Ошибка закрытия файла: " + e.getMessage());
        }
        currentReader = readerStack.isEmpty() ? null : readerStack.pop();
        if (!scriptStack.isEmpty()) scriptStack.pop();
    }

    /**
     * Проверяет, выполняется ли чтение из скрипта.
     *
     * @return true, если активен режим скрипта
     */
    public boolean isScriptMode() {
        return currentReader != null;
    }
}