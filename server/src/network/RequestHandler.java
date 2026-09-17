package network;

import managers.CommandManager;
import validator.RequestValidator;

/**
 * Обрабатывает запросы, полученные сервером.
 */
public class RequestHandler {

    private final CommandManager commandManager;

    /**
     * Создает обработчик запросов.
     *
     * @param commandManager менеджер команд
     */
    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Проверяет запрос и передает его на выполнение.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    public Response handle(Request request) {

        if (!RequestValidator.isValid(request)) {
            return new Response(false,
                    "Сервер получил некорректный запрос."
            );
        }

        return commandManager.execute(request);
    }
}