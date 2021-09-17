package guru.springframework.recipeapp.controller;

import guru.springframework.recipeapp.dto.RecipeDto;
import guru.springframework.recipeapp.service.ImageService;
import guru.springframework.recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    @InjectMocks
    ImageController imageController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getImageForm() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        //when
        when(recipeService.findRecipeDtoById(anyLong())).thenReturn(recipeDto);

        //then
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findRecipeDtoById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection());
//                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService).saveImageFile(anyLong(), any());
    }

    @Test
    void renderImageFromDB() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        String str = "fake image text";
        Byte[] bytesBoxed = new Byte[str.getBytes().length];

        int i = 0;
        for (byte bytes : str.getBytes()) {
            bytesBoxed[i++] = bytes;
        }
        recipeDto.setImage(bytesBoxed);

        //when
        when(recipeService.findRecipeDtoById(anyLong())).thenReturn(recipeDto);

        //then
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(str.getBytes().length, responseBytes.length);
    }

    @Test
    void getImageNumberFormatExceptionTest() throws Exception {

        mockMvc.perform(get("/recipe/adf/recipeImage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}