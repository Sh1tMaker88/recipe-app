package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureDto implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            return null;
        }

        final UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(unitOfMeasure.getId());
        unitOfMeasureDto.setDescription(unitOfMeasure.getDescription());

        return unitOfMeasureDto;
    }
}
