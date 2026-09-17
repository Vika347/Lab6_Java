package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для удаления элементов с ключом больше заданного.
 */
public class RemoveGreaterKeyCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду удаления элементов по ключу.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveGreaterKeyCommand(
            CollectionManager collectionManager
    ) {
        super(CommandType.REMOVE_GREATER_KEY);
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет элементы с ключом больше заданного.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Integer key = request.getKey();

        int before = collectionManager.size();

        collectionManager.removeGreaterKey(key);

        int after = collectionManager.size();

        int removed = before - after;

        return new Response(
                true,
                "Удалено элементов: "
                        + removed
        );
    }
}