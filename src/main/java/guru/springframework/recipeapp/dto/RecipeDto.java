package guru.springframework.recipeapp.dto;

import guru.springframework.recipeapp.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDto {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientDto> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesDto notes;
    private Set<CategoryDto> categories = new HashSet<>();
}
