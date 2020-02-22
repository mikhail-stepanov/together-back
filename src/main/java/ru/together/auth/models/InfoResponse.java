package ru.together.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse {

    int userId;

    String firstName;

    String lastName;

    String facebook;

    String instagram;

    String phone;

    Integer picId;

    boolean success;

    String error;
}
