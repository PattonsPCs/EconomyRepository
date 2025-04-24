package com.anthony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;


public class Database{
    private Connection connection;
    private Account account;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    public void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            logger.error("Failed to connect to SQLITE Database.");
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS playerdata(uuid TEXT PRIMARY KEY, balance INT NOT NULL)";
        try(Statement stmt = connection.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("Failed to create table.");
        }
    }

    public void createAccount(){
        try(PreparedStatement pstmt = connection.prepareStatement("INSERT INTO playerdata(uuid, balance) VALUES(?,?)")){
            pstmt.setString(1, account.getPlayerID());
            pstmt.setInt(2, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error creating account.");
        }
    }

    public void close(){
        try{
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to close connection.");
        }
    }
}
