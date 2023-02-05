package com.dptablo.template.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngenebio.msa.vdp.model.ResponseDto;
import com.ngenebio.msa.vdp.model.hla.chart.BaseVariationPlotChartData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/result/hla")
public class HlaResultController {

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
}
