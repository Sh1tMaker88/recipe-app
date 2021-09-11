package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.RecipeDtoToRecipe;
import guru.springframework.recipeapp.converter.RecipeToRecipeDto;
import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeToRecipeDto recipeToRecipeDto;
    @Autowired
    RecipeDtoToRecipe recipeDtoToRecipe;

    @Autowired
    RecipeServiceImpl recipeService;

    @Transactional
    @Rollback
    @Test
    void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeDto recipeDto = recipeToRecipeDto.convert(testRecipe);

        //when
        recipeDto.setDescription(NEW_DESCRIPTION);
        RecipeDto savedRecipeCommand = recipeService.saveRecipeDTO(recipeDto);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
