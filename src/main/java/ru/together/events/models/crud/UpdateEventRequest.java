package ru.together.events.models.crud;

import java.time.LocalDateTime;

public class UpdateEventRequest {

    int id;

    String title;

    String place;

    LocalDateTime date;

    String picUrl;

    String description;

    boolean isFuture;

    String youtube;

    String soundcloud;

    String cloud;
    
}
