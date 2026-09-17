package validator;

import network.CommandType; //импортируем enum CommandType
import network.Request;  //импортируем объект Request

/**
 * Проверяет корректность запросов клиента.
 */
public final class RequestValidator { //утилитный класс

    /**
     * Запрещает создание экземпляров класса.
     */
    private RequestValidator() {
    }

    /**
     * Проверяет корректность запроса в зависимости от типа команды.
     *
     * @param request запрос клиента
     * @return true, если запрос корректен
     */
    public static boolean isValid(Request request) {

        if (request == null) {
            return false;
        }

        if (request.getCommandType() == null) {
            return false;
        }

        CommandType commandType = request.getCommandType();

        return switch (commandType) {

            case INFO, SHOW, CLEAR -> true;  //не требуют аргументов

            case REMOVE_KEY, REMOVE_GREATER_KEY -> request.getKey() != null; //требуют ключ

            case INSERT, UPDATE -> request.getKey() != null
                    && LabWorkValidator.isValid(request.getLabWork());

            case REMOVE_GREATER -> LabWorkValidator.isValid(request.getLabWork());

            case REPLACE_IF_LOWER -> request.getKey() != null
                    && LabWorkValidator.isValid(request.getLabWork());

            case FILTER_BY_DIFFICULTY,
                 FILTER_LESS_THAN_AUTHOR,
                 COUNT_BY_AUTHOR -> request.getArgument() != null //дополнительный аргумент
                    && !request.getArgument().trim().isEmpty();
        };
    }
}
