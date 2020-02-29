package ru.together.content.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.common.exceptions.CommonException;
import ru.together.common.exceptions.ObjectNotFoundException;
import ru.together.content.interfaces.IImageService;
import ru.together.content.models.GetImageRequest;
import ru.together.content.models.GetImageResponse;
import ru.together.content.models.UploadImageRequest;
import ru.together.content.models.UploadImageResponse;
import ru.together.database.entities.Images;
import ru.together.database.services.DatabaseService;

import javax.annotation.PostConstruct;

@Service
public class ImageService implements IImageService {

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }

    @Override
    public GetImageResponse get(GetImageRequest request) throws CommonException {
        try {
            Images image = SelectById.query(Images.class, request.getId()).selectFirst(objectContext);

            return GetImageResponse.builder()
                    .id((Integer) image.getObjectId().getIdSnapshot().get("id"))
                    .name(image.getName())
                    .content(image.getContent())
                    .build();

        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getId()), "Error while getting image");
        }
    }

    @Override
    public UploadImageResponse upload(UploadImageRequest request) throws CommonException {
        try {
            Images image = objectContext.newObject(Images.class);
            image.setName(request.getName());
            image.setContent(request.getContent());

            objectContext.commitChanges();

            return UploadImageResponse.builder()
                    .id((Integer) image.getObjectId().getIdSnapshot().get("id"))
                    .name(image.getName())
                    .content(image.getContent())
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while adding image");
        }
    }
}
