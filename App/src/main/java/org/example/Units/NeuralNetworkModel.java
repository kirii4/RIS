package org.example.Units;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "neural_network_model")
public class NeuralNetworkModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private CropData cropData;

    @Column(nullable = false)
    private String architecture;

    @Column(nullable = false)
    private String trainingAlgorithm;

    @Column(nullable = false)
    private float accuracy;

    @Column(nullable = false)
    private String trainedOnData;

    @Column(nullable = false)
    private Time trainingTime;

    // Геттеры и сеттеры
}