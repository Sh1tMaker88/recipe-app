package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientDtoTest {

    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long RECIPE_ID = 1L;
    public static final Long UOM_ID = 2L;

    IngredientToIngredientDto converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());

    }

    @Test
    void testNullConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void testConvertNullUOM() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUom(null);
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        ingredient.setRecipe(recipe);

        //when
        IngredientDto ingredientDto = converter.convert(ingredient);

        //then
        assertNull(ingredientDto.getUom());
        assertEquals(ID_VALUE, ingredientDto.getId());
        assertEquals(RECIPE_ID, ingredientDto.getRecipeId());
        assertEquals(AMOUNT, ingredientDto.getAmount());
        assertEquals(DESCRIPTION, ingredientDto.getDescription());
    }

    @Test
    void testConvertWithUom() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        ingredient.setRecipe(recipe);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        //when
        IngredientDto ingredientDto = converter.convert(ingredient);
        //then
        assertEquals(ID_VALUE, ingredientDto.getId());
        assertNotNull(ingredientDto.getUom());
        assertEquals(RECIPE_ID, ingredientDto.getRecipeId());
        assertEquals(UOM_ID, ingredientDto.getUom().getId());
        assertEquals(AMOUNT, ingredientDto.getAmount());
        assertEquals(DESCRIPTION, ingredientDto.getDescription());
    }
}