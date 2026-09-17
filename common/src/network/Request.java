package network;

import models.LabWork;

import java.io.Serializable;

/**
 * Представляет запрос, отправляемый клиентом серверу.
 */
public class Request implements Serializable {

    private final CommandType commandType;  //тип команды
    private final Integer key;  //ключ для команд, которые его требуют
    private final String argument; //для команд, которым нужен какой-то текст
    private final LabWork labWork;  //сам объект коллекции

    /**
     * Создает запрос клиента.
     *
     * @param commandType тип команды
     * @param key ключ
     * @param argument строковый аргумент
     * @param labWork объект LabWork
     */
    public Request(
            CommandType commandType,
            Integer key,
            String argument,
            LabWork labWork
    ) {
        this.commandType = commandType;
        this.key = key;
        this.argument = argument;
        this.labWork = labWork;
    }

    /**
     * Возвращает тип команды.
     *
     * @return тип команды
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Возвращает ключ.
     *
     * @return ключ
     */
    public Integer getKey() {
        return key;
    }

    /**
     * Возвращает строковый аргумент.
     *
     * @return аргумент
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Возвращает объект LabWork.
     *
     * @return объект LabWork
     */
    public LabWork getLabWork() {
        return labWork;
    }
}