package network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

/**
 * Читает и десериализует запросы клиента.
 */
public class RequestReader {

    /**
     * Преобразует полученный UDP-пакет в объект Request.
     *
     * @param packet полученный UDP-пакет
     * @return десериализованный запрос
     * @throws IOException если произошла ошибка чтения
     * @throws ClassNotFoundException если класс объекта не найден
     */
    public Request readRequest(DatagramPacket packet)
            throws IOException, ClassNotFoundException {

        ByteArrayInputStream byteStream = new ByteArrayInputStream(
                packet.getData(),
                0,
                packet.getLength()
        );

        try (ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {

            return (Request) objectStream.readObject();
        }
    }
}