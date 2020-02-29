package ru.together.content.interfaces;

import ru.together.common.exceptions.CommonException;
import ru.together.content.models.GetImageRequest;
import ru.together.content.models.GetImageResponse;
import ru.together.content.models.UploadImageRequest;
import ru.together.content.models.UploadImageResponse;

public interface IImageService {

    String IMAGE_GET = "/v1/image/get";
    String IMAGE_UPLOAD = "/v1/image/upload";

    GetImageResponse get(GetImageRequest request) throws CommonException;

    UploadImageResponse upload(UploadImageRequest request) throws CommonException;
}
