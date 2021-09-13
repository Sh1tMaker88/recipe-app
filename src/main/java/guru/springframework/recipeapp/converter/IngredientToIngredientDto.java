package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientDto implements Converter<Ingredient, IngredientDto> {

    private final UnitOfMeasureToUnitOfMeasureDto uomConverter;

    public IngredientToIngredientDto(UnitOfMeasureToUnitOfMeasureDto uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientDto convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        final IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            ingredientDto.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientDto.setAmount(ingredient.getAmount());
        ingredientDto.setDescription(ingredient.getDescription());
        if (ingredient.getUom() != null) {
            ingredientDto.setUom(uomConverter.convert(ingredient.getUom()));
        }

        return ingredientDto;
    }
}
