package org.eu.nl.dndmapp.dmaserver.services;

import org.eu.nl.dndmapp.dmaserver.models.entities.User;
import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.EntityNotFoundException;
import org.eu.nl.dndmapp.dmaserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    public List<User> findAllByRole(UserRole role) {
        return (List<User>) this.userRepository.findAllByRolesContaining(role);
    }

    public User findById(UUID id) throws EntityNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(
            "User with ID '%s' is not found.",
            id
        )));
    }

    public User save(User user) throws EntityNotFoundException {
        if (user.getId() == null) {
            return this.userRepository.save(user);
        }
        try {
            User byId = this.findById(user.getId());

            // Check if the provided User data belongs to an existing User.
            if (!user.equals(byId)) throw new DataMismatchException(String.format(
                "Cannot update User info from User with ID: '%s' with data from User with ID: '%s'",
                user.getId(),
                byId.getId()
            ));
            return this.userRepository.save(user);
        }
        catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format(
                "Cannot update User with ID: '%s' because it does not exist.",
                user.getId()
            ));
        }
    }

    public void deleteById(UUID id) throws IllegalArgumentException, EntityNotFoundException {
        try {
            this.userRepository.findById(id);
            this.userRepository.deleteById(id);
        }
        catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException(String.format(
                "Cannot delete User with ID: '%s' because it does not exist in the database.",
                id
            ));
        }
    }
}
