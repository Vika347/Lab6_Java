package managers;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import network.Request;
import network.Response;

//классы для сериализации
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetSocketAddress; //адрес сервера
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel; //сетевой канал.

/**
 * Управляет сетевым взаимодействием клиента с сервером.
 */
public class NetworkManager {

    private final DatagramChannel channel;
    private final InetSocketAddress serverAddress;

    /**
     * Создает менеджер сетевого взаимодействия.
     *
     * @param host адрес сервера
     * @param port порт сервера
     * @throws Exception если не удалось открыть сетевой канал
     */
    public NetworkManager(String host, int port) throws Exception {
        this.channel = DatagramChannel.open(); //открываем UDP-канал
        this.channel.configureBlocking(false); //неблокирующий режим

        this.serverAddress = new InetSocketAddress(host, port);
    }

    /**
     * Отправляет запрос серверу и получает ответ.
     *
     * @param request запрос клиента
     * @return ответ сервера
     * @throws Exception если произошла ошибка сетевого взаимодействия
     */
    public Response sendRequest(Request request) throws Exception {

        byte[] requestBytes = serialize(request); //сериализация

        ByteBuffer sendBuffer = ByteBuffer.wrap(requestBytes); //создает буфер и заворачивает в него готовый массив

        channel.send(sendBuffer, serverAddress); //отправка UDP-датаграммы

        return receiveResponse();
    }

    /**
     * Получает ответ от сервера.
     *
     * @return ответ сервера
     * @throws Exception если ответ не получен или произошла ошибка чтения
     */
    private Response receiveResponse() throws Exception {

        try (Selector selector = Selector.open()) {

            channel.register(selector, SelectionKey.OP_READ); //данные готовы к чтению

            int readyChannels = selector.select(5000);

            if (readyChannels == 0) {
                throw new SocketTimeoutException("Сервер не ответил в течение 5 секунд");
            }

            ByteBuffer receiveBuffer = ByteBuffer.allocate(65535);

            var address = channel.receive(receiveBuffer);

            if (address == null) {
                throw new IOException("Не удалось получить ответ от сервера");
            }

            receiveBuffer.flip(); //переключает ByteBuffer из режима записи в режим для чтения

            byte[] data = new byte[receiveBuffer.remaining()];

            receiveBuffer.get(data); //копируем в обычный массив

            return deserialize(data);
        }
    }

    /**
     * Сериализует запрос в массив байтов.
     *
     * @param request запрос клиента
     * @return сериализованный массив байтов
     * @throws Exception если произошла ошибка сериализации
     */
    private byte[] serialize(Request request) throws Exception {

        try(ByteArrayOutputStream byteStream = new ByteArrayOutputStream();//куда будут записываться сер.байты
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) { //умеет превращать объекты в байты

            objectStream.writeObject(request);
            objectStream.flush();

            return byteStream.toByteArray(); //байты возвращаем, как массив
        }
    }

    /**
     * Десериализует массив байтов в объект Response.
     *
     * @param data массив байтов
     * @return ответ сервера
     * @throws Exception если произошла ошибка десериализации
     */
    private Response deserialize(byte[] data) throws Exception {

        try(ByteArrayInputStream byteStream = new ByteArrayInputStream(data);//поток из которого байты можно читать
            ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {

            return (Response) objectStream.readObject();
        }
    }

}