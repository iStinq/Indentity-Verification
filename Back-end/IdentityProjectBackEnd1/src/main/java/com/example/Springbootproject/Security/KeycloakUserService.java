package com.example.Springbootproject.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.Springbootproject.model.Register;

@Component
public class KeycloakUserService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.admin.username}")
    private String keycloakAdminUsername;

    @Value("${keycloak.admin.password}")
    private String keycloakAdminPassword;

    @Value("${keycloak.admin.client-id}")
    private String keycloakAdminClientId;
    
    @Value("${keycloak.admin.grant-type}")
	private String grantType;

    public void createUser(Register user) throws Exception {
        Keycloak keycloak = Keycloak.getInstance(
            keycloakServerUrl,
            keycloakRealm,
            keycloakAdminUsername,
            keycloakAdminPassword,
            keycloakAdminClientId
        );

        UsersResource usersResource = keycloak.realm(keycloakRealm).users();

        // Create UserRepresentation object
        UserRepresentation keycloakUser = new UserRepresentation();
        // Create Cridentials List and append our Password as a credentialRepresentation
        List<CredentialRepresentation> creds = new ArrayList<>();
		CredentialRepresentation cred = new CredentialRepresentation();
		cred.setTemporary(false);
		cred.setValue(user.getPassword());
		creds.add(cred);
		keycloakUser.setCredentials(creds);
		
		Map<String, List<String>> clientRoles = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        clientRoles.put("${keycloak.admin.client-id}", roles);
		
        keycloakUser.setUsername(user.getUsername());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setEnabled(true);
        keycloakUser.setCredentials(creds);
        keycloakUser.setClientRoles(clientRoles);

        // Create the user in Keycloak
        usersResource.create(keycloakUser);
    }
}
