package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.dto.UnitOfMeasureDto;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureDto> listAllUom();
}
