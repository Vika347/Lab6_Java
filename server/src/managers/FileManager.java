package managers;

import models.LabWork;
import utility.CsvParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * Управляет чтением и записью коллекции в файл.
 */
public class FileManager {

    private static final Logger logger =
            Logger.getLogger(FileManager.class.getName());

    private final String fileName;

    /**
     * Создает менеджер работы с файлом.
     *
     * @param fileName имя файла
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Читает коллекцию из файла.
     *
     * @return загруженная коллекция
     */
    public Hashtable<Integer, LabWork> readCollection() {

        Hashtable<Integer, LabWork> collection =
                new Hashtable<>();

        File file = new File(fileName);

        if (!file.exists()) {
            logger.warning("Файл не найден: "
                    + fileName
                    + ". Будет создан новый."
            );

            return collection;
        }

        try (
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);

                BufferedReader br = new BufferedReader(reader)) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                try {

                    Integer key = CsvParser.parseKey(line);

                    LabWork lab = CsvParser.parseLabWork(line);

                    collection.put(key, lab);

                } catch (Exception e) {

                    logger.warning("Ошибка парсинга строки: " + line);
                }
            }

            logger.info("Коллекция успешно загружена из файла.");

        } catch (IOException e) {

            logger.severe("Ошибка чтения файла: " + e.getMessage());
        }

        return collection;
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция LabWork
     */
    public void writeCollection(
            Hashtable<Integer, LabWork> collection
    ) {

        try (
                FileWriter writer =
                        new FileWriter(fileName)
        ) {

            for (var entry : collection.entrySet()) {

                writer.write(CsvParser.toCsv(entry.getKey(), entry.getValue()) + "\n");
            }

            logger.info("Коллекция успешно сохранена в файл.");

        } catch (IOException e) {

            logger.severe("Ошибка записи файла: " + e.getMessage()
            );
        }
    }
}

