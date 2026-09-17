package commands;

import managers.CollectionManager;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для обновления элемента коллекции.
 */
public class UpdateCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду обновления элемента.
     *
     * @param collectionManager менеджер коллекции
     */
    public UpdateCommand(CollectionManager collectionManager) {
        super(CommandType.UPDATE);
        this.collectionManager = collectionManager;
    }

    /**
     * Обновляет элемент коллекции по заданному ключу.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Integer key = request.getKey();
        LabWork labWork = request.getLabWork();

        boolean updated = collectionManager.update(key, labWork);

        if (!updated) {
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
                        + " успешно обновлён."
        );
    }
}