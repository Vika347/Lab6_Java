package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

import models.LabWork;

import java.util.List;

/**
 * Команда для вывода элементов коллекции.
 */
public class ShowCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду вывода элементов коллекции.
     *
     * @param collectionManager менеджер коллекции
     */
    public ShowCommand(CollectionManager collectionManager) {
        super(CommandType.SHOW);
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает элементы коллекции, отсортированные по имени.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        List<LabWork> labWorks = collectionManager.getSortedByName();

        if (labWorks.isEmpty()) {
            return new Response(true, "Коллекция пуста.");
        }

        return new Response(true, "Элементы коллекции:", labWorks);
    }
}