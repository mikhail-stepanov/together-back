package ru.together.events.interfaces;

import ru.together.common.exceptions.CommonException;
import ru.together.events.models.*;

import java.util.List;

public interface IEventService {

    String EVENT_ADD = "/v1/event/add";
    String EVENT_GET = "/v1/event/get";
    String EVENT_LIST_ALL = "/v1/event/list/all";
    String EVENT_LIST_FUTURE = "/v1/event/list/future";
    String EVENT_LIST_PAST = "/v1/event/list/past";
    String EVENT_LIST_FUTURE_USER = "/v1/event/list/future/user";
    String EVENT_LIST_PAST_USER = "/v1/event/list/past/user";
    String EVENT_UPDATE = "/v1/event/update";
    String EVENT_REMOVE = "/v1/event/remove";


    AddEventResponse add(AddEventRequest request) throws CommonException;

    GetEventResponse get(GetEventRequest request) throws CommonException;

    List<EventModel> listAll() throws CommonException;

    List<EventModel> listFuture() throws CommonException;

    List<EventModel> listPast() throws CommonException;

    List<EventModel> listFutureUser(EventUserList request) throws CommonException;

    List<EventModel> listPastUser(EventUserList request) throws CommonException;

    UpdateEventResponse update(UpdateEventRequest request) throws CommonException;

    RemoveEventResponse remove(RemoveEventRequest request) throws CommonException;

}
