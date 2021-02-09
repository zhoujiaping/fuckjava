package cn.sirenia.text;

public class NameStyle {
    /**
     * 驼峰命名转下划线命名
     */
    public static String toUnderline(String camelName) {
        return camelName.replaceAll("(?<Uper>[A-Z])", "_${Uper}").toLowerCase();
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toCamel(String underlineName) {
        if (underlineName == null) {
            return null;
        }
        underlineName = underlineName.toLowerCase();
        String[] array = underlineName.split("_(?=[a-z])");
        if (array.length == 1) {
            return underlineName;
        }
        // System.out.println(String.join(",", array));
        for (int i = 1; i < array.length; i++) {
            array[i] = array[i].substring(0, 1).toUpperCase() + array[i].substring(1);
        }
        return String.join("", array);
    }
}