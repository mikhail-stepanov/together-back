package ru.together.events.models.images;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddImagesResponse {

    Integer id;

    String url;

    String name;

    String content;
}
