package com.firattamur.inspection_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "answers")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id")
    private InspectionEntity inspection;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "has_issue")
    private Boolean hasIssue;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "answer")
    private List<InspectionPhotoEntity> photos;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}




