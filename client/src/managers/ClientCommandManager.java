package managers;

import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;
import utility.Console;
import utility.LabWorkAsker;
import utility.ReaderManager;
import validator.RequestValidator;

import java.net.SocketTimeoutException;

/**
 * Управляет обработкой команд на стороне клиента.
 */
public class ClientCommandManager {

    private final NetworkManager networkManager;
    private final LabWorkAsker labWorkAsker;
    private final Console console;
    private final ReaderManager readerManager;

    /**
     * Создает менеджер клиентских команд.
     *
     * @param networkManager менеджер сетевого взаимодействия
     * @param labWorkAsker менеджер создания объектов LabWork
     * @param console консоль
     * @param readerManager менеджер чтения данных
     */
    public ClientCommandManager(NetworkManager networkManager, LabWorkAsker labWorkAsker, Console console, ReaderManager readerManager) {
        this.networkManager = networkManager;
        this.labWorkAsker = labWorkAsker;
        this.console = console;
        this.readerManager = readerManager;
    }

    /**
     * Выполняет введенную клиентом команду.
     *
     * @param input введенная команда
     */
    public void execute(String input) {

        if (input == null || input.isBlank()) {
            return;
        }

        String[] parts = input.trim().split("\\s+", 2);

        String commandName = parts[0].toLowerCase();

        String argument = parts.length > 1
                ? parts[1].trim()
                : null;

        try {

            Request request = createRequest(commandName, argument);

            if (request == null) {
                return;
            }

            //Клиенская валидация
            if (!RequestValidator.isValid(request)) {

                console.printError("Некорректные данные команды.");

                return;
            }

            Response response = networkManager.sendRequest(request);

            printResponse(response);

        } catch (SocketTimeoutException e) {

            console.printError("Сервер временно недоступен.");

        } catch (NumberFormatException e) {

            console.printError("Ключ должен быть целым числом.");

        } catch (Exception e) {

            console.printError("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Создает запрос в зависимости от команды.
     *
     * @param commandName имя команды
     * @param argument аргумент команды
     * @return созданный запрос или null
     */
    private Request createRequest(String commandName, String argument) {

        return switch (commandName) {

            case "info" ->
                    new Request(
                            CommandType.INFO,
                            null,
                            null,
                            null
                    );

            case "show" ->
                    new Request(
                            CommandType.SHOW,
                            null,
                            null,
                            null
                    );

            case "insert" ->
                    createLabWorkWithKeyRequest(
                            CommandType.INSERT,
                            argument
                    );

            case "update" ->
                    createLabWorkWithKeyRequest(
                            CommandType.UPDATE,
                            argument
                    );

            case "remove_key" ->
                    createKeyRequest(
                            CommandType.REMOVE_KEY,
                            argument
                    );

            case "clear" ->
                    new Request(
                            CommandType.CLEAR,
                            null,
                            null,
                            null
                    );

            case "remove_greater" ->
                    createLabWorkRequest(
                            CommandType.REMOVE_GREATER
                    );

            case "remove_greater_key" ->
                    createKeyRequest(
                            CommandType.REMOVE_GREATER_KEY,
                            argument
                    );

            case "replace_if_lower" ->
                    createLabWorkWithKeyRequest(
                            CommandType.REPLACE_IF_LOWER,
                            argument
                    );

            case "filter_by_difficulty" ->
                    createArgumentRequest(
                            CommandType.FILTER_BY_DIFFICULTY,
                            argument
                    );

            case "filter_less_than_author" ->
                    createArgumentRequest(
                            CommandType.FILTER_LESS_THAN_AUTHOR,
                            argument
                    );

            case "count_by_author" ->
                    createArgumentRequest(
                            CommandType.COUNT_BY_AUTHOR,
                            argument
                    );

            /*
             * Локальная клиентская команда.
             * На сервер не отправляется.
             */
            case "help" -> {

                printHelp();

                yield null;
            }

            /*
             * execute_script тоже выполняется
             * клиентом.
             */
            case "execute_script" -> {

                executeScript(argument);

                yield null;
            }

            /*
             * exit завершает только клиент.
             * Сервер продолжает работать.
             */
            case "exit" -> {

                console.println("Завершение работы клиента.");

                System.exit(0);

                yield null;
            }

            /*
             * save клиенту недоступен.
             */
            case "save" -> {

                console.printError("Команда save доступна только на сервере.");

                yield null;
            }

            default -> {

                console.printError("Неизвестная команда: " + commandName);

                yield null;
            }
        };
    }

    /**
     * Создает запрос с ключом.
     *
     * @param commandType тип команды
     * @param argument значение ключа
     * @return созданный запрос
     */
    private Request createKeyRequest(CommandType commandType, String argument) {

        if (argument == null || argument.isBlank()) {

            console.printError("Необходимо указать ключ.");

            return null;
        }

        Integer key = Integer.parseInt(argument.trim());

        return new Request(commandType, key, null, null);
    }

    /**
     * Создает запрос с ключом и объектом LabWork.
     *
     * @param commandType тип команды
     * @param argument значение ключа
     * @return созданный запрос
     */
    private Request createLabWorkWithKeyRequest(CommandType commandType, String argument) {

        if (argument == null || argument.isBlank()) {

            console.printError("Необходимо указать ключ.");

            return null;
        }

        Integer key = Integer.parseInt(argument.trim());

        LabWork labWork = labWorkAsker.askLabWork();

        if (labWork == null) {

            console.printError("Не удалось создать LabWork.");

            return null;
        }

        return new Request(
                commandType,
                key,
                null,
                labWork
        );
    }

    /**
     * Создает запрос с объектом LabWork.
     *
     * @param commandType тип команды
     * @return созданный запрос
     */
    private Request createLabWorkRequest(
            CommandType commandType
    ) {

        LabWork labWork = labWorkAsker.askLabWork();

        if (labWork == null) {

            console.printError("Не удалось создать LabWork.");

            return null;
        }

        return new Request(
                commandType,
                null,
                null,
                labWork
        );
    }

    /**
     * Создает запрос со строковым аргументом.
     *
     * @param commandType тип команды
     * @param argument аргумент команды
     * @return созданный запрос
     */
    private Request createArgumentRequest(
            CommandType commandType,
            String argument
    ) {

        if (argument == null || argument.isBlank()) {

            console.printError("Необходимо указать аргумент команды.");

            return null;
        }

        return new Request(
                commandType,
                null,
                argument.trim(),
                null
        );
    }

    /**
     * Выводит ответ сервера.
     *
     * @param response ответ сервера
     */
    private void printResponse(
            Response response
    ) {

        if (response == null) {

            console.printError("Получен пустой ответ от сервера.");

            return;
        }

        if (response.isSuccess()) {

            if (response.getMessage() != null) {

                console.println(response.getMessage());
            }

        } else {

            console.printError(response.getMessage());
        }

        if (response.getLabWorks() != null) {

            response.getLabWorks()
                    .forEach(labWork -> console.println(labWork.toString()));
        }
    }

    /**
     * Выполняет команды из файла.
     *
     * @param fileName имя файла скрипта
     */
    private void executeScript(
            String fileName
    ) {

        if (fileName == null || fileName.isBlank()) {

            console.printError("Необходимо указать имя файла.");

            return;
        }

        if (!readerManager.pushScript(fileName)) {
            return;
        }



        labWorkAsker.setInteractiveMode(false);

        while (readerManager.isScriptMode()) {

            String line = readerManager.readLine();

            if (line == null) {
                break;
            }

            if (line.isBlank()) {
                continue;
            }

            console.println(
                    "> " + line
            );

            execute(line);
        }

        labWorkAsker.setInteractiveMode(true);
    }

    /**
     * Выводит справку по доступным командам.
     */
    private void printHelp() {

        console.println("""
            help : вывести справку по командам
            info : вывести информацию о коллекции
            show : вывести все элементы коллекции
            insert <key> : добавить новый элемент
            update <key> : обновить элемент по ключу
            remove_key <key> : удалить элемент по ключу
            clear : очистить коллекцию
            remove_greater : удалить элементы, превышающие заданный
            remove_greater_key <key> : удалить элементы с ключом больше заданного
            replace_if_lower <key> : заменить элемент, если новый меньше
            filter_by_difficulty <difficulty> : вывести элементы с заданной сложностью
            filter_less_than_author <author> : вывести элементы с автором меньше заданного
            count_by_author <author> : посчитать элементы с заданным автором
            execute_script <file_name> : выполнить команды из файла
            exit : завершить (клиент)
            """);
    }
}
