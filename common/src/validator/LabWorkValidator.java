package validator;

import models.LabWork;

/**
 * Проверяет корректность данных объекта LabWork.
 */
public final class LabWorkValidator {

    /**
     * Запрещает создание экземпляров класса.
     */
    private LabWorkValidator() {
    }

    /**
     * Проверяет имя LabWork.
     *
     * @param name имя LabWork
     * @return true, если имя корректно
     */
    public static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Проверяет минимальный балл.
     *
     * @param value минимальный балл
     * @return true, если значение корректно
     */
    public static boolean validateMinimalPoint(Integer value) {
        return value == null || value > 0;
    }

    /**
     * Проверяет значение личных качеств.
     *
     * @param value значение личных качеств
     * @return true, если значение корректно
     */
    public static boolean validatePersonalQualities(float value) {
        return value > 0;
    }

    /**
     * Проверяет имя автора.
     *
     * @param name имя автора
     * @return true, если имя корректно
     */
    public static boolean validatePersonName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Проверяет passportID автора.
     *
     * @param passportId passportID автора
     * @return true, если значение корректно
     */
    public static boolean validatePassportId(String passportId) {
        return passportId == null || !passportId.trim().isEmpty();
    }

    /**
     * Проверяет корректность объекта LabWork.
     *
     * @param labWork объект LabWork
     * @return true, если объект корректен
     */
    public static boolean isValid(LabWork labWork) {

        if (labWork == null) {
            return false;
        }

        if (!validateName(labWork.getName())) {
            return false;
        }

        if (labWork.getCoordinates() == null) {
            return false;
        }

        if (!validateMinimalPoint(labWork.getMinimalPoint())) {
            return false;
        }

        if (!validatePersonalQualities(
                labWork.getPersonalQualitiesMinimum())) {
            return false;
        }

        if (labWork.getDifficulty() == null) {
            return false;
        }

        if (labWork.getAuthor() == null) {
            return false;
        }

        if (!validatePersonName(
                labWork.getAuthor().getName())) {
            return false;
        }

        if (!validatePassportId(
                labWork.getAuthor().getPassportID())) {
            return false;
        }

        if (labWork.getAuthor().getLocation() == null) {
            return false;
        }

        if (labWork.getAuthor().getLocation().getZ() == null) {
            return false;
        }

        return true;
    }
}
