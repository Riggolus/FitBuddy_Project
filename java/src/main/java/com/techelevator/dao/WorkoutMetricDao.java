package com.techelevator.dao;

import com.techelevator.model.WorkoutMetrics;

import java.security.Principal;
import java.util.List;

public interface WorkoutMetricDao {

    List<WorkoutMetrics> myMetrics(Principal principal);

    List<WorkoutMetrics> memberMetrics(int userId);
}
