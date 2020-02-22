package ru.together.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    String firstName;

    String lastName;

    String email;

    String phone;

    String facebook;

    String instagram;

}
