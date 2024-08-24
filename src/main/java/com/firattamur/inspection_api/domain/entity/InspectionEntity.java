package com.firattamur.inspection_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inspections")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "car_id")
    private String carId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inspection")
    private List<AnswerEntity> answers;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
