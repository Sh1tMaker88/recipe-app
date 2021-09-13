package guru.springframework.recipeapp.controller;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import guru.springframework.recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @InjectMocks
    IngredientController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void ListIngredientsTest() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();

        //when
        when(recipeService.findRecipeDtoById(anyLong())).thenReturn(recipeDto);

        //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findRecipeDtoById(anyLong());
    }

    @Test
    void showIngredientTest() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDto);

        //then
        mockMvc.perform(get("/recipe/1/ingredients/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void updateIngredientFormTest() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientDto);
        when(unitOfMeasureService.listAllUom()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredients/3/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void saveOrUpdateIngredientTest() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(3L);
        ingredientDto.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredientDto(any())).thenReturn(ingredientDto);

        //then
        mockMvc.perform(post("/recipe/2/ingredients")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients/3/show"));
    }
}
