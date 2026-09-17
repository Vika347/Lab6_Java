package commands;

import managers.CollectionManager;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для замены элемента, если новый элемент меньше старого.
 */
public class ReplaceIfLowerCommand
        extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду замены элемента.
     *
     * @param collectionManager менеджер коллекции
     */
    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        super(CommandType.REPLACE_IF_LOWER);
        this.collectionManager = collectionManager;
    }

    /**
     * Заменяет элемент, если новый элемент меньше старого.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Integer key = request.getKey();

        LabWork labWork = request.getLabWork();

        if (!collectionManager.containsKey(key)) {

            return new Response(
                    false,
                    "Элемент с ключом "
                            + key
                            + " не найден."
            );
        }

        boolean replaced =
                collectionManager.replaceIfLower(key, labWork);

        if (!replaced) {

            return new Response(
                    false, "Новый элемент не меньше старого. "
                    + "Замена не выполнена.");
        }

        return new Response(
                true,
                "Элемент с ключом "
                        + key
                        + " заменён."
        );
    }
}