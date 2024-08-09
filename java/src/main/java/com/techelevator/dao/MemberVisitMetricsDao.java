package com.techelevator.dao;

import com.techelevator.model.MemberVisitMetrics;

import java.security.Principal;
import java.util.List;

public interface MemberVisitMetricsDao {

    List<MemberVisitMetrics> memberVisitMetrics(Principal principal);

    List<MemberVisitMetrics> employeeViewMemberVisitMetrics(int userId);


}
