package app;

import managers.ClientCommandManager;
import managers.NetworkManager;
import utility.Console;
import utility.InputManager;
import utility.LabWorkAsker;
import utility.ReaderManager;


/**
 * Главный класс клиентского приложения.
 */
public class ClientMain {

    /**
     * Запускает клиент и обрабатывает ввод команд.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        Console console = new Console();

        String host = args.length > 0
                ? args[0]
                : "localhost";

        int port = args.length > 1
                ? Integer.parseInt(args[1])
                : 6666;

        InputManager inputManager = new InputManager();

        ReaderManager readerManager =
                new ReaderManager(console, inputManager);

        try {

            NetworkManager networkManager =
                    new NetworkManager(host, port);

            LabWorkAsker labWorkAsker =
                    new LabWorkAsker(readerManager, console);

            ClientCommandManager commandManager =
                    new ClientCommandManager(
                            networkManager,
                            labWorkAsker,
                            console,
                            readerManager
                    );

            console.println(
                    "Клиент запущен. Сервер: "
                            + host
                            + ":"
                            + port
            );

            while (true) {

                console.print("> ");

                String line = readerManager.readLine();

                if (line == null) {
                    break;
                }

                commandManager.execute(line);
            }

        } catch (NumberFormatException e) {

            console.printError("Порт должен быть целым числом.");

        } catch (Exception e) {

            console.printError(
                    "Ошибка запуска клиента: "
                            + e.getMessage()
            );
        }
    }
}