package com.RequestManagementService.RequestManagementService.Entity;

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

    @OneToOne
    @MapsId
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;

    @OneToOne
    @MapsId
    @JoinColumn(name = "validator_id")
    private Validator validator;


}
