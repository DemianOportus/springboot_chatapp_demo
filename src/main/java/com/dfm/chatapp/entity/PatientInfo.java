package com.dfm.chatapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "patient_info")
public class PatientInfo {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "physical_question")
    private String physicalQuestion;

    @Column(name = "mental_question")
    private String mentalQuestion;

    @Column(name = "social_question")
    private String socialQuestion;

    @Column(name = "socialComplexCare_question")
    private String socialComplexCareQuestion;

    @Column(name = "socialFraility_question")
    private String socialFrailityQuestion;

}
