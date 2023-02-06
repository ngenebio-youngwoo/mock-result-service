package com.dptablo.template.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngenebio.msa.vdp.model.ResponseDto;
import com.ngenebio.msa.vdp.model.hla.chart.BaseVariationPlotChartData;
import com.ngenebio.msa.vdp.model.hla.chart.CoveragePlotChartData;
import com.ngenebio.msa.vdp.model.hla.report.HlaStandardReportData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/result/hla")
public class HlaResultController {

    @GetMapping
    @RequestMapping("/report/standard/{runId}")
    public ResponseEntity<ResponseDto<HlaStandardReportData>> getHlaStandardReportData(
            @PathVariable("runId") String runId,
            @RequestParam("sampleIdList") List<String> sampleIdList
    ) throws IOException {
        var resource = new ClassPathResource(
                "data.hla/standard.json", this.getClass().getClassLoader());
        var jsonString = new String(
                Files.readAllBytes(resource.getFile().toPath())
        );

        var objectMapper = new ObjectMapper();
        var reportData = objectMapper.readValue(jsonString, HlaStandardReportData.class);

        var responseDto = ResponseDto.<HlaStandardReportData>builder()
                .code(HttpStatus.OK.value())
                .data(reportData)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(responseDto);
    }

    @GetMapping
    @RequestMapping("/chart/base-variation-plot/{runId}/{sampleId}/{locusName}")
    public ResponseEntity<ResponseDto<BaseVariationPlotChartData>> getBaseVariationPlotData() throws IOException {
        var resource = new ClassPathResource(
                "data.hla/baseVariationPlotData.json", this.getClass().getClassLoader());
        var jsonString = new String(
                Files.readAllBytes(resource.getFile().toPath())
        );

        var objectMapper = new ObjectMapper();
        var baseVariationPlotChartData = objectMapper.readValue(jsonString, BaseVariationPlotChartData.class);

        var responseDto = ResponseDto.<BaseVariationPlotChartData>builder()
                .code(HttpStatus.OK.value())
                .data(baseVariationPlotChartData)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(responseDto);
    }

    @GetMapping
    @RequestMapping("/chart/coverage-plot/{runId}/{sampleId}/{locusName}")
    public ResponseEntity<ResponseDto<CoveragePlotChartData>> getCoveragePlotData() throws IOException {
        var resource = new ClassPathResource(
                "data.hla/coveragePlotData.json", this.getClass().getClassLoader());
        var jsonString = new String(
                Files.readAllBytes(resource.getFile().toPath())
        );

        var objectMapper = new ObjectMapper();
        var coveragePlotChartData = objectMapper.readValue(jsonString, CoveragePlotChartData.class);

        var responseDto = ResponseDto.<CoveragePlotChartData>builder()
                .code(HttpStatus.OK.value())
                .data(coveragePlotChartData)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(responseDto);
    }
}
