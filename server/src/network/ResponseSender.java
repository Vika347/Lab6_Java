package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Отправляет ответы сервера клиенту.
 */
public class ResponseSender {

    private final DatagramSocket socket;

    /**
     * Создает объект для отправки ответов.
     *
     * @param socket UDP-сокет сервера
     */
    public ResponseSender(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * Отправляет ответ клиенту по UDP.
     *
     * @param response ответ сервера
     * @param address адрес клиента
     * @param port порт клиента
     * @throws IOException если произошла ошибка отправки
     */
    public void sendResponse(Response response, InetAddress address, int port) throws IOException {

        byte[] data = serialize(response);

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

        socket.send(packet);
    }

    /**
     * Сериализует ответ в массив байтов.
     *
     * @param response ответ сервера
     * @return сериализованный массив байтов
     * @throws IOException если произошла ошибка сериализации
     */
    private byte[] serialize(Response response)
            throws IOException {

        try (
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

                ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)
        ) {

            objectStream.writeObject(response);
            objectStream.flush();

            return byteStream.toByteArray();
        }
    }
}