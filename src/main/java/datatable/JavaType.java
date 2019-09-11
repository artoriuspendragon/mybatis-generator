package datatable;

import java.util.Date;

public enum JavaType {
    BYTE(byte.class, Byte.class), CHAR(char.class, Character.class), SHORT(short.class, Short.class), INT(
            int.class, Integer.class), LONG(long.class, Long.class), FLOAT(float.class, Float.class), DOUBLE(
            double.class, Double.class), BOOLEAN(boolean.class, Boolean.class), STRING(
            String.class, String.class), BYTES(byte[].class, Byte[].class), ENUM(null, null), DATE(
            Date.class, Date.class), VOID(Void.class, Void.class), OBJECT(Object.class,
            Object.class);

    /**
     * @param primitiveTypeName
     * @param wrapperTypeName
     */
    private JavaType(Class<?> primitiveTypeName, Class<?> wrapperTypeName) {
        this.primitiveTypeName = primitiveTypeName;
        this.wrapperTypeName = wrapperTypeName;
    }

    private final Class<?> primitiveTypeName;

    private final Class<?> wrapperTypeName;

    public Class<?> getPrimitiveTypeName() {
        return primitiveTypeName;
    }

    public Class<?> getWrapperTypeName() {
        return wrapperTypeName;
    }

    public static JavaType fromSqlType(SqlType type) {
        return type.getJavaType();
    }

    public String getName() {
        if (primitiveTypeName == Date.class) {
            return primitiveTypeName.getName();
        }
        return primitiveTypeName.getSimpleName();
    }

}
