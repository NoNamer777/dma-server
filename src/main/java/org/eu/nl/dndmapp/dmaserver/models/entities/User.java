package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;
import org.eu.nl.dndmapp.dmaserver.models.converters.UserRoleConverter;
import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;
import org.eu.nl.dndmapp.dmaserver.utils.RequestBodyExtractor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "`user`")
public class User extends DmaEntity {
    @Column(
        name = "`username`",
        columnDefinition = "VARCHAR(32) NOT NULL UNIQUE"
    )
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = UserRoleConverter.class)
    @Column(
        name = "`role`",
        columnDefinition = "VARCHAR(24) NOT NULL"
    )
    @JoinTable(
        name = "`user_role`",
        joinColumns = @JoinColumn(
            name = "`user_id`",
            columnDefinition = "VARCHAR(64) NOT NULL"
    ))
    private final Set<UserRole> roles = new HashSet<>();

    /* CONSTRUCTORS */

    public User() {}

    public User(String id, String username) {
        super(id);

        this.username = username;
    }

    public static User extractFromJSON(ObjectNode jsonObject) throws DataMismatchException {
        ArrayNode rolesArray = RequestBodyExtractor.getList(jsonObject, "roles");
        List<UserRole> roles = new ArrayList<>();

        if (rolesArray == null) return new User.Builder()
            .withId(RequestBodyExtractor.getText(jsonObject, "id"))
            .withUsername(RequestBodyExtractor.getText(jsonObject, "username"))
            .build();

        for (JsonNode roleJson : rolesArray) {
            String role = roleJson.isTextual() && !roleJson.isNull()
                ? roleJson.asText()
                : null;

            roles.add(UserRole.parse(role));
        }

        return new User.Builder()
            .withId(RequestBodyExtractor.getText(jsonObject, "id"))
            .withUsername(RequestBodyExtractor.getText(jsonObject, "username"))
            .addRoles(roles)
            .build();
    }

    /* GETTERS AND SETTERS */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void assignRole(UserRole role) {
        this.roles.add(role);
    }

    public void revokeRole(UserRole role) {
        this.roles.remove(role);
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object) || !(object instanceof User)) return false;
        if (this == object) return true;

        User other = (User) object;

        return this.username.equals(other.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getUsername());
    }

    @Override
    public String toString() {
        return String.format("User{ id: '%s', username: '%s' }", this.getId(), this.getUsername());
    }

    /* BUILDERS */

    public static class Builder {
        private String id;
        private String username;
        private final Set<UserRole> roles;

        public Builder() {
            this.roles = new HashSet<>();
        }

        public Builder withId(String id) {
            this.id = id;

            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;

            return this;
        }

        public Builder addRoles(List<UserRole> roles) {
            this.roles.addAll(roles);

            System.out.printf("Assigned roles '%s' to User object\n", Arrays.toString(this.roles.toArray()));

            return this;
        }

        public User build() {
            User user = new User(id, username);

            this.roles.forEach(user::assignRole);

            return user;
        }
    }
}
