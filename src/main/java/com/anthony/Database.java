package com.anthony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.UUID;


public class Database{
    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    public void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            logger.error("Failed to connect to SQLITE Database.", e);
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS playerdata(uuid TEXT PRIMARY KEY, balance INT NOT NULL)";
        try(Statement stmt = connection.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("Failed to create table.", e);
        }
    }

    public void saveAccount(UUID uuid, int balance){
        try(PreparedStatement pstmt = connection.prepareStatement(
                "INSERT OR REPLACE INTO playerdata(uuid, balance) VALUES(?, ?)")){
            pstmt.setString(1, uuid.toString());
            pstmt.setInt(2, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to save account.",  e);
        }
    }

    public int loadBalance(UUID uuid){
        try(PreparedStatement pstmt = connection.prepareStatement(
                "SELECT balance FROM playerdata WHERE uuid = ?")) {
            pstmt.setString(1, uuid.toString());
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("balance");
            }
        }catch (SQLException e){
            logger.error("Failed to load balance.", e);
        }
        return 0;
    }

    public void close(){
            try{
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close connection.");
            }
        }
    }