package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.IngredientToIngredientDto;
import guru.springframework.recipeapp.converter.UnitOfMeasureToUnitOfMeasureDto;
import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.model.Ingredient;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private final IngredientToIngredientDto ingredientToIngredientDto;
    @Mock
    RecipeRepository recipeRepository;

    IngredientServiceImpl ingredientService;

    IngredientServiceImplTest() {
        this.ingredientToIngredientDto = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
    }

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientDto);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        Ingredient ingredient4 = new Ingredient();
        ingredient4.setId(4L);

        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        recipe.addIngredient(ingredient4);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //when
        IngredientDto ingredientDto = ingredientService.findByRecipeIdAndIngredientId(1L, 2L);

        //then
        assertEquals(2L, ingredientDto.getId());
        assertEquals(1L, ingredientDto.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }
}