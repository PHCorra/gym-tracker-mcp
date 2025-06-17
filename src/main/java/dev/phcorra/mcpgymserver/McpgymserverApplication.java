package dev.phcorra.mcpgymserver;

import dev.phcorra.mcpgymserver.service.MealService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpgymserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpgymserverApplication.class, args);

	}

	@Bean
	public List<ToolCallback> mealsTools(MealService mealService) {
		return List.of(ToolCallbacks.from(mealService));
	}

}
