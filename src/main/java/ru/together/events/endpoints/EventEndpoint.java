package ru.together.events.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.together.common.controllers.AbstractController;
import ru.together.common.exceptions.CommonException;
import ru.together.events.interfaces.IEventService;
import ru.together.events.models.*;
import ru.together.events.services.EventService;

import java.util.List;

@RestController
public class EventEndpoint extends AbstractController implements IEventService {

    @Autowired
    EventService eventService;


    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_ADD, method = {RequestMethod.POST})
    public AddEventResponse add(@RequestBody AddEventRequest request) throws CommonException {
        return eventService.add(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_GET, method = {RequestMethod.POST})
    public GetEventResponse get(@RequestBody GetEventRequest request) throws CommonException {
        return eventService.get(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_ALL, method = {RequestMethod.GET})
    public List<EventModel> listAll() throws CommonException {
        return eventService.listAll();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_FUTURE, method = {RequestMethod.GET})
    public List<EventModel> listFuture() throws CommonException {
        return eventService.listFuture();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_PAST, method = {RequestMethod.GET})
    public List<EventModel> listPast() throws CommonException {
        return eventService.listPast();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_REMOVE, method = {RequestMethod.POST})
    public RemoveEventResponse remove(@RequestBody RemoveEventRequest request) throws CommonException {
        return eventService.remove(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_UPDATE, method = {RequestMethod.POST})
    public UpdateEventResponse update(@RequestBody UpdateEventRequest request) throws CommonException {
        return eventService.update(request);
    }
}
