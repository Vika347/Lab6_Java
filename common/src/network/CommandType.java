package network;
import java.io.Serializable;

/**
 * Перечисляет типы команд, отправляемых клиентом серверу.
 */

public enum CommandType implements Serializable {
    INFO,
    SHOW,
    INSERT,
    UPDATE,
    REMOVE_KEY,
    CLEAR,
    REMOVE_GREATER,
    REMOVE_GREATER_KEY,
    REPLACE_IF_LOWER,
    FILTER_BY_DIFFICULTY,
    FILTER_LESS_THAN_AUTHOR,
    COUNT_BY_AUTHOR
}
