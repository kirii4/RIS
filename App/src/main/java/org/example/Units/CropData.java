package org.example.Units;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "crop_data")
public class CropData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;

    @ManyToOne
    @JoinColumn(name = "weather_id", nullable = false)
    private WeatherData weatherData;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String cropType;

    @Column(nullable = false)
    private float yield;

    @Column(nullable = false)
    private float soilMoisture;

    @Column(nullable = false)
    private String pestInfestation;

    @Column(nullable = false)
    private String cropHealth;

    @Column(nullable = false)
    private String growthStage;

    // Геттеры и сеттеры
}