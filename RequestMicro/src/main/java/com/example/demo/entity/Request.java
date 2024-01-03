package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    public enum Status {
        EN_ATTENTE, VALIDEE, REALISEE
    }
    @Enumerated(EnumType.ORDINAL)
    private Status status;
   
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "motif", length = 255)
    private String motif;

    @Column(name = "contenu", columnDefinition = "TEXT") // Pour de longs textes
    private String contenu;




}
