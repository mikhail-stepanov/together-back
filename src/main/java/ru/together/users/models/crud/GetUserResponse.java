package ru.together.users.models.crud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    int userId;

    String name;

    String email;

    String phone;

    boolean isVerified;
}
