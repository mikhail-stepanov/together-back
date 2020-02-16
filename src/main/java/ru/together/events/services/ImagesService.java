package ru.together.events.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.together.common.exceptions.CommonException;
import ru.together.common.exceptions.ObjectNotFoundException;
import ru.together.database.entities.Images;
import ru.together.database.services.DatabaseService;
import ru.together.events.interfaces.IImagesService;
import ru.together.events.models.images.AddImagesRequest;
import ru.together.events.models.images.AddImagesResponse;
import ru.together.events.models.images.GetImagesRequest;
import ru.together.events.models.images.GetImagesResponse;

import javax.annotation.PostConstruct;

public class ImagesService implements IImagesService {

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(ImagesService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }


    @Override
    public AddImagesResponse add(AddImagesRequest request) throws CommonException {
        try {
            Images images = objectContext.newObject(Images.class);

            images.setUrl(request.getUrl());
            images.setContent(request.getContent());
            images.setName(request.getName());

            objectContext.commitChanges();

            return AddImagesResponse.builder()
                    .id((Integer) images.getObjectId().getIdSnapshot().get("id"))
                    .content(images.getContent())
                    .url(images.getUrl())
                    .name(images.getName())
                    .build();
        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Images upload exception");
        }
    }

    @Override
    public GetImagesResponse get(GetImagesRequest request) throws CommonException {
        try {
            Images images = SelectById.query(Images.class, request.getId()).selectFirst(objectContext);

            return GetImagesResponse.builder()
                    .id((Integer) images.getObjectId().getIdSnapshot().get("id"))
                    .content(images.getContent())
                    .url(images.getUrl())
                    .name(images.getName())
                    .build();

        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getId()), "Images not found");
        }
    }
}
