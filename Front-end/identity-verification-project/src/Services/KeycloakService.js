import axios from "axios";

const KEYCLOAK_URL = "http://localhost:8095/realms/verification-realm/protocol/openid-connect/token";

class KeycloakService {

    getAdminToken() {

        const formData = new URLSearchParams();
        formData.append('grant_type', 'password');
        formData.append('client_id', 'verification-client');
        formData.append('username', "admin");
        formData.append('password', "admin");

        return axios.post(KEYCLOAK_URL, formData, {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
            }
        });
    }


}

export default new KeycloakService();