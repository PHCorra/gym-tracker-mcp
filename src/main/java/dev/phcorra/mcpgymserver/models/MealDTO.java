package dev.phcorra.mcpgymserver.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MealDTO(Integer id, LocalDateTime mealTime, BigDecimal kcal, BigDecimal protein, BigDecimal carbo,
                      BigDecimal fat) {
}
