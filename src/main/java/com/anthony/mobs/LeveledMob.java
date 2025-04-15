package com.anthony.mobs;


public abstract class LeveledMob {

    // This class is to set levels and scale stats to mobs and scale rewards for defeating those mobs
    protected int level;
    protected double strength;
    protected double speed;
    protected double health;


    public abstract void scaleStats();

    public String getDisplayName(){
        return "Lv. "  + level + " Mob.";
    }
}

