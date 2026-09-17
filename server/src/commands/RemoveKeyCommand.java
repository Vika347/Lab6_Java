package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для удаления элемента по ключу.
 */
public class RemoveKeyCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду удаления элемента по ключу.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveKeyCommand(CollectionManager collectionManager) {
        super(CommandType.REMOVE_KEY);
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет элемент с заданным ключом.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Integer key = request.getKey();

        boolean removed =
                collectionManager.remove(key);

        if (!removed) {
            return new Response(
                    false,
                    "Элемент с ключом "
                            + key
                            + " не найден."
            );
        }

        return new Response(
                true,
                "Элемент с ключом "
                        + key
                        + " удалён."
        );
    }
}