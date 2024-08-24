package com.firattamur.inspection_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inspection_photos")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    private String url;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
