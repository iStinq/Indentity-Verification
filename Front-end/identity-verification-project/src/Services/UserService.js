import axios from "axios";

const USER_API_URL = "http://localhost:8090/users";



class UserService {

    saveUser(user, token) {

        return axios.post(USER_API_URL, user, {
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        });
    }

    isRegistered(email) {

        return axios.get("http://localhost:8090/users/email" + '/' + email);
    }

    isCorrectPassword(email, password) {

        return axios.get("http://localhost:8090/users/email" + '/' + email + '/' + password);
    }

    isValidEmail(email) {

        return axios.get("http://localhost:8090/users/account-validation" + '/' + email);
    }

    isValidOTP(otpNumber) {
        
        return axios.get("http://localhost:8090/users/otp-validation?otpNumber=" + otpNumber);
    }
}

export default new UserService();