package guru.springframework.recipeapp.controller;

import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientList(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredient from recipe with ID:{}", recipeId);
        model.addAttribute("recipe", recipeService.findRecipeDtoById(recipeId));

        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/show")
    public String shopRecipeIngredient(@PathVariable Long recipeId,
                                       @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredients/show";
    }
}
