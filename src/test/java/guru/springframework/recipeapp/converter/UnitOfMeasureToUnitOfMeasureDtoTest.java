package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureDtoTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureToUnitOfMeasureDto converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureDto();
    }

    @Test
    void testNullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObj() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureDto uomDto = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomDto.getId());
        assertEquals(DESCRIPTION, uomDto.getDescription());
    }
}