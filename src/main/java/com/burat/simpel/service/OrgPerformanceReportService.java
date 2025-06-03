package com.burat.simpel.service;

import com.burat.simpel.dto.AllReportDTO;
import com.burat.simpel.model.Competency;
import com.burat.simpel.model.DivisiModel;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface OrgPerformanceReportService {
    List<AllReportDTO> getAllAllReportDTO();
    List<AllReportDTO> getDivisionalReportDTO(DivisiModel divisi);
    List<AllReportDTO> getCompetencyReportDTO(Competency competency);
    String generateAllReportJSON();
    String generateDivisionalReportJSON();
    String generateCompetencyReportJSON();
    Map<Object, List<AllReportDTO>> generateTableDivisionalReport();
    Map<Object, List<AllReportDTO>> generateTableCompetencyReport();
    Workbook createExcelWorkbookAll();
    Workbook createExcelWorkbookDivisional();
    Workbook createExcelWorkbookCompetency();
}
