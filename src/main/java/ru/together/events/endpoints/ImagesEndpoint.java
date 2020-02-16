package ru.together.events.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.together.common.exceptions.CommonException;
import ru.together.events.interfaces.IImagesService;
import ru.together.events.models.images.AddImagesRequest;
import ru.together.events.models.images.AddImagesResponse;
import ru.together.events.models.images.GetImagesRequest;
import ru.together.events.models.images.GetImagesResponse;
import ru.together.events.services.ImagesService;

public class ImagesEndpoint implements IImagesService {

    @Autowired
    ImagesService imagesService;

    @Override
    @CrossOrigin
    @RequestMapping(value = IMAGES_ADD, method = {RequestMethod.POST})
    public AddImagesResponse add(AddImagesRequest request) throws CommonException {
        return imagesService.add(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = IMAGES_GET, method = {RequestMethod.POST})
    public GetImagesResponse get(GetImagesRequest request) throws CommonException {
        return imagesService.get(request);
    }
}
