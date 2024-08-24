package com.firattamur.inspection_api.service;

import com.firattamur.inspection_api.domain.dto.*;
import com.firattamur.inspection_api.domain.entity.AnswerEntity;
import com.firattamur.inspection_api.domain.entity.InspectionEntity;
import com.firattamur.inspection_api.domain.entity.InspectionPhotoEntity;
import com.firattamur.inspection_api.domain.entity.QuestionEntity;
import com.firattamur.inspection_api.repository.InspectionRepository;
import com.firattamur.inspection_api.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final QuestionRepository questionRepository;

    public InspectionService(InspectionRepository inspectionRepository, QuestionRepository questionRepository) {
        this.inspectionRepository = inspectionRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public InspectionResponseDTO createInspection(InspectionRequestDTO createInspectionDTO) {
        LocalDateTime now = LocalDateTime.now();

        InspectionEntity inspection = InspectionEntity.builder()
                .carId(createInspectionDTO.carId())
                .createdAt(now)
                .build();
        log.info("Creating inspection for car: {}", createInspectionDTO.carId());

        List<AnswerEntity> answers = createInspectionDTO.answers().stream()
                .map(answerDTO -> createAnswer(answerDTO, inspection, now))
                .collect(Collectors.toList());
        log.info("Answers created for inspection: {}", inspection.getId());

        inspection.setAnswers(answers);

        InspectionEntity savedInspection = inspectionRepository.save(inspection);
        log.info("Inspection created for car: {}", createInspectionDTO.carId());

        return mapToInspectionResponseDTO(savedInspection);
    }

    public InspectionResponseDTO getInspection(String carId) {
        List<QuestionEntity> questions = questionRepository.findAllByOrderByOrderNumberAsc();
        log.info("Getting questions for inspection");

        Optional<InspectionEntity> latestInspection = inspectionRepository.findFirstByCarIdOrderByCreatedAtDesc(carId);
        log.info("Getting inspection for car: {}", carId);

        List<QuestionResponseDTO> questionResponses = questions.stream()
                .map(question -> mapToQuestionResponseDTO(question, latestInspection.orElse(null)))
                .collect(Collectors.toList());
        log.info("Questions retrieved for inspection");

        return InspectionResponseDTO.builder()
                .carId(carId)
                .inspectionId(latestInspection.map(InspectionEntity::getId).orElse(null))
                .createdAt(latestInspection.map(InspectionEntity::getCreatedAt).orElse(null))
                .questions(questionResponses)
                .build();
    }

    private AnswerEntity createAnswer(AnswerRequestDTO answerDTO, InspectionEntity inspection, LocalDateTime now) {
        QuestionEntity question = questionRepository.findById(answerDTO.questionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + answerDTO.questionId()));

        AnswerEntity answer = AnswerEntity.builder()
                .inspection(inspection)
                .questionId(question.getId())
                .hasIssue(answerDTO.hasIssue())
                .description(answerDTO.description())
                .createdAt(now)
                .build();

        List<InspectionPhotoEntity> photos = answerDTO.photos().stream()
                .map(photoDTO -> createPhoto(photoDTO, answer, now))
                .collect(Collectors.toList());

        answer.setPhotos(photos);

        return answer;
    }

    private InspectionPhotoEntity createPhoto(InspectionPhotoRequestDTO photoDTO, AnswerEntity answer, LocalDateTime now) {
        return InspectionPhotoEntity.builder()
                .answer(answer)
                .url(photoDTO.url())
                .orderNumber(photoDTO.orderNumber())
                .createdAt(now)
                .build();
    }

    private InspectionResponseDTO mapToInspectionResponseDTO(InspectionEntity inspection) {
        List<QuestionResponseDTO> questionResponses = inspection.getAnswers().stream()
                .map(this::mapToQuestionResponseDTO)
                .collect(Collectors.toList());

        return InspectionResponseDTO.builder()
                .carId(inspection.getCarId())
                .inspectionId(inspection.getId())
                .createdAt(inspection.getCreatedAt())
                .questions(questionResponses)
                .build();
    }

    private QuestionResponseDTO mapToQuestionResponseDTO(AnswerEntity answer) {
        QuestionEntity question = questionRepository.findById(answer.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + answer.getQuestionId()));

        return new QuestionResponseDTO(
                question.getId(),
                question.getText(),
                question.getOrderNumber(),
                mapToAnswerResponseDTO(answer)
        );
    }

    private QuestionResponseDTO mapToQuestionResponseDTO(QuestionEntity question, InspectionEntity inspection) {
        AnswerResponseDTO answer = null;
        if (inspection != null) {
            Optional<AnswerEntity> answerEntity = inspection.getAnswers().stream()
                    .filter(a -> a.getQuestionId().equals(question.getId()))
                    .findFirst();

            if (answerEntity.isPresent()) {
                answer = mapToAnswerResponseDTO(answerEntity.get());
            }
        }

        return new QuestionResponseDTO(
                question.getId(),
                question.getText(),
                question.getOrderNumber(),
                answer
        );
    }

    private AnswerResponseDTO mapToAnswerResponseDTO(AnswerEntity answer) {
        List<String> photoUrls = answer.getPhotos().stream()
                .map(InspectionPhotoEntity::getUrl)
                .collect(Collectors.toList());

        return new AnswerResponseDTO(
                answer.getHasIssue(),
                answer.getDescription(),
                photoUrls
        );
    }
}