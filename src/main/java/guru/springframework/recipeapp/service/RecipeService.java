package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeDto findRecipeDtoById(Long id);

    RecipeDto saveRecipeDTO(RecipeDto recipeDTO);
}
