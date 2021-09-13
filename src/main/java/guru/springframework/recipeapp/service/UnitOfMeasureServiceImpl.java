package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.UnitOfMeasureToUnitOfMeasureDto;
import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto;
    }

    @Override
    public Set<UnitOfMeasureDto> listAllUom() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureDto::convert)
                .collect(Collectors.toSet());
    }
}
