package guru.springframework.recipeapp.converter;

import guru.springframework.recipeapp.dto.NotesDto;
import guru.springframework.recipeapp.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesDtoTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesDto converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesDto();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesDto notesDto = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, notesDto.getId());
        assertEquals(RECIPE_NOTES, notesDto.getRecipeNotes());
    }
}