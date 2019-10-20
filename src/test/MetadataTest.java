import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MetadataTest {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        String port = "3306";
        String schema = "mytest";
        String user = "root";
        String password = "123456";
        String tableName = "test_table%";
        Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema + "?serverTimezone=UTC", user, password);
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet tableResult = metaData.getTables(schema,schema,tableName,null);
        while(tableResult.next()){
            System.out.println(tableResult.getString("TABLE_NAME"));
        }

        ResultSet resultSet = metaData.getColumns(schema, schema, tableName, "%");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("COLUMN_NAME"));
        }
        connection.close();
    }
}
