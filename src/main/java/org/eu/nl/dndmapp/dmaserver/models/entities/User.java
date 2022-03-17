package org.eu.nl.dndmapp.dmaserver.models.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eu.nl.dndmapp.dmaserver.models.DmaEntity;
import org.eu.nl.dndmapp.dmaserver.models.RequestBodyExtractor;
import org.eu.nl.dndmapp.dmaserver.models.converters.UserRoleConverter;
import org.eu.nl.dndmapp.dmaserver.models.enums.UserRole;
import org.eu.nl.dndmapp.dmaserver.models.exceptions.DataMismatchException;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`user`")
public class User extends DmaEntity {
    @Column(
        name = "`username`",
        columnDefinition = "VARCHAR(32) NOT NULL UNIQUE"
    )
    private String username;

    @Setter(AccessLevel.NONE)
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

    public User(String id) {
        super(id);
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

    public void assignRole(UserRole role) {
        this.roles.add(role);
    }

    public static class Builder {
        private final Set<UserRole> roles;
        private String id;
        private String username;

        public Builder() {
            this.roles = new HashSet<>();
        }

        public Builder withId(String id) {
            this.id = id;

            System.out.printf("Assigned ID '%s' to User object\n", this.id);

            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;

            System.out.printf("Assigned username '%s' to User object\n", this.username);

            return this;
        }

        public Builder addRoles(List<UserRole> roles) {
            this.roles.addAll(roles);

            System.out.printf("Assigned roles '%s' to User object\n", Arrays.toString(this.roles.toArray()));

            return this;
        }

        public User build() {
            User user = new User(id);
            user.setUsername(username);

            this.roles.forEach(user::assignRole);

            return user;
        }
    }
}
