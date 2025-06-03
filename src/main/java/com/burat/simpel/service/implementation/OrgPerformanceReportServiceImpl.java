package com.burat.simpel.service.implementation;

import com.burat.simpel.dto.AllReportDTO;
import com.burat.simpel.model.Competency;
import com.burat.simpel.model.DivisiModel;
import com.burat.simpel.repository.CompetencyDb;
import com.burat.simpel.repository.DivisiDb;
import com.burat.simpel.repository.ReviewAssessmentDb;
import com.burat.simpel.service.OrgPerformanceReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrgPerformanceReportServiceImpl implements OrgPerformanceReportService {

    @Autowired
    ReviewAssessmentDb reviewAssessmentDb;

    @Autowired
    DivisiDb divisiDb;

    @Autowired
    private CompetencyDb competencyDb;

    @Override
    public List<AllReportDTO> getAllAllReportDTO() {
        return reviewAssessmentDb.getAllReports();
    }

    @Override
    public List<AllReportDTO> getDivisionalReportDTO(DivisiModel divisi) {
        return reviewAssessmentDb.getDivisionalReports(divisi);
    }

    @Override
    public List<AllReportDTO> getCompetencyReportDTO(Competency competency) {
        return reviewAssessmentDb.getCompetencyReports(competency);
    }

    @Override
    public String generateAllReportJSON() {
        List<AllReportDTO> allReportDTOS = getAllAllReportDTO();
        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for (AllReportDTO r : allReportDTOS) {
            labels.add(r.getEventPeriod().getPeriodName());
            values.add(r.getRerataGap());
        }

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("values", values);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    public String generateDivisionalReportJSON() {
        List<DivisiModel> listDivision = divisiDb.findAll();
        Map<String, Object> chartData = new HashMap<>();

        for (DivisiModel d : listDivision) {

            // Remove this filter when not using dummy data anymore
            if (d.getIdDivisi() == 1)
                continue;
            else if (d.getIdDivisi() == 6)
                continue;

            List<AllReportDTO> allDivisionalReportDTO = getDivisionalReportDTO(d);
            List<String> labels = new ArrayList<>();
            List<Double> values = new ArrayList<>();

            for (AllReportDTO r : allDivisionalReportDTO) {
                labels.add(r.getEventPeriod().getPeriodName());
                values.add(r.getRerataGap());
            }

            Map<String, Object> divisionalData = new HashMap<>();
            divisionalData.put("labels", labels);
            divisionalData.put("values", values);

            chartData.put(d.getNama(), divisionalData);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    public String generateCompetencyReportJSON() {
        List<Competency> listCompetency = competencyDb.findAll();
        Map<String, Object> chartData = new HashMap<>();

        for (Competency c : listCompetency) {
            List<AllReportDTO> allCompetencyReportDTO = getCompetencyReportDTO(c);
            if (allCompetencyReportDTO == null || allCompetencyReportDTO.size() == 0)
                continue;

            List<String> labels = new ArrayList<>();
            List<Double> values = new ArrayList<>();

            for (AllReportDTO r : allCompetencyReportDTO) {
                labels.add(r.getEventPeriod().getPeriodName());
                values.add(r.getRerataGap());
            }

            Map<String, Object> competencyData = new HashMap<>();
            competencyData.put("labels", labels);
            competencyData.put("values", values);

            chartData.put(c.getNama(), competencyData);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(chartData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    public Map<Object, List<AllReportDTO>> generateTableDivisionalReport() {
        List<DivisiModel> listDivision = divisiDb.findAll();
        Map<Object, List<AllReportDTO>> mapReport = new HashMap<>();

        for (DivisiModel d : listDivision) {
            // Remove this filter when not using dummy data anymore
            if (d.getIdDivisi() == 1)
                continue;
            else if (d.getIdDivisi() == 6)
                continue;

            List<AllReportDTO> allDivisionalReportDTO = getDivisionalReportDTO(d);
            mapReport.put(d, allDivisionalReportDTO);
        }

        return mapReport;
    }

    @Override
    public Map<Object, List<AllReportDTO>> generateTableCompetencyReport() {
        List<Competency> listCompetency = competencyDb.findAll();
        Map<Object, List<AllReportDTO>> mapReport = new HashMap<>();

        for (Competency c : listCompetency) {
            List<AllReportDTO> allCompetencyReportDTO = getCompetencyReportDTO(c);
            if (allCompetencyReportDTO == null || allCompetencyReportDTO.size() == 0)
                continue;

            mapReport.put(c, allCompetencyReportDTO);
        }

        return mapReport;
    }

    @Override
    public Workbook createExcelWorkbookAll() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        Row headerRow = sheet.createRow(0);
        Cell assPerCell = headerRow.createCell(0);
        assPerCell.setCellValue("Assessment Period");
        Cell dateCell = headerRow.createCell(1);
        dateCell.setCellValue("Date");
        Cell participantsCell = headerRow.createCell(2);
        participantsCell.setCellValue("Participants");
        Cell gapCell = headerRow.createCell(3);
        gapCell.setCellValue("Average Gap");

        Integer rowCount = 1;
        List<AllReportDTO> allReportDTOS = getAllAllReportDTO();
        for (AllReportDTO r : allReportDTOS) {
            Row row = sheet.createRow(rowCount);
            List<String> data = new ArrayList<>();
            data.add(r.getEventPeriod().getPeriodName());
            data.add(r.getEventPeriod().getDateStart().toString());
            data.add(r.getParticipants().toString());
            data.add(r.getRerataGap().toString());

            Integer cellCounter = 0;
            for (String d : data) {
                Cell cell = row.createCell(cellCounter);
                cell.setCellValue(d);
                cellCounter++;
            }
            rowCount++;
        }

        return workbook;
    }

    @Override
    public Workbook createExcelWorkbookDivisional() {
        Workbook workbook = new XSSFWorkbook();

        Map<Object, List<AllReportDTO>> mapReport = generateTableDivisionalReport();
        for (Object key : mapReport.keySet()) {
            DivisiModel division = (DivisiModel) key;
            List<AllReportDTO> allReportDTOS = mapReport.get(key);

            Sheet sheet = workbook.createSheet(division.getNama());

            Row headerRow = sheet.createRow(0);

            Cell assPerCell = headerRow.createCell(0);
            assPerCell.setCellValue("Assessment Period");
            Cell dateCell = headerRow.createCell(1);
            dateCell.setCellValue("Date");
            Cell participantsCell = headerRow.createCell(2);
            participantsCell.setCellValue("Participants");
            Cell gapCell = headerRow.createCell(3);
            gapCell.setCellValue("Average Gap");

            Integer rowCount = 1;
            for (AllReportDTO r : allReportDTOS) {
                Row row = sheet.createRow(rowCount);
                List<String> data = new ArrayList<>();
                data.add(r.getEventPeriod().getPeriodName());
                data.add(r.getEventPeriod().getDateStart().toString());
                data.add(r.getParticipants().toString());
                data.add(r.getRerataGap().toString());

                Integer cellCounter = 0;
                for (String d : data) {
                    Cell cell = row.createCell(cellCounter);
                    cell.setCellValue(d);
                    cellCounter++;
                }
                rowCount++;
            }

        }

        return workbook;
    }

    @Override
    public Workbook createExcelWorkbookCompetency() {
        Workbook workbook = new XSSFWorkbook();

        Map<Object, List<AllReportDTO>> mapReport = generateTableCompetencyReport();
        for (Object key : mapReport.keySet()) {
            Competency competency = (Competency) key;
            List<AllReportDTO> allReportDTOS = mapReport.get(key);

            Sheet sheet = workbook.createSheet(competency.getNama());

            Row headerRow = sheet.createRow(0);

            Cell assPerCell = headerRow.createCell(0);
            assPerCell.setCellValue("Assessment Period");
            Cell dateCell = headerRow.createCell(1);
            dateCell.setCellValue("Date");
            Cell participantsCell = headerRow.createCell(2);
            participantsCell.setCellValue("Participants");
            Cell gapCell = headerRow.createCell(3);
            gapCell.setCellValue("Average Gap");

            Integer rowCount = 1;
            for (AllReportDTO r : allReportDTOS) {
                Row row = sheet.createRow(rowCount);
                List<String> data = new ArrayList<>();
                data.add(r.getEventPeriod().getPeriodName());
                data.add(r.getEventPeriod().getDateStart().toString());
                data.add(r.getParticipants().toString());
                data.add(r.getRerataGap().toString());

                Integer cellCounter = 0;
                for (String d : data) {
                    Cell cell = row.createCell(cellCounter);
                    cell.setCellValue(d);
                    cellCounter++;
                }
                rowCount++;
            }

        }

        return workbook;
    }


}
