package org.example.api.models;

public record UserRegistration(
        String first_name,
        String last_name,
        Address address,
        String phone,
        String dob,
        String password,
        String email
) {
}
