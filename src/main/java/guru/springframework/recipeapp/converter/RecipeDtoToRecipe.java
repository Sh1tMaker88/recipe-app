package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoToRecipe implements Converter<RecipeDto, Recipe> {

    private final NotesDtoToNotes notesConverter;
    private final CategoryDtoToCategory categoryConverter;
    private final IngredientDtoToIngredient ingredientConverter;

    public RecipeDtoToRecipe(NotesDtoToNotes notesConverter, CategoryDtoToCategory categoryConverter,
                             IngredientDtoToIngredient ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Override
    public Recipe convert(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setCookTime(recipeDto.getCookTime());
        recipe.setPrepTime(recipeDto.getPrepTime());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setDifficulty(recipeDto.getDifficulty());
        recipe.setDirections(recipeDto.getDirections());
        recipe.setServings(recipeDto.getServings());
        recipe.setSource(recipeDto.getSource());
        recipe.setImage(recipeDto.getImage());
        recipe.setUrl(recipeDto.getUrl());
        if (recipeDto.getNotes() != null) {
            recipe.setNotes(notesConverter.convert(recipeDto.getNotes()));
        }

        if (recipeDto.getCategories() != null && recipeDto.getCategories().size() > 0){
            recipeDto.getCategories()
                    .forEach( category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipeDto.getIngredients() != null && recipeDto.getIngredients().size() > 0){
            recipeDto.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
