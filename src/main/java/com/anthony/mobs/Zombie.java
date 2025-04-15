package com.anthony.mobs;


import java.util.Random;

public class Zombie extends LeveledMob{
    @Override
    public void scaleStats() {
        Random randLevel = new Random();
        level  = randLevel.nextInt(5);
        health += health +(level * .75);
        strength += strength + (level * .8);
        speed += speed + (level * .5);
    }
    @Override
    public String getDisplayName(){
        return "Lv. " + level + " Zombie";}

}
