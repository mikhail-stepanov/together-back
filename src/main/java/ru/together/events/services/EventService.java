package ru.together.events.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.common.exceptions.CommonException;
import ru.together.common.exceptions.ObjectNotFoundException;
import ru.together.database.entities.*;
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
    public AddEventResponse add(AddEventRequest request) throws CommonException {
        try {
            Event event = objectContext.newObject(Event.class);
            event.setCreatedDate(LocalDateTime.now());
            event.setTitle(request.getTitle());
            event.setDate(request.getDate());
            event.setPlace(request.getPlace());
            event.setDescription(request.getDescription());
            event.setTicketcloud(request.getTicketcloud());
            event.setIsFuture(true);
            event.setPicBig(request.getPicBig());
            event.setPicSmall(request.getPicSmall());
            event.setVideo(request.getVideo());

            objectContext.commitChanges();

            return AddEventResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while adding event");
        }
    }

    @Override
    public GetEventResponse get(GetEventRequest request) throws CommonException {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);

            return GetEventResponse.builder()
                    .title(event.getTitle())
                    .date(event.getDate())
                    .place(event.getPlace())
                    .description(event.getDescription())
                    .picBig(event.getPicBig())
                    .picSmall(event.getPicSmall())
                    .video(event.getVideo())
                    .ticketcloud(event.getTicketcloud())
                    .isFuture(event.isIsFuture())
                    .youtube(event.getYoutube())
                    .soundcloud(event.getSoundcloud())
                    .cloud(event.getCloud())
                    .build();
        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getId()), "Error while getting event");
        }
    }

    @Override
    public List<EventModel> listAll() throws CommonException {
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
                        .picBig(event.getPicBig())
                        .picSmall(event.getPicSmall())
                        .video(event.getVideo())
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of event");
        }
    }

    @Override
    public List<EventModel> listFuture() throws CommonException {
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
                        .picBig(event.getPicBig())
                        .picSmall(event.getPicSmall())
                        .video(event.getVideo())
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of event");
        }
    }

    @Override
    public List<EventModel> listPast() throws CommonException {
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
                        .picBig(event.getPicBig())
                        .picSmall(event.getPicSmall())
                        .video(event.getVideo())
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of event");
        }
    }

    @Override
    public List<EventModel> listFutureUser(EventUserList request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            List<UserTicket> tickets = ObjectSelect.query(UserTicket.class)
                    .where(UserTicket.TICKET_TO_USER.eq(user))
                    .select(objectContext);

            List<EventModel> futureEvents = new ArrayList<>();

            tickets.forEach(userTicket -> {
                Event event = userTicket.getTicketToEvent();
                futureEvents.add(EventModel.builder()
                        .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                        .title(event.getTitle())
                        .date(event.getDate())
                        .place(event.getPlace())
                        .description(event.getDescription())
                        .picBig(event.getPicBig())
                        .picSmall(event.getPicSmall())
                        .video(event.getVideo())
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });
            return futureEvents;
        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getUserId()), "Error while getting list of future user events");
        }
    }

    @Override
    public List<EventModel> listPastUser(EventUserList request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            List<UserPastEvent> pastUserEvents = ObjectSelect.query(UserPastEvent.class)
                    .where(UserPastEvent.PAST_TO_USER.eq(user))
                    .select(objectContext);

            List<EventModel> pastEvents = new ArrayList<>();

            pastUserEvents.forEach(pastEvent -> {
                Event event = pastEvent.getPastToEvent();
                pastEvents.add(EventModel.builder()
                        .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                        .title(event.getTitle())
                        .date(event.getDate())
                        .place(event.getPlace())
                        .description(event.getDescription())
                        .picBig(event.getPicBig())
                        .picSmall(event.getPicSmall())
                        .video(event.getVideo())
                        .ticketcloud(event.getTicketcloud())
                        .isFuture(event.isIsFuture())
                        .youtube(event.getYoutube())
                        .soundcloud(event.getSoundcloud())
                        .cloud(event.getCloud())
                        .build());
            });
            return pastEvents;
        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getUserId()), "Error while getting list of past user events");
        }
    }

    @Override
    public UpdateEventResponse update(UpdateEventRequest request) throws CommonException {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);


            event.setTitle(Optional.ofNullable(request.getTitle()).orElse(event.getTitle()));
            event.setDate(Optional.ofNullable(request.getDate()).orElse(event.getDate()));
            event.setPlace(Optional.ofNullable(request.getPlace()).orElse(event.getPlace()));
            event.setDescription(Optional.ofNullable(request.getDescription()).orElse(event.getDescription()));
            event.setTicketcloud(Optional.ofNullable(request.getTicketcloud()).orElse(event.getTicketcloud()));
            event.setIsFuture(Optional.ofNullable(request.isFuture()).orElse(event.isIsFuture()));
            event.setYoutube(Optional.ofNullable(request.getYoutube()).orElse(event.getYoutube()));
            event.setSoundcloud(Optional.ofNullable(request.getSoundcloud()).orElse(event.getSoundcloud()));
            event.setCloud(Optional.ofNullable(request.getCloud()).orElse(event.getCloud()));
            event.setPicSmall(Optional.ofNullable(request.getPicSmall()).orElse(event.getPicSmall()));
            event.setPicBig(Optional.ofNullable(request.getPicBig()).orElse(event.getPicBig()));
            event.setVideo(Optional.ofNullable(request.getVideo()).orElse(event.getVideo()));


            objectContext.commitChanges();

            return UpdateEventResponse.builder()
                    .id((Integer) event.getObjectId().getIdSnapshot().get("id"))
                    .title(event.getTitle())
                    .date(event.getDate())
                    .place(event.getPlace())
                    .description(event.getDescription())
                    .picBig(event.getPicBig())
                    .picSmall(event.getPicSmall())
                    .video(event.getVideo())
                    .ticketcloud(event.getTicketcloud())
                    .isFuture(event.isIsFuture())
                    .youtube(event.getYoutube())
                    .soundcloud(event.getSoundcloud())
                    .cloud(event.getCloud())
                    .build();

        } catch (Exception e) {
            throw new CommonException(Integer.toString(request.getId()), "Error while updating event");
        }
    }

    @Override
    public RemoveEventResponse remove(RemoveEventRequest request) throws CommonException {
        try {
            Event event = SelectById.query(Event.class, request.getId()).selectFirst(objectContext);

            event.setDeletedDate(LocalDateTime.now());

            objectContext.commitChanges();

            return RemoveEventResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            throw new CommonException(Integer.toString(request.getId()), "Error while removing event");
        }
    }

    private Images addImagesByUrl(String url) {
        Images images = objectContext.newObject(Images.class);
        images.setUrl(url);

        objectContext.commitChanges();

        return images;
    }
}
