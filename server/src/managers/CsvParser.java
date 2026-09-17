package utility;

import models.*;

import java.util.Date;

/**
 * Преобразует объекты LabWork в CSV и обратно.
 */
public class CsvParser {

    /**
     * Создает объект LabWork из CSV-строки.
     *
     * @param line CSV-строка
     * @return объект LabWork
     */
    public static LabWork parseLabWork(String line) {

        String[] data = line.split(";", -1);

        if (data.length < 16) {
            throw new IllegalArgumentException("Некорректная CSV строка");
        }

        LabWork lab = new LabWork();



        lab.setId(Integer.parseInt(data[1].trim()));

        lab.setName(data[2].trim());

        Coordinates coordinates = new Coordinates();

        coordinates.setX(Long.parseLong(data[3].trim()));

        coordinates.setY(Long.parseLong(data[4].trim()));

        lab.setCoordinates(coordinates);

        lab.setCreationDate(new Date(Long.parseLong(data[5].trim())));

        String minPoint = data[6].trim();

        lab.setMinimalPoint(minPoint.isEmpty()
                ? null
                : Integer.parseInt(minPoint)
        );

        lab.setPersonalQualitiesMinimum(Float.parseFloat(data[7].trim()));

        lab.setDifficulty(Difficulty.valueOf(data[8].trim().toUpperCase()));

        Person author = new Person();

        author.setName(data[9].trim());

        author.setPassportID(data[10].trim().isEmpty()
                ? null
                : data[10].trim()
        );

        author.setEyeColor(parseColor(data[11].trim()));

        author.setHairColor(parseColor(data[12].trim()));

        Location location = new Location();

        location.setX(Integer.parseInt(data[13].trim()));

        location.setY(Float.parseFloat(data[14].trim()));

        location.setZ(Float.parseFloat(data[15].trim()));

        author.setLocation(location);

        lab.setAuthor(author);

        return lab;
    }

    /**
     * Получает ключ из CSV-строки.
     *
     * @param line CSV-строка
     * @return ключ элемента
     */
    public static int parseKey(String line) {

        String[] data = line.split(";", -1);

        if (data.length < 16) {
            throw new IllegalArgumentException("Некорректная CSV строка");
        }

        return Integer.parseInt(
                data[0].trim()
        );
    }

    /**
     * Преобразует строковое значение в Color.
     *
     * @param value строковое значение цвета
     * @return цвет или null
     */
    private static Color parseColor(String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        return Color.valueOf(
                value.toUpperCase()
        );
    }

    /**
     * Преобразует объект LabWork в CSV-строку.
     *
     * @param key ключ элемента
     * @param lab объект LabWork
     * @return CSV-строка
     */
    public static String toCsv(
            Integer key,
            LabWork lab
    ) {

        return key + ";" +

                lab.getId() + ";" +

                lab.getName() + ";" +

                lab.getCoordinates().getX() + ";" +

                lab.getCoordinates().getY() + ";" +

                lab.getCreationDate().getTime() + ";" +

                (lab.getMinimalPoint() == null
                        ? ""
                        : lab.getMinimalPoint()) + ";" +

                lab.getPersonalQualitiesMinimum() + ";" +

                lab.getDifficulty() + ";" +

                lab.getAuthor().getName() + ";" +

                (lab.getAuthor().getPassportID() == null
                        ? ""
                        : lab.getAuthor().getPassportID()) + ";" +

                (lab.getAuthor().getEyeColor() == null
                        ? ""
                        : lab.getAuthor().getEyeColor()) + ";" +

                (lab.getAuthor().getHairColor() == null
                        ? ""
                        : lab.getAuthor().getHairColor()) + ";" +

                lab.getAuthor().getLocation().getX() + ";" +

                lab.getAuthor().getLocation().getY() + ";" +

                lab.getAuthor().getLocation().getZ();
    }
}