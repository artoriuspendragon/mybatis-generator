package datatable;

public class Column {

    private String name;

    private SqlType type;

    private String comment;

    private boolean autoIncrement;

    public Column() {
        super();
    }

    public Column(String name, SqlType type, String comment, boolean autoIncrement) {
        this.name = name;
        this.type = type;
        this.comment = comment;
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SqlType getType() {
        return type;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", type=" + type + ", comment=" + comment + "]";
    }

}
