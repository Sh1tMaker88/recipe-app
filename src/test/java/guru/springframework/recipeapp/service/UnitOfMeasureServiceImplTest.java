package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.converter.UnitOfMeasureToUnitOfMeasureDto;
import guru.springframework.recipeapp.dto.UnitOfMeasureDto;
import guru.springframework.recipeapp.model.UnitOfMeasure;
import guru.springframework.recipeapp.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Spy
    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;
    @InjectMocks
    UnitOfMeasureServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void listAllUom() {
        //given
        Set<UnitOfMeasure> unitOfMeasuresSet = new HashSet<>();
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);
        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure3.setId(3L);

        unitOfMeasuresSet.add(unitOfMeasure1);
        unitOfMeasuresSet.add(unitOfMeasure2);
        unitOfMeasuresSet.add(unitOfMeasure3);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasuresSet);

        //when
        Set<UnitOfMeasureDto> unitOfMeasureDtoSet = service.listAllUom();

        //then
        assertEquals(3, unitOfMeasureDtoSet.size());
        verify(unitOfMeasureRepository).findAll();
    }
}