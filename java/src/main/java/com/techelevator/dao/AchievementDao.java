package com.techelevator.dao;

import com.techelevator.model.Achievements;

import java.security.Principal;
import java.util.List;

public interface AchievementDao {
    List<Achievements> getAchievements(Principal principal);

    Achievements getAchievementById(int goalId, Principal principal);

    void createAchievement(Achievements achievements, Principal principal);
    boolean updateAchievementById(Achievements achievements, Principal principal);

    boolean deleteAchievementById(int goalId);
}
