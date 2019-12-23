package ru.together.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    int id;

    int userId;

    String name;

    String email;

    String phone;

    boolean isVerified;

}