package org.eu.nl.dndmapp.dmaserver.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eu.nl.dndmapp.dmaserver.models.entities.User;
import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;
import org.eu.nl.dndmapp.dmaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // TODO - Restrict access to admins only.
    @GetMapping
    public List<User> getAll(@RequestParam(name = "role", required = false) String role) {
        return role == null
            ? this.userService.findAll()
            : this.userService.findAllByRole(UserRole.parse(role));
    }

    @GetMapping(path = { "/{id}" })
    public User getById(@PathVariable String id) {
        return this.userService.findById(UUID.fromString(id));
    }

    // TODO - Restrict access to admins only and Users that are trying to update their own info.
    @PutMapping("/{id}")
    public User update(@RequestBody ObjectNode requestBody, @PathVariable String id) {
        User fromRequestBody = User.extractFromJSON(requestBody);

        // Check if the ID of the User in the request URL is the
        // same as the ID of the User provided in the request body.
        if (!fromRequestBody.getId().toString().equals(id)) throw new DataMismatchException(String.format(
            "Cannot update User with ID: '%s' with data from User with ID: '%s'",
            id,
            fromRequestBody.getId()
        ));
        return this.userService.save(fromRequestBody);
    }

    // TODO - Restrict access to admins only and Users who delete their own account.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws DataMismatchException {
        try {
            // TODO - Should actually disable User accounts and not delete them in order to preserve data
            this.userService.deleteById(UUID.fromString(id));
        }
        catch (IllegalArgumentException exception) {
            throw new DataMismatchException(String.format("Invalid User ID. '%s' is not a valid UUID format.", id));
        }
    }
}
