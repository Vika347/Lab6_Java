package managers;

import models.Difficulty;
import models.LabWork;

import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Управляет коллекцией объектов LabWork.
 */
public class CollectionManager {

    private final Hashtable<Integer, LabWork> collection;
    private final Date initializationDate;
    private final FileManager fileManager;

    /**
     * Создает менеджер коллекции.
     *
     * @param fileManager менеджер работы с файлом
     */
    public CollectionManager(FileManager fileManager) {
        this.collection = new Hashtable<>();
        this.initializationDate = new Date();
        this.fileManager = fileManager;
    }

    /**
     * Загружает коллекцию из файла.
     */
    public void loadCollection() {
        collection.clear();
        collection.putAll(fileManager.readCollection());

        updateIdGenerator();
    }

    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection() {
        fileManager.writeCollection(collection);
    }

    /**
     * Обновляет генератор идентификаторов.
     */
    private void updateIdGenerator() {

        int maxId = collection.values()
                .stream()
                .mapToInt(LabWork::getId)
                .max()
                .orElse(0);

        IdGenerator.init(maxId);
    }

    /**
     * Возвращает коллекцию.
     *
     * @return коллекция LabWork
     */
    public Hashtable<Integer, LabWork> getCollection() {
        return collection;
    }

    /**
     * Возвращает дату инициализации коллекции.
     *
     * @return дата инициализации
     */
    public Date getInitializationDate() {
        return initializationDate;
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return количество элементов
     */
    public int size() {
        return collection.size();
    }

    /**
     * Проверяет наличие ключа в коллекции.
     *
     * @param key ключ
     * @return true, если ключ существует
     */
    public boolean containsKey(Integer key) {
        return collection.containsKey(key);
    }

    /**
     * Возвращает элемент по ключу.
     *
     * @param key ключ
     * @return элемент коллекции
     */
    public LabWork get(Integer key) {
        return collection.get(key);
    }

    /**
     * Добавляет элемент в коллекцию.
     *
     * @param key ключ
     * @param labWork объект LabWork
     */
    public void insert(Integer key, LabWork labWork) {

        labWork.setId(IdGenerator.nextId());
        labWork.setCreationDate(new Date());

        collection.put(key, labWork);
    }

    /**
     * Обновляет элемент по ключу.
     *
     * @param key ключ
     * @param newLabWork новый объект LabWork
     * @return true, если элемент обновлен
     */
    public boolean update(Integer key, LabWork newLabWork) {

        LabWork oldLabWork = collection.get(key);

        if (oldLabWork == null) {
            return false;
        }

        newLabWork.setId(oldLabWork.getId());
        newLabWork.setCreationDate(
                oldLabWork.getCreationDate()
        );

        collection.put(key, newLabWork);

        return true;
    }

    /**
     * Удаляет элемент по ключу.
     *
     * @param key ключ
     * @return true, если элемент удален
     */
    public boolean remove(Integer key) {
        return collection.remove(key) != null;
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Возвращает элементы, отсортированные по имени.
     *
     * @return отсортированный список элементов
     */
    public List<LabWork> getSortedByName() {

        return collection.values()
                .stream()
                .sorted(Comparator.comparing(LabWork::getName))
                .toList();
    }

    /**
     * Удаляет элементы, превышающие заданный.
     *
     * @param labWork объект для сравнения
     */
    public void removeGreater(LabWork labWork) {

        collection.values()
                .removeIf(current -> current.compareTo(labWork) > 0);
    }

    /**
     * Удаляет элементы с ключом больше заданного.
     *
     * @param key ключ
     */
    public void removeGreaterKey(Integer key) {

        collection.keySet()
                .removeIf(currentKey -> currentKey > key);
    }

    /**
     * Заменяет элемент, если новый меньше старого.
     *
     * @param key ключ
     * @param newLabWork новый объект LabWork
     * @return true, если элемент заменен
     */
    public boolean replaceIfLower(
            Integer key,
            LabWork newLabWork
    ) {

        LabWork oldLabWork = collection.get(key);

        if (oldLabWork == null) {
            return false;
        }

        if (newLabWork.compareTo(oldLabWork) >= 0) {
            return false;
        }

        newLabWork.setId(oldLabWork.getId());
        newLabWork.setCreationDate(oldLabWork.getCreationDate());

        collection.put(
                key,
                newLabWork
        );

        return true;
    }

    /**
     * Подсчитывает элементы с заданным автором.
     *
     * @param authorName имя автора
     * @return количество элементов
     */
    public long countByAuthor(String authorName) {

        return collection.values()
                .stream()
                .filter(labWork -> labWork.getAuthor()
                        .getName()
                        .equals(authorName))
                .count();
    }

    /**
     * Фильтрует элементы по сложности.
     *
     * @param difficulty сложность
     * @return список подходящих элементов
     */
    public List<LabWork> filterByDifficulty(Difficulty difficulty) {

        return collection.values()
                .stream()
                .filter(labWork -> labWork.getDifficulty() == difficulty)
                .sorted(Comparator.comparing(LabWork::getName))
                .toList();
    }

    /**
     * Возвращает элементы, автор которых меньше заданного.
     *
     * @param authorName имя автора
     * @return список подходящих элементов
     */
    public List<LabWork> filterLessThanAuthor(
            String authorName
    ) {

        return collection.values()
                .stream()
                .filter(labWork -> labWork.getAuthor()
                        .getName()
                        .compareTo(authorName) < 0)
                .sorted(Comparator.comparing(LabWork::getName))
                .toList();
    }
}