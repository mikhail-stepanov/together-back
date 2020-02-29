package ru.together.content.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.together.common.exceptions.CommonException;
import ru.together.content.interfaces.IImageService;
import ru.together.content.models.GetImageRequest;
import ru.together.content.models.GetImageResponse;
import ru.together.content.models.UploadImageRequest;
import ru.together.content.models.UploadImageResponse;
import ru.together.content.services.ImageService;

@RestController
public class ImageEndpoint implements IImageService {

    @Autowired
    ImageService imageService;

    @Override
    @CrossOrigin
    @RequestMapping(value = IMAGE_GET, method = {RequestMethod.POST})
    public GetImageResponse get(@RequestBody GetImageRequest request) throws CommonException {
        return imageService.get(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = IMAGE_UPLOAD, method = {RequestMethod.POST})
    public UploadImageResponse upload(@RequestBody UploadImageRequest request) throws CommonException {
        return imageService.upload(request);
    }
}
