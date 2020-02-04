package ru.together.events.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.events.interfaces.IEventService;
import ru.together.events.models.*;
import ru.together.events.services.EventService;

@RestController
public class EventEndpoint implements IEventService {

    @Autowired
    EventService eventService;


    @Override
    @RequestMapping(value = EVENT_ADD, method = {RequestMethod.POST})
    public AddEventResponse add(@RequestBody AddEventRequest request) {
        return eventService.add(request);
    }

    @Override
    @RequestMapping(value = EVENT_GET, method = {RequestMethod.POST})
    public GetEventResponse get(@RequestBody GetEventRequest request) {
        return eventService.get(request);
    }

    @Override
    @RequestMapping(value = EVENT_LIST_ALL, method = {RequestMethod.GET})
    public ListEventResponse listAll() {
        return eventService.listAll();
    }

    @Override
    @RequestMapping(value = EVENT_LIST_FUTURE, method = {RequestMethod.GET})
    public ListEventResponse listFuture() {
        return eventService.listFuture();
    }

    @Override
    @RequestMapping(value = EVENT_LIST_PAST, method = {RequestMethod.GET})
    public ListEventResponse listPast() {
        return eventService.listPast();
    }

    @Override
    @RequestMapping(value = EVENT_REMOVE, method = {RequestMethod.POST})
    public RemoveEventResponse remove(@RequestBody RemoveEventRequest request) {
        return eventService.remove(request);
    }

    @Override
    @RequestMapping(value = EVENT_UPDATE, method = {RequestMethod.POST})
    public UpdateEventResponse update(@RequestBody UpdateEventRequest request) {
        return eventService.update(request);
    }
}
