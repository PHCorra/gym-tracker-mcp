package dev.phcorra.mcpgymserver.service;

import dev.phcorra.mcpgymserver.models.Meal;
import dev.phcorra.mcpgymserver.models.MealDTO;
import dev.phcorra.mcpgymserver.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {

    private static final String getMeals_TOOL_DESCRIPTION = "Get all meals registered in the database. Returns a " +
            "complete list of all user meals with their details including mealTime, kcal, protein, carbohydrates, and fat content.";

    private static final String getMealsByDay_TOOL_DESCRIPTION =
            "Get all meals for a specific day. The user input can be natural language like \"yesterday\", \"today\", or a specific date such as \"16th of June\". " +
                    "You should convert this input into a Java LocalDate, then create two LocalDateTime variables: one for the start of the day (00:00) and one for the end of the day (23:59:59.999999999) to query the meals within that range.\n\n" +
                    "Step-by-step guide to process user input and retrieve meals by day:\n" +
                    "1. Receive user input as a string, which can be:\n" +
                    "   - Natural terms like \"yesterday\", \"today\", \"tomorrow\"\n" +
                    "   - Specific dates like \"16th of June\", \"2025-06-16\"\n\n" +
                    "2. Convert this string into a Java LocalDate:\n" +
                    "   - \"yesterday\" -> LocalDate.now().minusDays(1)\n" +
                    "   - \"today\" -> LocalDate.now()\n" +
                    "   - \"tomorrow\" -> LocalDate.now().plusDays(1)\n" +
                    "   - \"16th of June\" -> LocalDate.of(currentYear, 6, 16)\n" +
                    "   - \"2025-06-16\" -> LocalDate.parse(\"2025-06-16\")\n\n" +
                    "3. Create two LocalDateTime objects to define the day's interval:\n" +
                    "   - start = localDate.atStartOfDay() (e.g., 2025-06-16T00:00)\n" +
                    "   - end = localDate.atTime(LocalTime.MAX) (e.g., 2025-06-16T23:59:59.999999999)\n\n" +
                    "4. Use start and end to query all meals where mealTime (LocalDateTime) is between these values.\n\n" +
                    "What NOT to do:\n" +
                    " - Do not return only the meal at the exact hour provided; retrieve all meals of the day.\n" +
                    " - Do not use the original input string directly in the query without converting it to a date.\n" +
                    " - Do not ignore the day's interval and query only by date without time or with incomplete LocalDateTime.\n\n" +
                    "The goal is always to return all meals for the entire day corresponding to the user's input.";

    private static final String createMeal_TOOL_DESCRIPTION = "The mealTime field must be passed as a string in the format yyyy-MM-dd'T'HH:mm:ss, " +
            "which maps to a LocalDateTime in Java.\n" +
            "The carbo, protein, fat, and kcal fields are of type BigDecimal in Java, so always pass them\n " +
            "as numeric values with a decimal point (e.g., 32.5 — not 32,5 or 32).\n" +
            "You are allowed to create meals for any date, not just today. If the user does not provide\n" +
            "a specific hour, you may choose any reasonable time during the requested day.\n" +
            "remember to choose a value and not work with a range of values";

    private static final String deleteMeal_TOOL_DESCRIPTION = "Delete a specific meal by its ID. You must provide the unique integer ID of the meal to be deleted.";

    private final MealRepository mealRepository;

    @Tool(name = "get_meals", description = getMeals_TOOL_DESCRIPTION)
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @Tool(name = "create_meal", description = createMeal_TOOL_DESCRIPTION)
    public Meal createMeal (MealDTO mealDTO)  {
        Meal meal = Meal.builder()
                .mealTime(mealDTO.mealTime())
                .kcal(mealDTO.kcal())
                .protein(mealDTO.protein())
                .carbo(mealDTO.carbo())
                .fat(mealDTO.fat())
                .build();
        return mealRepository.save(meal);
    }

    @Tool(name="get_meal_by_day", description = getMealsByDay_TOOL_DESCRIPTION)
    public List<Meal> getMealsByDay(LocalDateTime start, LocalDateTime end) {
        List<Meal> meal = mealRepository.findAllByMealTimeBetween(start, end);
        return meal;
    }

    @Tool(name="delete_meal", description = deleteMeal_TOOL_DESCRIPTION)
    public void deteleMealById(Integer id) {
        mealRepository.deleteById(id);
    }
}
