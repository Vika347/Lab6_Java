package commands;

import managers.CollectionManager;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

import java.util.List;

/**
 * Команда для фильтрации элементов по автору.
 */
public class FilterLessThanAuthorCommand
        extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду фильтрации по автору.
     *
     * @param collectionManager менеджер коллекции
     */
    public FilterLessThanAuthorCommand(CollectionManager collectionManager) {
        super(CommandType.FILTER_LESS_THAN_AUTHOR);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы, автор которых меньше заданного.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        String authorName = request.getArgument();

        List<LabWork> result = collectionManager.filterLessThanAuthor(authorName);

        if (result.isEmpty()) {

            return new Response(
                    true,
                    "Подходящих элементов не найдено."
            );
        }

        return new Response(
                true,
                "Найденные элементы:",
                result
        );
    }
}