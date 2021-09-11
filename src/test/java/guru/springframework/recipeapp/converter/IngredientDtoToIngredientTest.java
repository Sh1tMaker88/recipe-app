package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.Ingredient;
import guru.springframework.recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientDtoToIngredientTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;

    IngredientDtoToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientDto()));
    }

    @Test
    void convert() {
        //given
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ID_VALUE);
        ingredientDto.setAmount(AMOUNT);
        ingredientDto.setDescription(DESCRIPTION);
        UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(UOM_ID);
        ingredientDto.setUnitOfMeasure(unitOfMeasureDto);

        //when
        Ingredient ingredient = converter.convert(ingredientDto);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    void convertWithNullUOM() {
        //given
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ID_VALUE);
        ingredientDto.setAmount(AMOUNT);
        ingredientDto.setDescription(DESCRIPTION);

        //when
        Ingredient ingredient = converter.convert(ingredientDto);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());

    }
}