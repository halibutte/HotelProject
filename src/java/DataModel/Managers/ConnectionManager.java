/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 *
 * @author x3041557
 */
public class ConnectionManager {
    //members to deal with database 
    //holds the jdbc connection to postgre db
    Connection conn;
    /* LOCAL CONNECTION */
    
    final String CONN_STRING = "jdbc:postgresql://localhost:5432/studentdb";
    final String SCHEMA = "hotelbooking";
    final String USERNAME = "student";
    final String PW = "dbpassword"; 
    
    /* DEPLOYED CONNECTION */
    /*final String CONN_STRING = "jdbc:postgresql://cmpstudb-02.cmp.uea.ac.uk:5432/groupee";
    final String SCHEMA = "hotelbooking";
    final String USERNAME = "groupee";
    final String PW = "groupee";*/

    public ConnectionManager() throws SQLException {
        //attempt to create a connection, throw an SQLException if not poss
        conn = makeConnection();
    }
    
    private Connection makeConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        Connection connection = DriverManager.getConnection(
                CONN_STRING, 
                USERNAME, 
                PW
        );
        //attempt to set the schema
        Statement stmt = connection.createStatement();
        String schemaSQL = String.format("SET search_path TO '%s'", SCHEMA);
        stmt.execute(schemaSQL);
        return connection;
    }
    
    public Connection getConnection() throws SQLException {
        //get the conncection object if possible, throw SQLException if not
        if(Objects.isNull(conn) || conn.isClosed()) {
            conn = makeConnection();
        }
        return conn;
    }
    
    @Override
    public void finalize() {
        try {
            conn.close();
        } catch (SQLException e) {
            
        }
    }
    
    public static void main(String[] args) {
        ConnectionManager c = null;
        try {
            c = new ConnectionManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {        
            Statement s = c.getConnection().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM customer");
            while(rs.next()) {
                System.out.println(rs.getString(2));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}