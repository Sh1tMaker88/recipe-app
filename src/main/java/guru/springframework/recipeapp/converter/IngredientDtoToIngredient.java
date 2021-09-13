package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToIngredient implements Converter<IngredientDto, Ingredient> {

    private final UnitOfMeasureDtoToUnitOfMeasure uomConverter;

    public IngredientDtoToIngredient(UnitOfMeasureDtoToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientDto ingredientDto) {
        if (ingredientDto == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.getId());
        ingredient.setAmount(ingredientDto.getAmount());
        ingredient.setDescription(ingredientDto.getDescription());
        ingredient.setUom(uomConverter.convert(ingredientDto.getUom()));

        return ingredient;
    }
}
