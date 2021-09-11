package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureDtoToUnitOfMeasureTest {

    static final String DESCRIPTION = "description";
    static final Long LONG_VALUE = 1L;

    UnitOfMeasureDtoToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureDtoToUnitOfMeasure();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureDto()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasureDto uomDTO = new UnitOfMeasureDto();
        uomDTO.setId(LONG_VALUE);
        uomDTO.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure uom = converter.convert(uomDTO);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}