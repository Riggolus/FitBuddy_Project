package com.techelevator.controller;

import com.techelevator.dao.AchievementDao;
import com.techelevator.model.Achievements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/achievements")
@PreAuthorize("isAuthenticated()")
public class AchievementsController {

    private final AchievementDao achievementDao;

    @Autowired
    public AchievementsController (AchievementDao achievementDao){
        this.achievementDao = achievementDao;
    }

    @GetMapping("/all")
    public List<Achievements> getAchievements(Principal principal){
        return achievementDao.getAchievements(principal);
    }

    @GetMapping("/{goalId}")
    public Achievements getAchievementsById(@PathVariable("goalId") int goalId, Principal principal){
        return achievementDao.getAchievementById(goalId, principal);
    }

    @PostMapping("/create")
    public void createAchievement(@RequestBody Achievements achievements, Principal principal) {
        achievementDao.createAchievement(achievements, principal);
    }

    @PutMapping("/update")
    public boolean updateAchievementById(@RequestBody Achievements achievements, Principal principal) {
        return achievementDao.updateAchievementById(achievements, principal);
    }

    @DeleteMapping("/delete/{goalId}")
    public boolean deleteAchievementByGoalId(@PathVariable("goalId") int goalId) {
        return achievementDao.deleteAchievementById(goalId);
    }



}
