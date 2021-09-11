package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureDtoToUnitOfMeasure implements Converter<UnitOfMeasureDto, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureDto uomDto) {
        if (uomDto == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomDto.getId());
        uom.setDescription(uomDto.getDescription());
        return uom;
    }
}
