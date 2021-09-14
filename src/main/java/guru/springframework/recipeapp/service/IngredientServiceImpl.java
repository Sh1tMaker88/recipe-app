package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.IngredientDtoToIngredient;
import guru.springframework.recipeapp.converter.IngredientToIngredientDto;
import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import guru.springframework.recipeapp.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientDto ingredientToIngredientDto;
    private final IngredientDtoToIngredient ingredientDtoToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientDto ingredientToIngredientDto,
                                 IngredientDtoToIngredient ingredientDtoToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientDto = ingredientToIngredientDto;
        this.ingredientDtoToIngredient = ingredientDtoToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientDto saveIngredientDto(IngredientDto ingredientDto) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientDto.getRecipeId());
        if (recipeOptional.isEmpty()) {
            log.error("Recipe with ID:{} not found", ingredientDto.getRecipeId());
            return new IngredientDto();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientDto.getId()))
                .findFirst();

        if (ingredientOptional.isEmpty()) {
            Ingredient ingredient = ingredientDtoToIngredient.convert(ingredientDto);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        } else {
            Ingredient foundedIngredient = ingredientOptional.get();
            foundedIngredient.setDescription(ingredientDto.getDescription());
            foundedIngredient.setAmount(ingredientDto.getAmount());
            foundedIngredient.setUom(unitOfMeasureRepository
                    .findById(ingredientDto.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UoM not found")));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientDto.getId()))
                .findFirst();

        if (savedIngredientOptional.isEmpty()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(el -> el.getDescription().equals(ingredientDto.getDescription()))
                    .filter(el -> el.getAmount().equals(ingredientDto.getAmount()))
                    .filter(el -> el.getUom().getId().equals(ingredientDto.getUom().getId()))
                    .findFirst();
        }

        return ingredientToIngredientDto.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("No such recipe with ID:{}", recipeId);
            throw new RuntimeException("No such recipe with ID:" + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(el -> el.getId().equals(ingredientId))
                .findFirst();
        if (ingredientOptional.isEmpty()) {
            log.error("No such ingredient with ID:{} in recipeID:{}", ingredientId, recipeId);
            throw new RuntimeException("No such ingredient with ID:" + ingredientId);
        }
        ingredientOptional.get().setRecipe(null);
        recipe.getIngredients().remove(ingredientOptional.get());

        recipeRepository.save(recipe);
    }
}
