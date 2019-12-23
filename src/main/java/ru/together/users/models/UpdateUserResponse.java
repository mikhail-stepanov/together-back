package ru.together.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    Integer id;

    Integer userId;

    String name;

    String email;

    String phone;

    boolean isVerified;

}