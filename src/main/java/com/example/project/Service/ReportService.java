package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Report;
import com.example.project.Repository.ReportRepository;

import net.minidev.json.JSONObject;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getReports() {
        return reportRepository.getAll();
    }

    public JSONObject addReport(Report report) {
        reportRepository.save(report);
        
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("Success message", "Report Successfully Created !!");
        return jsonObject;
    }

    public void deleteReport(int reportId) {
        boolean exists = reportRepository.existsById(reportId);
        if (!exists) {
            throw new IllegalStateException("report with id " + reportId + " does not exist");
        }
        reportRepository.deleteById(reportId);
    }
    
    @Transactional
    public Report reviewReport(int reportId, Boolean isReviewed) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalStateException("Report with ID " + reportId + " does not exist"));
        report.setIsReviewed(isReviewed);
        return report;
    }
}