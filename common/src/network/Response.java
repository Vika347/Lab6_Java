package network;

import models.LabWork;

import java.io.Serializable;
import java.util.List;

/**
 * Представляет ответ сервера клиенту.
 */
public class Response implements Serializable {

    private final boolean success;
    private final String message;
    private final List<LabWork> labWorks;

    /**
     * Создает ответ сервера с коллекцией объектов.
     *
     * @param success успешность выполнения команды
     * @param message сообщение сервера
     * @param labWorks список объектов LabWork
     */
    public Response(
            boolean success,
            String message,
            List<LabWork> labWorks
    ) {
        this.success = success;
        this.message = message;
        this.labWorks = labWorks;
    }

    /**
     * Создает ответ сервера без списка объектов.
     *
     * @param success успешность выполнения команды
     * @param message сообщение сервера
     */
    public Response(boolean success, String message) {
        this(success, message, null);
    }

    /**
     * Возвращает результат выполнения команды.
     *
     * @return true, если команда выполнена успешно
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Возвращает сообщение сервера.
     *
     * @return сообщение сервера
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает список объектов LabWork.
     *
     * @return список объектов LabWork
     */
    public List<LabWork> getLabWorks() {
        return labWorks;
    }
}