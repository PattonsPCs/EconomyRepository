package com.anthony.mobs;

public interface Aggressive {
    default void boostAggressionStats(LeveledMob mob){
        mob.strength *= 1.25;
        mob.health *= 1.15;
        mob.speed *= 1.1;
    }
}
