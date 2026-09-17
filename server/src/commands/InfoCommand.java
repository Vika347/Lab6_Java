package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для вывода информации о коллекции.
 */
public class InfoCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду вывода информации о коллекции.
     *
     * @param collectionManager менеджер коллекции
     */
    public InfoCommand(CollectionManager collectionManager) {
        super(CommandType.INFO);
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает информацию о коллекции.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        String message =
                "Тип коллекции: "
                        + collectionManager.getCollection()
                        .getClass()
                        .getSimpleName()
                        + "\n"
                        + "Дата инициализации: "
                        + collectionManager.getInitializationDate()
                        + "\n"
                        + "Количество элементов: "
                        + collectionManager.size();

        return new Response(true, message);
    }
}