package core;

import datatable.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String uncapitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    public static String columnNameToFieldName(String name) {
        int l = name.length();
        StringBuilder stringBuilder = new StringBuilder(l);
        boolean flag = false;
        for (int i = 0; i < l; i++) {
            char ch = name.charAt(i);

            if (ch == '_') {
                flag = true;
                continue;
            }

            if (flag) {
                stringBuilder.append(Character.toUpperCase(ch));
                flag = false;
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }


    public static String tableNameToClassName(String name) {
        return capitalize(columnNameToFieldName(name));
    }

    public static String getterName(String name, JavaType javaType) {
        if (javaType == JavaType.BOOLEAN) {
            return "is" + capitalize(name);
        } else {
            return "get" + capitalize(name);
        }
    }

    public static String getLiteFieldsString(Table table) {
        List<Column> columns = table.getColumns();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (column.isAutoIncrement()) {
                continue;
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("`").append(column.getName()).append("`");
        }
        return stringBuilder.toString();
    }

    public static String getLiteParamsString(Table table) {
        List<Column> columns = table.getColumns();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (column.isAutoIncrement()) {
                continue;
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(":").append(column.getName());
        }
        return stringBuilder.toString();
    }

    public static String getAutoIncrementFieldsString(Table table) {
        List<Column> columns = table.getColumns();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (!column.isAutoIncrement()) {
                continue;
            }

            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("`").append(column.getName()).append("`");
        }
        return stringBuilder.toString();
    }

    public static String getMethodName(PrimaryKey primaryKey) {
        List<Column> columns = primaryKey.getMembers();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (stringBuilder.length() > 0) {
                stringBuilder.append("And");
            }
            stringBuilder.append(tableNameToClassName(column.getName()));
        }
        return stringBuilder.toString();
    }

    public static String getMethodName(Index index) {
        List<Column> columns = index.getMembers();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (stringBuilder.length() > 0) {
                stringBuilder.append("And");
            }
            stringBuilder.append(tableNameToClassName(column.getName()));
        }
        return stringBuilder.toString();
    }

    public static JavaType columnTypeToJavaType(SqlType type) {
        return JavaType.fromSqlType(type);
    }

    public static String columnToJavaTypeString(Column column) {
        if (column.getType().getJavaType().equals(JavaType.DATE)) {
            return "java.util.Date";
        }
        if (column.isNullable()) {
            return column.getType().getJavaType().getWrapperTypeName().getSimpleName();
        } else {
            return column.getType().getJavaType().getPrimitiveTypeName().getSimpleName();
        }
    }

    public static String columnToWrapperJavaTypeString(Column column) {
        if (column.getType().getJavaType().equals(JavaType.DATE)) {
            return "java.util.Date";
        }
        return column.getType().getJavaType().getWrapperTypeName().getSimpleName();
    }
}
