package utility;

import models.*;
import validator.LabWorkValidator;

/**
 * Создает объект LabWork на основе введенных данных.
 */
public class LabWorkAsker {

    private final ReaderManager readerManager;
    private final Console console;
    private boolean interactiveMode = true;

    /**
     * Создает объект для ввода данных LabWork.
     *
     * @param readerManager менеджер чтения данных
     * @param console консоль
     */
    public LabWorkAsker(ReaderManager readerManager, Console console) {
        this.readerManager = readerManager;
        this.console = console;
    }

    /**
     * Устанавливает режим ввода данных.
     *
     * @param interactive true для интерактивного режима
     */

    public void setInteractiveMode(boolean interactive) {
        this.interactiveMode = interactive;
    }

    /**
     * Создаёт и заполняет объект LabWork.
     */
    public LabWork askLabWork() {
        try {
            LabWork lab = new LabWork();

            lab.setName(askName());
            lab.setCoordinates(askCoordinates());
            lab.setMinimalPoint(askMinimalPoint());
            lab.setPersonalQualitiesMinimum(askPersonalQualities());
            lab.setDifficulty(askDifficulty());
            lab.setAuthor(askPerson());

            return lab;

        } catch (RuntimeException e) {
            console.printError("Операция отменена: " + e.getMessage());
            return null;
        }
    }

