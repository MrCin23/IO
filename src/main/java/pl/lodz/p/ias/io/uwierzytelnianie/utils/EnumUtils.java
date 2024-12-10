package pl.lodz.p.ias.io.uwierzytelnianie.utils;

import java.util.Arrays;

public class EnumUtils {

    /**
     * Checks if a value is a valid enum constant.
     *
     * @param enumClass the enum class to check
     * @param value the value to validate
     * @return true if the value is valid; false otherwise
     */
    public static boolean isValidEnum(Class<? extends Enum<?>> enumClass, String value) {
        if (value == null || enumClass == null) {
            return false;
        }
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}