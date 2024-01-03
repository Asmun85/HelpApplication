package com.RequestManagementService.RequestManagementService.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "benevole")
public class Benevole {

    @Id
    @Column(name = "user_id")
    private Long id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User benevole;
}
