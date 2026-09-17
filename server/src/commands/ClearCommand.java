package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для очистки коллекции.
 */
public class ClearCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду очистки коллекции.
     *
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        super(CommandType.CLEAR);
        this.collectionManager = collectionManager;
    }

    /**
     * Очищает коллекцию.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        collectionManager.clear();

        return new Response(
                true,
                "Коллекция очищена."
        );
    }
}