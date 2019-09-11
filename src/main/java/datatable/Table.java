package datatable;

import java.util.List;

public class Table {

    private String name;

    private List<Column> columns;

    private PrimaryKey primaryKey;

    private List<Index> indexes;

    public Table() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "Table [name=" + name + ", columns=" + columns + ", primaryKey=" + primaryKey
                + ", indexes=" + indexes + "]";
    }

}
