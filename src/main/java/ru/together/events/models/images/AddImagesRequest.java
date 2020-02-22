package ru.together.events.models.images;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddImagesRequest {

    String url;

    String name;

    byte[] content;
}
