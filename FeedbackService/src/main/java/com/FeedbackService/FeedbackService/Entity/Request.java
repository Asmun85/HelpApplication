package com.FeedbackService.FeedbackService.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "request")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "motif")
    private String motif;

    @Column(name = "contenu")
    private String contenu;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "demandeur_id")
    private Long demandeurId;

    @Column(name = "benevole_id")
    private Long benevoleId;

    @Column(name = "validator_id")
    private Long validatorId;


}
