package datatable;

import core.Utils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Types;

public enum SqlType {

    BIT(Types.BIT, JavaType.BOOLEAN),

    TINYINT(Types.TINYINT, JavaType.SHORT),

    SMALLINT(Types.SMALLINT, JavaType.SHORT),

    INTEGER(Types.INTEGER, JavaType.INT),

    BIGINT(Types.BIGINT, JavaType.LONG),

    FLOAT(Types.FLOAT, JavaType.FLOAT),

    REAL(Types.REAL, JavaType.DOUBLE),

    DOUBLE(Types.DOUBLE, JavaType.DOUBLE),

    NUMERIC(Types.NUMERIC, JavaType.DOUBLE),

    DECIMAL(Types.DECIMAL, JavaType.DOUBLE),

    CHAR(Types.CHAR, JavaType.STRING),

    VARCHAR(Types.VARCHAR, JavaType.STRING),

    LONGVARCHAR(Types.LONGVARCHAR, JavaType.STRING),

    DATE(Types.DATE, JavaType.DATE),

    TIME(Types.TIME, JavaType.DATE),

    TIMESTAMP(Types.TIMESTAMP, JavaType.DATE),

    BINARY(Types.BINARY, JavaType.BYTES),

    VARBINARY(Types.VARBINARY, JavaType.BYTES),

    LONGVARBINARY(Types.LONGVARBINARY, JavaType.BYTES),

    NULL(Types.NULL, JavaType.VOID),

    OTHER(Types.OTHER, JavaType.OBJECT),

    JAVA_OBJECT(Types.JAVA_OBJECT, JavaType.OBJECT),

    DISTINCT(Types.DISTINCT, JavaType.OBJECT),

    STRUCT(Types.STRUCT, JavaType.OBJECT),

    ARRAY(Types.ARRAY, JavaType.BYTES),

    BLOB(Types.BLOB, JavaType.OBJECT),

    CLOB(Types.CLOB, JavaType.OBJECT),

    REF(Types.REF, JavaType.OBJECT),

    DATALINK(Types.DATALINK, JavaType.OBJECT),

    BOOLEAN(Types.BOOLEAN, JavaType.BOOLEAN),

    NCHAR(Types.NCHAR, JavaType.STRING), ;

    private int code;

    private JavaType javaType;

    private SqlType(int code) {
        this(code, null);
    }

    private SqlType(int code, JavaType javaType) {
        this.code = code;
        this.javaType = javaType;
    }

    public int getCode() {
        return code;
    }

    public static SqlType fromDataType(int type) {
        for (SqlType sqlType : values()) {
            if (sqlType.getCode() == type) {
                return sqlType;
            }
        }
        return null;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
