package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeDtoTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;
    RecipeToRecipeDto converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeDto(new NotesToNotesDto(), new CategoryToCategoryDto(),
                new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto()));
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeDto recipeDto = converter.convert(recipe);

        //then
        assertNotNull(recipeDto);
        assertEquals(RECIPE_ID, recipeDto.getId());
        assertEquals(COOK_TIME, recipeDto.getCookTime());
        assertEquals(PREP_TIME, recipeDto.getPrepTime());
        assertEquals(DESCRIPTION, recipeDto.getDescription());
        assertEquals(DIFFICULTY, recipeDto.getDifficulty());
        assertEquals(DIRECTIONS, recipeDto.getDirections());
        assertEquals(SERVINGS, recipeDto.getServings());
        assertEquals(SOURCE, recipeDto.getSource());
        assertEquals(URL, recipeDto.getUrl());
        assertEquals(NOTES_ID, recipeDto.getNotes().getId());
        assertEquals(2, recipeDto.getCategories().size());
        assertEquals(2, recipeDto.getIngredients().size());
    }
}