package commands;

import network.CommandType;
import network.Request;
import network.Response;

/**
 * Представляет серверную команду.
 */
public interface Command {

    /**
     * Возвращает тип команды.
     *
     * @return тип команды
     */
    CommandType getType();

    /**
     * Выполняет команду.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    Response execute(Request request);
}