package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.IngredientToIngredientDto;
import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientDto ingredientToIngredientDto;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientDto ingredientToIngredientDto) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientDto = ingredientToIngredientDto;
    }

    @Override
    public IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("No recipe with ID:{}", recipeId);
            throw new RuntimeException("No recipe with ID:" + recipeId);
        }

        Optional<Ingredient> ingredientOptional = recipeOptional.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (ingredientOptional.isEmpty()) {
            log.error("No ingredient with ID:{} in recipeID:{}", ingredientId, recipeId);
            throw new RuntimeException("No ingredient with ID:" + ingredientId);
        }

        return ingredientToIngredientDto.convert(ingredientOptional.get());
    }

}
