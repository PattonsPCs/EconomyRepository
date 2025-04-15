package com.anthony.mobs;

public interface End {
    default void applyEndBuffs(LeveledMob mob){
        mob.health *= 1.9;
        mob.strength *= 2;
        mob.speed *= 1.75;
    }
}
