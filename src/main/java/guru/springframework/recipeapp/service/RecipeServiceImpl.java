package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.RecipeDtoToRecipe;
import guru.springframework.recipeapp.converter.RecipeToRecipeDto;
import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoToRecipe recipeDtoToRecipe;
    private final RecipeToRecipeDto recipeToRecipeDto;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoToRecipe recipeDtoToRecipe,
                             RecipeToRecipeDto recipeToRecipeDto) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoToRecipe = recipeDtoToRecipe;
        this.recipeToRecipeDto = recipeToRecipeDto;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Im in the service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("No such element in database with ID:" + id);
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeDto findRecipeDtoById(Long id) {
        Recipe recipe = findById(id);
        return recipeToRecipeDto.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeDto saveRecipeDTO(RecipeDto recipeDTO) {
        Recipe detachedRecipe = recipeDtoToRecipe.convert(recipeDTO);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved recipeId:" + savedRecipe.getId());

        return recipeToRecipeDto.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting recipe with ID:" + id);
        recipeRepository.deleteById(id);
    }
}
