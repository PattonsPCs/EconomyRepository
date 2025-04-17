package com.anthony;


import org.bukkit.entity.Player;

import java.sql.*;

public class EconData{
    private Connection connection;
    private PlayerEcon playerEcon = new PlayerEcon(0);

    public void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            System.out.println("Failed to connect to SQLITE Database.");
            e.printStackTrace();
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS playerdata(uuid TEXT PRIMARY KEY, balance INT NOT NULL)";
        try(Statement stmt = connection.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Failed to create table.");
            e.printStackTrace();
        }
    }

    public void createAccount(){
        try(PreparedStatement pstmt = connection.prepareStatement("INSERT INTO playerdata(uuid, balance) VALUES(?,?)")){
            pstmt.setString(1, playerEcon.getId().toString());
            pstmt.setInt(2, playerEcon.getCurrencyAmount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating account.");
            e.printStackTrace();
        }
    }

    public void deposit(Player player, int amount){
        try(PreparedStatement pstmt = connection.prepareStatement("UPDATE playerdata SET balance = balance + ? WHERE uuid = ?")){
            playerEcon.depositPlayer(player, amount);
            pstmt.setInt(1, amount);
            pstmt.setString(2, player.getUniqueId().toString());
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error depositing money.");
            e.printStackTrace();
        }
    }

    public void withdraw(Player player, int amount){
        try(PreparedStatement pstmt = connection.prepareStatement("UPDATE playerdata SET balance = balance - ? WHERE uuid = ?")){
            playerEcon.withdrawPlayer(player, amount);
            pstmt.setInt(1, amount);
            pstmt.setString(2, player.getUniqueId().toString());
            pstmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error withdrawing money.");
        }
    }



    public void close(){
        try{
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection.");
            e.printStackTrace();
        }
    }
}