    /**
     * Запрашивает имя LabWork.
     */
    private String askName() {

        if (interactiveMode) {

            while (true) {

                console.print("Введите имя LabWork: ");

                String value = readerManager.readLine();

                if (value == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                if (LabWorkValidator.validateName(value)) {
                    return value;
                }

                console.printError("Имя не может быть пустым.");
            }

        } else {
            String value = readerManager.readLine();

            if (value == null) {
                throw new RuntimeException("Ввод прерван");
            }

            if (!LabWorkValidator.validateName(value)) {
                throw new RuntimeException("Имя не может быть пустым");
            }

            return value;
        }
    }

    /**
     * Запрашивает координаты.
     */
    private Coordinates askCoordinates() {

        if (interactiveMode) {

            while (true) {

                try {

                    console.print("Введите X: ");

                    String xStr = readerManager.readLine();

                    if (xStr == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    long x = Long.parseLong(xStr);


                    console.print("Введите Y: ");

                    String yStr = readerManager.readLine();

                    if (yStr == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    long y = Long.parseLong(yStr);


                    Coordinates coordinates = new Coordinates();

                    coordinates.setX(x);
                    coordinates.setY(y);

                    return coordinates;

                } catch (NumberFormatException e) {

                    console.printError("Координаты должны быть числами.");
                }
            }

        } else {

            try {

                String xStr = readerManager.readLine();

                if (xStr == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                long x = Long.parseLong(xStr);


                String yStr = readerManager.readLine();

                if (yStr == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                long y = Long.parseLong(yStr);


                Coordinates coordinates = new Coordinates();

                coordinates.setX(x);
                coordinates.setY(y);

                return coordinates;

            } catch (NumberFormatException e) {

                throw new RuntimeException("Ошибка парсинга координат");
            }
        }
    }

    /**
     * Запрашивает minimalPoint.
     */
    private Integer askMinimalPoint() {

        if (interactiveMode) {

            while (true) {

                console.print("Введите minimalPoint (или оставьте пустым): ");

                String value = readerManager.readLine();

                if (value == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                if (value.isBlank()) {
                    return null;
                }

                try {

                    int result = Integer.parseInt(value);

                    if (LabWorkValidator.validateMinimalPoint(result)) {
                        return result;
                    }

                    console.printError("Значение должно быть > 0");

                } catch (NumberFormatException e) {

                    console.printError("Введите корректное число.");
                }
            }

        } else {

            String value = readerManager.readLine();

            if (value == null) {
                throw new RuntimeException("Ввод прерван");
            }

            if (value.isBlank()) {
                return null;
            }

            try {

                int result = Integer.parseInt(value);

                if (!LabWorkValidator.validateMinimalPoint(result)) {
                    throw new RuntimeException("Значение должно быть > 0");
                }

                return result;

            } catch (NumberFormatException e) {

                throw new RuntimeException("Введите корректное число");
            }
        }
    }

    /**
     * Запрашивает personalQualitiesMinimum.
     */
    private float askPersonalQualities() {

        if (interactiveMode) {

            while (true) {

                try {

                    console.print("Введите personalQualitiesMinimum: ");

                    String value = readerManager.readLine();

                    if (value == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    float result = Float.parseFloat(value);

                    if (LabWorkValidator.validatePersonalQualities(result)) {
                        return result;
                    }

                    console.printError("Значение должно быть > 0");

                } catch (NumberFormatException e) {

                    console.printError("Введите число.");
                }
            }

        } else {

            String value = readerManager.readLine();

            if (value == null) {
                throw new RuntimeException("Ввод прерван");
            }

            float result = Float.parseFloat(value);

            if (!LabWorkValidator.validatePersonalQualities(result)) {
                throw new RuntimeException("Значение должно быть > 0");
            }

            return result;
        }
    }

    /**
     * Запрашивает Difficulty.
     */
    private Difficulty askDifficulty() {

        if (interactiveMode) {

            while (true) {

                console.println("Доступные значения Difficulty: " + "VERY_EASY, EASY, HARD, VERY_HARD, IMPOSSIBLE");

                console.print("Введите difficulty: ");

                String value = readerManager.readLine();

                if (value == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                try {

                    return Difficulty.valueOf(
                            value.toUpperCase()
                    );

                } catch (IllegalArgumentException e) {

                    console.printError("Неверное значение difficulty.");
                }
            }

        } else {

            String value = readerManager.readLine();

            if (value == null) {
                throw new RuntimeException("Ввод прерван");
            }

            return Difficulty.valueOf(
                    value.toUpperCase()
            );
        }
    }

    /**
     * Запрашивает автора LabWork.
     */
    private Person askPerson() {

        Person person = new Person();

        if (interactiveMode) {

            while (true) {

                console.print("Введите имя автора: ");

                String name = readerManager.readLine();

                if (name == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                if (LabWorkValidator.validatePersonName(name)) {

                    person.setName(name);

                    break;
                }

                console.printError("Имя не может быть пустым.");
            }

        } else {

            String name = readerManager.readLine();

            if (name == null) {
                throw new RuntimeException("Ввод прерван");
            }

            if (!LabWorkValidator.validatePersonName(name)) {
                throw new RuntimeException("Имя не может быть пустым");
            }

            person.setName(name);
        }


        console.print("Введите passportID (можно оставить пустым): ");

        String passport = readerManager.readLine();

        if (passport == null) {
            throw new RuntimeException("Ввод прерван");
        }

        person.setPassportID(LabWorkValidator.validatePassportId(passport) && !passport.isBlank()
                        ? passport
                        : null
        );


        person.setEyeColor(askColor("eyeColor"));

        person.setHairColor(askColor("hairColor"));

        person.setLocation(askLocation());

        return person;
    }

    /**
     * Запрашивает Color.
     */
    private Color askColor(String fieldName) {

        if (interactiveMode) {

            while (true) {

                console.println("Доступные значения " + fieldName + ": GREEN, RED, ORANGE, WHITE, BLACK, YELLOW");

                console.print("Введите значение (или оставьте пустым): ");

                String value = readerManager.readLine();

                if (value == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                if (value.isBlank()) {
                    return null;
                }

                try {

                    return Color.valueOf(
                            value.toUpperCase()
                    );

                } catch (IllegalArgumentException e) {

                    console.printError("Неверное значение Color.");
                }
            }

        } else {

            String value = readerManager.readLine();

            if (value == null) {
                throw new RuntimeException("Ввод прерван");
            }

            if (value.isBlank()) {
                return null;
            }

            return Color.valueOf(
                    value.toUpperCase()
            );
        }
    }

    /**
     * Запрашивает Location.
     */
    private Location askLocation() {

        if (interactiveMode) {

            while (true) {

                try {

                    console.print("Введите location.x: ");

                    String xStr = readerManager.readLine();

                    if (xStr == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    int x = Integer.parseInt(xStr);


                    console.print("Введите location.y: ");

                    String yStr = readerManager.readLine();

                    if (yStr == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    float y = Float.parseFloat(yStr);


                    console.print("Введите location.z: ");

                    String zStr = readerManager.readLine();

                    if (zStr == null) {
                        throw new RuntimeException("Ввод прерван");
                    }

                    float z = Float.parseFloat(zStr);


                    Location location = new Location();

                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);

                    return location;

                } catch (NumberFormatException e) {

                    console.printError(
                            "Ошибка ввода location."
                    );
                }
            }

        } else {

            try {

                String xStr = readerManager.readLine();

                if (xStr == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                int x = Integer.parseInt(xStr);


                String yStr = readerManager.readLine();

                if (yStr == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                float y = Float.parseFloat(yStr);


                String zStr = readerManager.readLine();

                if (zStr == null) {
                    throw new RuntimeException("Ввод прерван");
                }

                float z = Float.parseFloat(zStr);


                Location location = new Location();

                location.setX(x);
                location.setY(y);
                location.setZ(z);

                return location;

            } catch (NumberFormatException e) {

                throw new RuntimeException(
                        "Ошибка парсинга location"
                );
            }
        }
    }
}