package com.tsoyolv.gerritstat.controller;

import com.tsoyolv.gerritstat.controller.dto.ReportRequest;
import com.tsoyolv.gerritstat.service.ChangeService;
import com.tsoyolv.gerritstat.service.ReportService;
import com.tsoyolv.gerritstat.service.model.ChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("reportRequest")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ChangeService changeService;

    @ModelAttribute("reportRequest")
    public ReportRequest defaultReportRequest() {
        return new ReportRequest();
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new ReportRequest());
        return "index";
    }

    @PostMapping("/report")
    public String getReport(@ModelAttribute ReportRequest request, Model model) {
        List<String> userList = Arrays.asList(request.getUser().split("\\s*,\\s*"));
        var reports = reportService.generateReport(userList, request);
        model.addAttribute("reports", reports);
        model.addAttribute("request", request);
        return "report";
    }

    @GetMapping("/user/{userId}/changes")
    public String getUserChanges(@PathVariable String userId, @ModelAttribute("reportRequest") ReportRequest request, Model model) {
        List<ChangeSet> changes = changeService.getChangesForUser(userId, request.getCookie(), request.getFromDate(), request.getToDate());
        model.addAttribute("request", request);
        model.addAttribute("userId", userId);
        model.addAttribute("changes", changes);
        return "userChanges";
    }

}
