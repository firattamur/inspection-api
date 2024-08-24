package com.firattamur.inspection_api.controller;

import com.firattamur.inspection_api.domain.dto.InspectionRequestDTO;
import com.firattamur.inspection_api.domain.dto.InspectionResponseDTO;
import com.firattamur.inspection_api.service.InspectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inspections")
@Slf4j
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(
            InspectionService inspectionService
    ) {
        this.inspectionService = inspectionService;
    }

    @PostMapping
    public ResponseEntity<InspectionResponseDTO> createInspection(@RequestBody InspectionRequestDTO inspectionRequestDTO) {
        log.info("Creating inspection for car: {}", inspectionRequestDTO.carId());

        InspectionResponseDTO response = inspectionService.createInspection(inspectionRequestDTO);
        log.info("Inspection created for car: {}", inspectionRequestDTO.carId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<InspectionResponseDTO> getInspection(@PathVariable String carId) {
        log.info("Getting inspection for car: {}", carId);

        InspectionResponseDTO response = inspectionService.getInspection(carId);
        log.info("Inspection retrieved for car: {}", carId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
