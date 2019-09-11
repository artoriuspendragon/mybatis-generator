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

    public Table createTable(Connection connection, String tableName) throws SQLException {

        Table table = new Table();
        table.setName(tableName);

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        Map<String, Column> columnsMap = new HashMap<String, Column>();
        List<Column> columnsList = new ArrayList<Column>();

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            int dataType = columns.getInt("DATA_TYPE");
            String autoincrement = columns.getString("IS_AUTOINCREMENT");

            String comment = columns.getString("REMARKS");

            Column column = new Column();
            column.setName(columnName);
            column.setComment(comment);
            column.setType(SqlType.fromDataType(dataType));
            column.setAutoIncrement("YES".equalsIgnoreCase(autoincrement));

            columnsList.add(column);
            columnsMap.put(columnName, column);
        }

        table.setColumns(columnsList);

        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            String columnName = primaryKeys.getString("COLUMN_NAME");

            ArrayList<Column> arrayList = new ArrayList<Column>();

            Column column = columnsMap.get(columnName);

            arrayList.add(column);

            PrimaryKey key = new PrimaryKey(arrayList);
            table.setPrimaryKey(key);
        }

        ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, true, false);
        ArrayList<Index> indexesList = new ArrayList<Index>();

        Map<String, Index> indexMap = convertToMap(indexInfo,columnsMap);

        ResultSet indexInfo2 = metaData.getIndexInfo(null, null, tableName, false, false);

        Map<String,Index> indexMap2 = convertToMap(indexInfo2,columnsMap);
        indexMap.putAll(indexMap2);

        for (Map.Entry<String, Index> entry : indexMap.entrySet()) {
            indexesList.add(entry.getValue());
        }

        table.setIndexes(indexesList);
        return table;

    }


    private Map<String, Index> convertToMap(ResultSet indexInfo,Map<String,Column> columnsMap) throws SQLException {
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
            if(index==null){
                List<Column> members = new ArrayList();
                Index newIndex = new Index();
                members.add(indexColumn);
                newIndex.setName(indexName);
                newIndex.setUnique(unique);
                newIndex.setMembers(members);
                indexMap.put(indexName,newIndex);
            }else{
                index.getMembers().add(indexColumn);
            }
        }
        return indexMap;
    }

}