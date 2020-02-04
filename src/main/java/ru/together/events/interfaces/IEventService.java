package ru.together.events.interfaces;

import ru.together.events.models.*;

public interface IEventService {

    String EVENT_ADD = "/v1/event/add";
    String EVENT_GET = "/v1/event/get";
    String EVENT_LIST_ALL = "/v1/event/list/all";
    String EVENT_LIST_FUTURE = "/v1/event/list/future";
    String EVENT_LIST_PAST = "/v1/event/list/past";
    String EVENT_UPDATE = "/v1/event/update";
    String EVENT_REMOVE = "/v1/event/remove";


    AddEventResponse add(AddEventRequest request);

    GetEventResponse get(GetEventRequest request);

    ListEventResponse listAll();

    ListEventResponse listFuture();

    ListEventResponse listPast();

    UpdateEventResponse update(UpdateEventRequest request);

    RemoveEventResponse remove(RemoveEventRequest request);

}
