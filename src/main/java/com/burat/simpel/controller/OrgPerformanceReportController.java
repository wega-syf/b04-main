package com.burat.simpel.controller;

import com.burat.simpel.dto.AllReportDTO;
import com.burat.simpel.service.OrgPerformanceReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrgPerformanceReportController {
    @Autowired
    OrgPerformanceReportService orgPerformanceReportService;

    @GetMapping("/report")
    private String selectReportModesPage() {
        return "report-modes";
    }

    @GetMapping("/report/all")
    private String allEmployeeReportPage(Model model) {
        String jsonString = orgPerformanceReportService.generateAllReportJSON();
        List<AllReportDTO> listReport = orgPerformanceReportService.getAllAllReportDTO();

        model.addAttribute("listReport", listReport);
        model.addAttribute("chartData", jsonString);
        return "report-all";
    }

    @GetMapping("/report/divisional")
    private String divisionalReportPage(Model model) {
        String jsonString = orgPerformanceReportService.generateDivisionalReportJSON();
        Map<Object, List<AllReportDTO>> mapReport = orgPerformanceReportService.generateTableDivisionalReport();

        model.addAttribute("mapReport", mapReport);
        model.addAttribute("chartData", jsonString);
        return "report-divisional";
    }

    @GetMapping("/report/competency")
    private String competencyReportPage(Model model) {
        String jsonString = orgPerformanceReportService.generateCompetencyReportJSON();
        Map<Object, List<AllReportDTO>> mapReport = orgPerformanceReportService.generateTableCompetencyReport();

        model.addAttribute("mapReport", mapReport);
        model.addAttribute("chartData", jsonString);
        return "report-competency";
    }

    @GetMapping("/report/download-excel")
    public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam(value = "mode") Long mode) throws IOException {
        Workbook workbook = null;
        if (mode == 1L) {
            workbook = orgPerformanceReportService.createExcelWorkbookAll();
        } else if (mode == 2L) {
            workbook = orgPerformanceReportService.createExcelWorkbookDivisional();
        } else {
            workbook = orgPerformanceReportService.createExcelWorkbookCompetency();
        }

        // Convert workbook to bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] excelBytes = outputStream.toByteArray();

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        if (mode == 1L) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=All_OrganizationPerformanceReport.xlsx");
        } else if (mode == 2L) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Divisional_OrganizationPerformanceReport.xlsx");
        } else {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Competency_OrganizationPerformanceReport.xlsx");
        }

        // Return the Excel file as a ResponseEntity
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(new ByteArrayInputStream(excelBytes)));
    }

}
