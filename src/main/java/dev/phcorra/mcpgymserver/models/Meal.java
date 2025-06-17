package dev.phcorra.mcpgymserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="meal_time")
    private LocalDateTime mealTime;

    @Column(name="kcal", precision = 10, scale = 2)
    private BigDecimal kcal;

    @Column(name="protein", precision = 10, scale = 2)
    private BigDecimal protein;

    @Column(name="carbo", precision = 10, scale = 2)
    private BigDecimal carbo;

    @Column(name="fat", precision = 10, scale = 2)
    private BigDecimal fat;

}
