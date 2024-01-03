package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "request_id")
    private Long id;
    @Column(name = "demandeur_id")
    private Long demandeurId;

    @Column(name = "benevole_id")
    private Long benevoleId;

    @Column(name = "validator_id")
    private Long validatorId; // ID du validateur

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status; // Enum pour le statut

    public enum RequestStatus {
        EN_ATTENTE, VALIDEE, REALISEE, REFUSEE;
    }

    @Setter
    @Column(name = "motif")
    private String motif;

    @Column(name = "contenu")
    private String contenu;


}

