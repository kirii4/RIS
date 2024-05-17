package org.example.Units;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private float precipitation;

    @Column(nullable = false)
    private float windSpeed;

    @Column(nullable = false)
    private float pressure;

    @Column(nullable = false)
    private float visibility;

    @Column(nullable = false)
    private Time sunriseTime;

    @Column(nullable = false)
    private Time sunsetTime;

    // Геттеры и сеттеры
}