package com.example.myfurniture.dto.request;

import com.example.myfurniture.entity.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateReq {
    String fullName;
    String email;
    String phoneNumber;
    LocalDate dateOfBirth;
}
