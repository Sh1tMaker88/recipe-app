package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.dto.IngredientDto;

public interface IngredientService {

    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto saveIngredientDto(IngredientDto ingredientDto);
}
