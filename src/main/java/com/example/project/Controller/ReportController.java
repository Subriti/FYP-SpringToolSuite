package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Post;
import com.example.project.Model.Report;
import com.example.project.Service.ReportService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/showReport")
    public List<Report> getReport() {
        return reportService.getReports();
    }

    @PostMapping("/addReport")
    public JSONObject addNewReport(@RequestBody Report report) {
        return reportService.addReport(report);
    }

    @DeleteMapping(path = "/deleteReport/{report_id}")
    public void deleteReport(@PathVariable("report_id") int reportId) {
        reportService.deleteReport(reportId);
    }

    /*
     * @PutMapping(path = "/updateReport/{postId}")
     * public JSONObject updateReport(@PathVariable("postId") Post postId) {
     * return reportService.updateReportStatus(postId);
     * }
     */
}
