import core.Generator;
import core.TableCreator;
import datatable.Table;

import java.sql.Connection;

public class TableCreateTest {

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        String host = "127.0.0.1";
        String port = "3306";
        String database = "mytest";
        String user = "root";
        String password = "123456";
        String targetPackage = "test.component";
        String sourceDirectory = "F:/projects/code-generator/mybatis-generator/src/main/java";
        String table = "test_table%";
        MojoRun mojoRun = new MojoRun();
        mojoRun.setDatabase(database);
        mojoRun.setHost(host);
        mojoRun.setOverwrite(true);
        mojoRun.setPassword(password);
        mojoRun.setPort(port);
        mojoRun.setSourceDirectory(sourceDirectory);
        mojoRun.setTable(table);
        mojoRun.setTargetPackage(targetPackage);
        mojoRun.setUser(user);
        mojoRun.execute();
    }
}
