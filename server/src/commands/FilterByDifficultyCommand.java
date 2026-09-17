package commands;

import managers.CollectionManager;
import models.Difficulty;
import models.LabWork;
import network.CommandType;
import network.Request;
import network.Response;

import java.util.List;

/**
 * Команда для фильтрации элементов по сложности.
 */
public class FilterByDifficultyCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    /**
     * Создает команду фильтрации по сложности.
     *
     * @param collectionManager менеджер коллекции
     */
    public FilterByDifficultyCommand(CollectionManager collectionManager) {
        super(CommandType.FILTER_BY_DIFFICULTY);
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы с заданной сложностью.
     *
     * @param request запрос клиента
     * @return ответ сервера
     */
    @Override
    public Response execute(Request request) {

        Difficulty difficulty;

        try {

            difficulty = Difficulty.valueOf(request.getArgument().trim().toUpperCase());

        } catch (IllegalArgumentException e) {

            return new Response(
                    false,
                    "Некорректное значение difficulty."
            );
        }

        List<LabWork> result = collectionManager.filterByDifficulty(difficulty);

        if (result.isEmpty()) {

            return new Response(
                    true,
                    "Элементов с difficulty "
                            + difficulty
                            + " не найдено."
            );
        }

        return new Response(
                true,
                "Найденные элементы:",
                result
        );
    }
}