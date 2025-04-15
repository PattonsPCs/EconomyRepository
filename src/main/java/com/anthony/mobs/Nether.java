package com.anthony.mobs;

public interface Nether {
    default void applyNetherBuffs(LeveledMob mob){
        mob.health *= 2;
        mob.strength *= 1.7;
        mob.speed *= 1.5;
    }

}
