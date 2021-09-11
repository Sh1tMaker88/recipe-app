package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Category;
import guru.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeDto implements Converter<Recipe, RecipeDto> {

    private final NotesToNotesDto notesConverter;
    private final CategoryToCategoryDto categoryConverter;
    private final IngredientToIngredientDto ingredientConverter;

    public RecipeToRecipeDto(NotesToNotesDto notesConverter, CategoryToCategoryDto categoryConverter, IngredientToIngredientDto ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeDto convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setCookTime(recipe.getCookTime());
        recipeDto.setPrepTime(recipe.getPrepTime());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setDifficulty(recipe.getDifficulty());
        recipeDto.setDirections(recipe.getDirections());
        recipeDto.setServings(recipe.getServings());
        recipeDto.setSource(recipe.getSource());
        recipeDto.setUrl(recipe.getUrl());
        if (recipe.getNotes() != null) {
            recipeDto.setNotes(notesConverter.convert(recipe.getNotes()));
        }

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach((Category category) -> recipeDto.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> recipeDto.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipeDto;
    }
}
