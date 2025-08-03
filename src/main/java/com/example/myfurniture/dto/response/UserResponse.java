package com.example.myfurniture.dto.response;

import com.example.myfurniture.entity.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String fullName;
    String email;
    String avatarUrl;
    String phoneNumber;
    LocalDate dateOfBirth;
    Set<Address> addresses;
    String accountRole;
}
