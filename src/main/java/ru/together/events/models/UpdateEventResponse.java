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
public class UpdateEventResponse {

    int id;

    String title;

    String place;

    LocalDateTime date;

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
