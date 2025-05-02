package com.anthony.transactions;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


public class PluginLogger {

    private final File file;

    public PluginLogger(JavaPlugin plugin, String filename){
        file = new File(plugin.getDataFolder(), filename);
        if(!file.exists()){
            try{
                if(!file.createNewFile()){
                    plugin.getLogger().severe("File creation failed.");
                }
            } catch (IOException e){
                plugin.getLogger().severe("Couldn't create log file.");
            }
        }
    }

    public void log(String message){
        try(FileWriter fw = new FileWriter(file, true)){
            fw.write("[" + LocalDateTime.now() + "] " + message + "\n");
        } catch (IOException e){
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }
}
