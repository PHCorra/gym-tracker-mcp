package dev.phcorra.mcpgymserver.repository;

import dev.phcorra.mcpgymserver.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findAllByMealTimeBetween(LocalDateTime start, LocalDateTime end);
}
