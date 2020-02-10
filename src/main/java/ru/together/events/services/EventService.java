package ru.together.events.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.database.entities.Event;
import ru.together.database.entities.Images;
import ru.together.database.services.DatabaseService;
import ru.together.events.interfaces.IEventService;
import ru.together.events.models.*;
import ru.together.users.services.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }

    @Override
    public AddEventResponse add(AddEventRequest request) {
        try {
            Event event = objectContext.newObject(Event.class);
            event.setCreatedDate(LocalDateTime.now());
            event.setTitle(request.getTitle());
            event.setDate(request.getDate());
            event.setPlace(request.getPlace());
            event.setDescription(request.getDescription());
            event.setTicketcloud(request.getTicketcloud());
            event.setIsFuture(true);
            event.setYoutube(request.getYoutube());
            event.setSoundcloud(request.getSoundcloud());
            event.setCloud(request.getCloud());

            if (request.getPicBigId() != null) {
                Images bigPic = SelectById.query(Images.class, request.getPicBigId()).selectFirst(objectContext);
                event.setEventToBigPic(bigPic);
            }
            if (request.getPicBigId() != null) {
                Images smallPic = SelectById.query(Images.class, request.getPicSmallId()).selectFirst(objectContext);
                event.setEventToSmallPic(smallPic);
            }
            if (request.getPicBigId() != null) {
                Images video = SelectById.query(Images.class, request.getVideo()).selectFirst(objectContext);
                event.setEventToVideo(video);
            }

            objectContext.commitChanges();

            return AddEventResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            log.error("Exception while adding event: " + e.getLocalizedMessage());
            return AddEventResponse.builder()
                    .success(false)
                    .build();
        }
    }

    @Override
    public GetEventResponse get(GetEventRequest request) {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);

            return GetEventResponse.builder()
                    .title(event.getTitle())
                    .date(event.getDate())
                    .place(event.getPlace())
                    .description(event.getDescription())
                    .picBigId(0)
                    .picSmallId(0)
                    .video(0)
                    .ticketcloud(event.getTicketcloud())
                    .isFuture(event.isIsFuture())
                    .youtube(event.getYoutube())
                    .soundcloud(event.getSoundcloud())
                    .cloud(event.getCloud())
                    .build();
        } catch (Exception e) {
            log.error("Exception while getting event: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<EventModel> listAll() {
        try {
            List<EventModel> response = new ArrayList<>();
            List<Event> events = ObjectSelect.query(Event.class).
                    where(Event.DELETED_DATE.isNull())
                    .select(objectContext);

            events.forEach(event -> {
                response.add(EventModel.builder()
                        .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                        .title(event.getTitle())
                        .date(event.getDate())
                        .place(event.getPlace())
                        .description(event.getDescription())
                        .picBigId(0)
                        .picSmallId(0)
                        .video(0)
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            log.error("Exception while getting list of events: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<EventModel> listFuture() {
        try {
            List<EventModel> response = new ArrayList<>();
            List<Event> events = ObjectSelect.query(Event.class).
                    where(Event.DELETED_DATE.isNull()).and(Event.IS_FUTURE.isTrue())
                    .select(objectContext);

            events.forEach(event -> {
                response.add(EventModel.builder()
                        .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                        .title(event.getTitle())
                        .date(event.getDate())
                        .place(event.getPlace())
                        .description(event.getDescription())
                        .picBigId(0)
                        .picSmallId(0)
                        .video(0)
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            log.error("Exception while getting list of future events: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<EventModel> listPast() {
        try {
            List<EventModel> response = new ArrayList<>();
            List<Event> events = ObjectSelect.query(Event.class).
                    where(Event.DELETED_DATE.isNull()).and(Event.IS_FUTURE.isFalse())
                    .select(objectContext);

            events.forEach(event -> {
                response.add(EventModel.builder()
                        .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                        .title(event.getTitle())
                        .date(event.getDate())
                        .place(event.getPlace())
                        .description(event.getDescription())
                        .picBigId(0)
                        .picSmallId(0)
                        .video(0)
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            log.error("Exception while getting list of past events: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public UpdateEventResponse update(UpdateEventRequest request) {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);

            if (request.getPicBigId() != null) {
                Images bigPic = SelectById.query(Images.class, request.getPicBigId()).selectFirst(objectContext);
                event.setEventToBigPic(bigPic);
            }
            if (request.getPicBigId() != null) {
                Images smallPic = SelectById.query(Images.class, request.getPicSmallId()).selectFirst(objectContext);
                event.setEventToSmallPic(smallPic);
            }
            if (request.getPicBigId() != null) {
                Images video = SelectById.query(Images.class, request.getVideo()).selectFirst(objectContext);
                event.setEventToVideo(video);
            }
            event.setTitle(Optional.ofNullable(request.getTitle()).orElse(event.getTitle()));
            event.setDate(Optional.ofNullable(request.getDate()).orElse(event.getDate()));
            event.setPlace(Optional.ofNullable(request.getPlace()).orElse(event.getPlace()));
            event.setDescription(Optional.ofNullable(request.getDescription()).orElse(event.getDescription()));
            event.setTicketcloud(Optional.ofNullable(request.getTicketcloud()).orElse(event.getTicketcloud()));
            event.setIsFuture(Optional.ofNullable(request.isFuture()).orElse(event.isIsFuture()));
            event.setYoutube(Optional.ofNullable(request.getYoutube()).orElse(event.getYoutube()));
            event.setSoundcloud(Optional.ofNullable(request.getSoundcloud()).orElse(event.getSoundcloud()));
            event.setCloud(Optional.ofNullable(request.getCloud()).orElse(event.getCloud()));

            objectContext.commitChanges();

            return UpdateEventResponse.builder()
                    .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                    .title(event.getTitle())
                    .date(event.getDate())
                    .place(event.getPlace())
                    .description(event.getDescription())
                    .picBigId((int) event.getEventToBigPic().getObjectId().getIdSnapshot().get("id"))
                    .picSmallId((int) event.getEventToSmallPic().getObjectId().getIdSnapshot().get("id"))
                    .video((int) event.getEventToVideo().getObjectId().getIdSnapshot().get("id"))
                    .ticketcloud(event.getTicketcloud())
                    .isFuture(event.isIsFuture())
                    .youtube(event.getYoutube())
                    .soundcloud(event.getSoundcloud())
                    .cloud(event.getCloud())
                    .build();

        } catch (Exception e) {
            log.error("Exception while updating event: " + e.getMessage());
            return null;
        }
    }

    @Override
    public RemoveEventResponse remove(RemoveEventRequest request) {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);

            event.setDeletedDate(LocalDateTime.now());

            objectContext.commitChanges();

            return RemoveEventResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            log.error("Exception while removing event: " + e.getMessage());
            return RemoveEventResponse.builder()
                    .success(false)
                    .build();
        }
    }
}
