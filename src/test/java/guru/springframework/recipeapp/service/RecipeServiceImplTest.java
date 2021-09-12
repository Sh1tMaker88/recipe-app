package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.RecipeToRecipeDto;
import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeDto recipeToRecipeDto;
//    @Mock
//    RecipeDtoToRecipe recipeDtoToRecipe;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getRecipeByIdTest() {
        Recipe recipe = Recipe.builder().id(1L).build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe returnedRecipe = recipeService.findById(1L);

        assertNotNull(returnedRecipe);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipeDtoByIdTest() {
        Recipe recipe = Recipe.builder().id(1L).build();

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeToRecipeDto.convert(any())).thenReturn(recipeDto);

        RecipeDto recipeById = recipeService.findRecipeDtoById(1L);

        assertNotNull(recipeById);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
        verify(recipeToRecipeDto).convert(any());
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> recipeSet = recipeService.getRecipes();

        assertEquals(recipeSet.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }


}