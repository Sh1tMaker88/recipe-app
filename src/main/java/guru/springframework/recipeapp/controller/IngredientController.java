package guru.springframework.recipeapp.controller;

import guru.springframework.recipeapp.dto.IngredientDto;
import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.service.IngredientService;
import guru.springframework.recipeapp.service.RecipeService;
import guru.springframework.recipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping("/recipe/{recipeId}/ingredients/new")
    public String newIngredient(@PathVariable Long recipeId, Model model) {
        RecipeDto recipeDto = recipeService.findRecipeDtoById(recipeId);
        //todo raise exception if null

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientDto);

        ingredientDto.setUom(new UnitOfMeasureDto());
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());

        return "recipe/ingredients/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
        model.addAttribute("uomList", unitOfMeasureService.listAllUom());

        return "recipe/ingredients/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredients")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientDto ingredientDto) {
        IngredientDto savedIngredientDto = ingredientService.saveIngredientDto(ingredientDto);

        log.debug("Saved recipe id:{}", savedIngredientDto.getRecipeId());
        log.debug("Saved ingredient id:{}", savedIngredientDto.getId());

        return "redirect:/recipe/" + savedIngredientDto.getRecipeId() + "/ingredients/" + savedIngredientDto.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long recipeId,
                                   @PathVariable Long ingredientId) {
        log.debug("Delete ingredient with ID:{} in recipeID:{}", ingredientId, recipeId);
        ingredientService.deleteIngredientById(recipeId, ingredientId);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
