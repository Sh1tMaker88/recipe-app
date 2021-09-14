package guru.springframework.recipeapp.service;

import guru.springframework.recipeapp.model.Recipe;
import guru.springframework.recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if (recipeOptional.isEmpty()) {
                log.error("No sucre recipe with ID:{}", recipeId);
                throw new RuntimeException("No such recipe with ID:" + recipeId);
            }

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte bytes : file.getBytes()) {
                byteObjects[i++] = bytes;
            }

            recipeOptional.get().setImage(byteObjects);
            recipeRepository.save(recipeOptional.get());
        } catch (IOException e) {

            //todo handle
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }
}
