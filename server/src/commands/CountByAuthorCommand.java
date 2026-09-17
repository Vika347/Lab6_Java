package commands;

import managers.CollectionManager;
import network.CommandType;
import network.Request;
import network.Response;

/**
 * Команда для подсчета элементов с заданным автором.
 */
public class CountByAuthorCommand
        extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду подсчета элементов по автору.
     *
     * @param collectionManager менеджер коллекции
     */
    public CountByAuthorCommand(CollectionManager collectionManager) {
        super(CommandType.COUNT_BY_AUTHOR);
        this.collectionManager = collectionManager;
    }

    /**
     * Подсчитывает элементы с заданным автором.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        String authorName = request.getArgument();

        long count = collectionManager.countByAuthor(authorName);

        return new Response(
                true,
                "Количество элементов с автором \""
                        + authorName
                        + "\": "
                        + count
        );
    }
}