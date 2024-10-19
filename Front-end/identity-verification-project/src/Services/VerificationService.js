import axios from "axios";

const VERIFICATION_API_URL = "http://localhost:8090/verifications";

class VerificationService {
    saveVerification(file, photo, firstName, lastName, cin, birthPlace, birthDate) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('photo', photo);
        formData.append('firstName', firstName);
        formData.append('lastName', lastName);
        formData.append('cin', cin);
        formData.append('birthPlace', birthPlace);
        formData.append('birthDate', birthDate);

        return axios.post(VERIFICATION_API_URL, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}


export default new VerificationService();