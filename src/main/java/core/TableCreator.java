package core;

import datatable.*;

import java.sql.*;
import java.util.*;

public class TableCreator {

    private static final TableCreator instance = new TableCreator();

    private TableCreator() {
    }

    public static TableCreator getInstance() {
        return instance;
    }

    public List<Table> createTable(Connection connection, String tableNamePattern, String schema) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(schema, schema, tableNamePattern, null);
        List<Table> tableList = new ArrayList<>();
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            tableList.add(createSingleTable(connection, tableName, schema));
        }
        return tableList;
    }

    public Table createSingleTable(Connection connection, String tableName, String schema) throws SQLException {

        Table table = new Table();
        table.setName(tableName);

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(schema, schema, tableName, null);

        Map<String, Column> columnsMap = new HashMap<String, Column>();
        List<Column> columnsList = new ArrayList<Column>();

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            int dataType = columns.getInt("DATA_TYPE");
            String autoincrement = columns.getString("IS_AUTOINCREMENT");
            int nullable = columns.getInt("NULLABLE");

            String comment = columns.getString("REMARKS");

            Column column = new Column();
            column.setName(columnName);
            column.setComment(comment);
            column.setType(SqlType.fromDataType(dataType));
            column.setAutoIncrement("YES".equalsIgnoreCase(autoincrement));
            column.setNullable(nullable != 0);

            columnsList.add(column);
            columnsMap.put(columnName, column);
        }

        table.setColumns(columnsList);

        ResultSet primaryKeys = metaData.getPrimaryKeys(schema, schema, tableName);
        while (primaryKeys.next()) {
            String columnName = primaryKeys.getString("COLUMN_NAME");

            ArrayList<Column> arrayList = new ArrayList<Column>();

            Column column = columnsMap.get(columnName);

            arrayList.add(column);

            PrimaryKey key = new PrimaryKey(arrayList);
            table.setPrimaryKey(key);
        }

        ResultSet indexInfo = metaData.getIndexInfo(schema, schema, tableName, true, false);
        ArrayList<Index> indexesList = new ArrayList<Index>();

        Map<String, Index> indexMap = convertToMap(indexInfo, columnsMap);

        ResultSet indexInfo2 = metaData.getIndexInfo(schema, schema, tableName, false, false);

        Map<String, Index> indexMap2 = convertToMap(indexInfo2, columnsMap);
        indexMap.putAll(indexMap2);

        for (Map.Entry<String, Index> entry : indexMap.entrySet()) {
            indexesList.add(entry.getValue());
        }

        table.setIndexes(indexesList);
        return table;

    }


    private Map<String, Index> convertToMap(ResultSet indexInfo, Map<String, Column> columnsMap) throws SQLException {
        Map<String, Index> indexMap = new LinkedHashMap<String, Index>();
        while (indexInfo.next()) {
            String indexName = indexInfo.getString("INDEX_NAME");

            if ("primary".equalsIgnoreCase(indexName) || indexName == null
                    || indexName.toLowerCase().contains("primary")) {
                continue;
            }

            String columnName = indexInfo.getString("COLUMN_NAME");
            boolean unique = !indexInfo.getBoolean("NON_UNIQUE");

            Column indexColumn = columnsMap.get(columnName);
            Index index = indexMap.get(indexName);
            if (index == null) {
                List<Column> members = new ArrayList();
                Index newIndex = new Index();
                members.add(indexColumn);
                newIndex.setName(indexName);
                newIndex.setUnique(unique);
                newIndex.setMembers(members);
                indexMap.put(indexName, newIndex);
            } else {
                index.getMembers().add(indexColumn);
            }
        }
        return indexMap;
    }

}
