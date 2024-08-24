package com.firattamur.inspection_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    @PostPersist
    public void onPostPersist() {
        this.createdDate = LocalDateTime.now();
    }

}
