package managers;

import commands.Command;
import network.CommandType;
import network.Request;
import network.Response;

import java.util.EnumMap;
import java.util.Map;

/**
 * Управляет регистрацией и выполнением серверных команд.
 */
public class CommandManager {

    private final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    /**
     * Регистрирует команду.
     *
     * @param command команда
     */
    public void register(Command command) {
        commands.put(command.getType(), command);
    }

    /**
     * Выполняет команду из запроса клиента.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    public Response execute(Request request) {

        Command command = commands.get(request.getCommandType());

        if (command == null) {
            return new Response(false, "Команда не зарегистрирована.");
        }

        try {
            return command.execute(request);

        } catch (Exception e) {
            return new Response(false, "Ошибка выполнения команды: " + e.getMessage());
        }
    }
}