package aws.practice.auroraPostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AuroraPostgres {
    // database info 
    private static final String url = CredentioalsInfo.url;
    private static final String user = CredentioalsInfo.user;
    private static final String password = CredentioalsInfo.password;

    private AuroraPostgres(){
    }

    // update messages table
    public static void updateMessage(String targetColumnName, String message, String targetPrimaryKey){
        String sql = String.format("update messages set %s=? where queue_name=?;", targetColumnName);
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);){
                statement.setString(1, message);
                statement.setString(2, targetPrimaryKey);
            statement.executeUpdate();
            System.out.println("complete update!");
        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // insert into messages table (init)
    public static void initInsert(String primaryKeyValue){
        String sql = String.format("insert into messages (queue_name) values (?);");
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);){
                statement.setString(1, primaryKeyValue);
            statement.executeUpdate();
            System.out.println("complete insert!");
        }catch (SQLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // confirm connection
    private static void confirmConnction(){
        String sql = "select 1;";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);){
                statement.executeQuery();
                System.out.println("successfully connected!");
        }catch(SQLException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        confirmConnction();
    }
}
