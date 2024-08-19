package com.tsoyolv.gerritstat.controller;

import com.tsoyolv.gerritstat.controller.dto.ReportRequest;
import com.tsoyolv.gerritstat.service.ReportService;
import com.tsoyolv.gerritstat.service.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new ReportRequest());
        return "index";
    }

    @PostMapping("/report")
    public String getReport(@ModelAttribute ReportRequest request, Model model) {
        Report report = reportService.generateReport(request);
        model.addAttribute("report", report);
        model.addAttribute("request", request);
        return "report";
    }
}
