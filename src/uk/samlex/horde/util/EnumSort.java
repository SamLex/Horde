package uk.samlex.horde.util;

import java.util.Arrays;

public class EnumSort {

    public static <T extends Enum<T>> T[] sortEnumArrayAlphabetically(T[] e, Class<T> enumClass) {
        String[] names = new String[e.length];

        for (int i = 0; i < names.length; i++) {
            names[i] = e[i].toString();
        }

        Arrays.sort(names);

        for (int i = 0; i < e.length; i++) {
            e[i] = Enum.valueOf(enumClass, names[i]);
        }

        return e;
    }
}
