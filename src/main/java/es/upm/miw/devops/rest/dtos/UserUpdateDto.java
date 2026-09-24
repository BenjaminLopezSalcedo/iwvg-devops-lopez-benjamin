package es.upm.miw.devops.rest.dtos;

import es.upm.miw.devops.rest.models.Role;

public record UserUpdateDto(
        String firstName,
        String familyName,
        String email,
        String identity,
        String address,
        String city,
        String province,
        String postalCode,
        boolean active,
        Role role
) {
}