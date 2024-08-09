package com.techelevator.controller;

import com.techelevator.dao.MemberVisitMetricsDao;
import com.techelevator.model.MemberVisitMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/visit-metrics")
@PreAuthorize("isAuthenticated()")
public class MemberVisitMetricsController {

    private final MemberVisitMetricsDao memberVisitMetricsDao;

    @Autowired
    public MemberVisitMetricsController(MemberVisitMetricsDao memberVisitMetricsDao) {
        this.memberVisitMetricsDao = memberVisitMetricsDao;
    }

    @GetMapping
    public List<MemberVisitMetrics> memberVisitMetrics(Principal principal) {
        return memberVisitMetricsDao.memberVisitMetrics(principal);
    }

    @GetMapping("/{userId}")
    public List<MemberVisitMetrics> employeeViewMemberVisitMetrics(@PathVariable int userId) {
        return memberVisitMetricsDao.employeeViewMemberVisitMetrics(userId);
    }

}
