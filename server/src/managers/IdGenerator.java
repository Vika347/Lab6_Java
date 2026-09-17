package managers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Генерирует уникальные идентификаторы для объектов LabWork.
 */
public class IdGenerator {

    private static final AtomicInteger counter =
            new AtomicInteger(0);

    /**
     * Возвращает следующий уникальный идентификатор.
     *
     * @return следующий идентификатор
     */
    public static int nextId() {
        return counter.incrementAndGet();
    }

    /**
     * Устанавливает начальное значение счетчика.
     *
     * @param maxId максимальный существующий идентификатор
     */
    public static void init(int maxId) {
        counter.set(maxId);
    }
}