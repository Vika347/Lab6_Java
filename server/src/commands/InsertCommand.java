package commands;

import managers.CollectionManager;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для добавления элемента в коллекцию.
 */
public class InsertCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду добавления элемента.
     *
     * @param collectionManager менеджер коллекции
     */
    public InsertCommand(CollectionManager collectionManager) {
        super(CommandType.INSERT);
        this.collectionManager = collectionManager;
    }

    /**
     * Добавляет новый элемент в коллекцию по заданному ключу.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Integer key = request.getKey();
        LabWork labWork = request.getLabWork();

        if (collectionManager.containsKey(key)) {

            return new Response(false, "Элемент с ключом "
                    + key
                    + " уже существует."
            );
        }

        collectionManager.insert(key, labWork);

        return new Response(true,
                "Элемент успешно добавлен. "
                        + "Ключ: "
                        + key
                        + ", id: "
                        + labWork.getId()
        );
    }
}