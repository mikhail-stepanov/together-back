package ru.together.events.interfaces;

import ru.together.common.exceptions.CommonException;
import ru.together.events.models.images.AddImagesRequest;
import ru.together.events.models.images.AddImagesResponse;
import ru.together.events.models.images.GetImagesRequest;
import ru.together.events.models.images.GetImagesResponse;

public interface IImagesService {

    String IMAGES_ADD = "/v1/images/add";
    String IMAGES_GET = "/v1/images/get";

    AddImagesResponse add(AddImagesRequest request) throws CommonException;

    GetImagesResponse get(GetImagesRequest request) throws CommonException;
}
