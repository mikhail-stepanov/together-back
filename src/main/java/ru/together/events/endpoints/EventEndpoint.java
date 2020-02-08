package ru.together.events.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.together.events.interfaces.IEventService;
import ru.together.events.models.*;
import ru.together.events.services.EventService;

import java.util.List;

@RestController
public class EventEndpoint implements IEventService {

    @Autowired
    EventService eventService;


    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_ADD, method = {RequestMethod.POST})
    public AddEventResponse add(@RequestBody AddEventRequest request) {
        return eventService.add(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_GET, method = {RequestMethod.POST})
    public GetEventResponse get(@RequestBody GetEventRequest request) {
        return eventService.get(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_ALL, method = {RequestMethod.GET})
    public List<EventModel> listAll() {
        return eventService.listAll();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_FUTURE, method = {RequestMethod.GET})
    public List<EventModel> listFuture() {
        return eventService.listFuture();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_LIST_PAST, method = {RequestMethod.GET})
    public List<EventModel> listPast() {
        return eventService.listPast();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_REMOVE, method = {RequestMethod.POST})
    public RemoveEventResponse remove(@RequestBody RemoveEventRequest request) {
        return eventService.remove(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = EVENT_UPDATE, method = {RequestMethod.POST})
    public UpdateEventResponse update(@RequestBody UpdateEventRequest request) {
        return eventService.update(request);
    }
}
