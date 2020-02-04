package ru.together.events.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventResponse {

    int id;

    String title;

    String place;

    String date;

    String ticketcloud;

    String picBigUrl;

    String picSmallUrl;

    String video;

    String description;

    boolean isFuture;

    String youtube;

    String soundcloud;

    String cloud;
}
