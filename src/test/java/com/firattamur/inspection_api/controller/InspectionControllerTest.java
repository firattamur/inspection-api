package com.firattamur.inspection_api.controller;

import com.firattamur.inspection_api.domain.dto.InspectionRequestDTO;
import com.firattamur.inspection_api.domain.dto.InspectionResponseDTO;
import com.firattamur.inspection_api.service.InspectionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InspectionControllerTest {

    @Mock
    private InspectionService inspectionService;

    @InjectMocks
    private InspectionController inspectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateInspectionWithValidData() {
        InspectionRequestDTO requestDTO = new InspectionRequestDTO("car123", List.of(/* mock answers */));
        InspectionResponseDTO responseDTO = new InspectionResponseDTO("car123", 1L, LocalDateTime.now(), List.of(/* mock questions */));
        when(inspectionService.createInspection(any(InspectionRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<InspectionResponseDTO> response = inspectionController.createInspection(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(inspectionService).createInspection(requestDTO);
    }

    @Test
    void shouldGetInspectionWithValidCarId() {
        String carId = "car123";
        InspectionResponseDTO responseDTO = new InspectionResponseDTO(carId, 1L, LocalDateTime.now(), List.of(/* mock questions */));
        when(inspectionService.getInspection(carId)).thenReturn(responseDTO);

        ResponseEntity<InspectionResponseDTO> response = inspectionController.getInspection(carId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(inspectionService).getInspection(carId);
    }

    @Test
    void shouldThrowExceptionWhenCreateInspectionWithInvalidData() {
        InspectionRequestDTO invalidRequestDTO = new InspectionRequestDTO(null, null);
        when(inspectionService.createInspection(any(InspectionRequestDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        assertThrows(IllegalArgumentException.class, () -> inspectionController.createInspection(invalidRequestDTO));
        verify(inspectionService).createInspection(invalidRequestDTO);
    }

    @Test
    void shouldThrowExceptionWhenGetInspectionWithNonExistentCarId() {
        String nonExistentCarId = "nonexistent123";
        when(inspectionService.getInspection(nonExistentCarId)).thenThrow(new EntityNotFoundException("Inspection not found"));

        assertThrows(EntityNotFoundException.class, () -> inspectionController.getInspection(nonExistentCarId));
        verify(inspectionService).getInspection(nonExistentCarId);
    }

}
