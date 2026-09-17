package commands;

import managers.CollectionManager;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для удаления элементов, превышающих заданный.
 */
public class RemoveGreaterCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду удаления превышающих элементов.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveGreaterCommand(
            CollectionManager collectionManager
    ) {
        super(CommandType.REMOVE_GREATER);
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет элементы, превышающие заданный.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        LabWork labWork = request.getLabWork();

        int before = collectionManager.size();

        collectionManager.removeGreater(labWork);

        int after = collectionManager.size();

        int removed = before - after;

        return new Response(
                true,
                "Удалено элементов: "
                        + removed
        );
    }
}