package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.CategoryDto;
import guru.springframework.recipeapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToCategory implements Converter<CategoryDto, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(categoryDto.getId());
        category.setDescription(categoryDto.getDescription());

        return category;
    }
}
