package ru.together.events.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEventResponse {

    int id;

    String title;

    String place;

    String date;

    String ticketcloud;

    Integer picBigId;

    Integer picSmallId;

    Integer video;

    String description;

    boolean isFuture;

    String youtube;

    String soundcloud;

    String cloud;
}
