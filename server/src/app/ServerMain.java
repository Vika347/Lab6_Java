package app;
import commands.*;


import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import network.Request;
import network.RequestHandler;
import network.RequestReader;
import network.Response;
import network.ResponseSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

/**
 * Главный класс серверного приложения.
 */
public class ServerMain {

    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final int PORT = 6666;

    /**
     * Запускает сервер, обрабатывает запросы клиентов и серверные команды.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        String fileName = System.getenv("LAB_FILE");

        if (fileName == null || fileName.isBlank()) {
            logger.severe("Переменная окружения LAB_FILE не задана.");
            return;
        }

        FileManager fileManager = new FileManager(fileName);

        CollectionManager collectionManager = new CollectionManager(fileManager);

        collectionManager.loadCollection();

        CommandManager commandManager = new CommandManager();
        commandManager.register(new UpdateCommand(collectionManager));

        commandManager.register(new RemoveKeyCommand(collectionManager));

        commandManager.register(new ClearCommand(collectionManager));

        commandManager.register(new RemoveGreaterCommand(collectionManager));

        commandManager.register(new RemoveGreaterKeyCommand(collectionManager));

        commandManager.register(new ReplaceIfLowerCommand(collectionManager));

        commandManager.register(new FilterByDifficultyCommand(collectionManager));

        commandManager.register(new FilterLessThanAuthorCommand(collectionManager));

        commandManager.register(new CountByAuthorCommand(collectionManager));

        commandManager.register(new InfoCommand(collectionManager));

        commandManager.register(new ShowCommand(collectionManager));

        commandManager.register(new InsertCommand(collectionManager));

        RequestHandler requestHandler = new RequestHandler(commandManager);

        RequestReader requestReader = new RequestReader();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try (
                DatagramSocket socket = new DatagramSocket(PORT)
        ) {

            socket.setSoTimeout(500);

            ResponseSender responseSender = new ResponseSender(socket);

            logger.info("Сервер запущен на порту " + PORT);

            boolean running = true;

            while (running) {

                // Проверяем серверную консоль
                if (consoleReader.ready()) {

                    String command = consoleReader.readLine();

                    if (command != null) {

                        command = command.trim();

                        switch (command) {

                            case "save" -> {

                                collectionManager.saveCollection();
                                logger.info("Коллекция сохранена.");
                            }

                            case "exit" -> {

                                logger.info("Завершение работы сервера.");
                                running = false;
                            }

                            default -> {

                                logger.warning("Неизвестная серверная команда: " + command);
                            }
                        }
                    }
                }

                if (!running) {
                    break;
                }

                byte[] buffer = new byte[65535];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                try {

                    socket.receive(packet);

                } catch (SocketTimeoutException e) {

                    continue;
                }

                logger.info(
                        "Получен запрос от "
                                + packet.getAddress()
                                + ":"
                                + packet.getPort()
                );

                Response response;

                try {

                    Request request = requestReader.readRequest(packet);

                    response = requestHandler.handle(request);

                } catch (Exception e) {

                    logger.warning(
                            "Ошибка обработки запроса: "
                                    + e.getMessage()
                    );

                    response =
                            new Response(false, "Ошибка обработки запроса.");
                }

                try {

                    responseSender.sendResponse(
                            response,
                            packet.getAddress(),
                            packet.getPort()
                    );

                    logger.info("Ответ отправлен клиенту.");

                } catch (Exception e) {

                    logger.warning("Ошибка отправки ответа: " + e.getMessage());
                }
            }

        } catch (Exception e) {

            logger.severe("Ошибка сервера: " + e.getMessage());

        } finally {

            collectionManager.saveCollection();

            logger.info("Коллекция сохранена перед завершением.");
        }
    }
}