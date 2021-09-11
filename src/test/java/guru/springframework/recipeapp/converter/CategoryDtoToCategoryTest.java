package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.CategoryDto;
import guru.springframework.recipeapp.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoToCategoryTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryDtoToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryDtoToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryDto()));
    }

    @Test
    void convert() {
        //given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID_VALUE);
        categoryDto.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(categoryDto);

        //then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}