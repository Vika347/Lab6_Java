package commands;

import network.CommandType;

/**
 * Базовый класс для серверных команд.
 */
public abstract class AbstractCommand implements Command {

    private final CommandType type;

    /**
     * Создает команду с указанным типом.
     *
     * @param type тип команды
     */
    protected AbstractCommand(CommandType type) {
        this.type = type;
    }

    /**
     * Возвращает тип команды.
     *
     * @return тип команды
     */
    @Override
    public CommandType getType() {
        return type;
    }
}