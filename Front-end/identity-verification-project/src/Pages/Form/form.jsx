import "./form.css"
import { useState } from "react";
import VerificationService from "../../Services/VerificationService";

export const Form = () => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [cin, setCIN] = useState("");
    const [birthPlace, setBirthPlace] = useState("");
    const [birthDate, setBirthDate] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("");
    const [file, setFile] = useState(null);
    const [photo, setPhoto] = useState(null);

    const onSubmit = async (e) => {
        e.preventDefault();

        try {
            await VerificationService.saveVerification(file, photo, firstName, lastName, cin, birthPlace, birthDate)
            alert("Your demand was sent correctly, Wait for the admin to have a look at it to verify your identity.");
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <>
            <div className="verify-container">
                <div className="verify-header">
                    <p>Verify your identity</p>
                </div>

                <form>
                    <label htmlFor="fn">First Name</label>
                    <input type="text" placeholder="Enter your first name here..." id="fn" onChange={(e) => setFirstName(e.target.value)} required/>

                    <label htmlFor="ls">Last Name</label>
                    <input type="text" placeholder="Enter your last name here..." id="ls" onChange={(e) => setLastName(e.target.value)} required/>

                    <label htmlFor="cin">CIN</label>
                    <input type="text" placeholder="Enter your cin here..." id="cin" onChange={(e) => setCIN(e.target.value)} required/>

                    <label htmlFor="bp">Birth Place</label>
                    <input type="text" placeholder="Enter your birth place here..." id="bp" onChange={(e) => setBirthPlace(e.target.value)} required/>

                    <label htmlFor="bd">Birth Date</label>
                    <input type="date" placeholder="Enter your birth date here..." id="bd" onChange={(e) => setBirthDate(e.target.value)} required/>

                    <label htmlFor="cin-file">Upload your CIN</label>
                    <input type="file" id="cin-file" onChange={(e) => setFile(e.target.files[0])}/>

                    <label htmlFor="phone">Phone Number</label>
                    <div className="phone-container">
                        <input type="text" placeholder="Enter your phone number here..." id="phone" onChange={(e) => setPhoneNumber(e.target.value)} required/>
                    </div>
                    
                    <div className="verify-face-container">
                        <label>Upload your photo Verify your faceID</label>
                        <input type="file" id="cam-file" onChange={(e) => setPhoto(e.target.files[0])}/>
                    </div>

                    <div className="last-btns">
                        <button onClick={onSubmit}>Submit</button>
                        <button>Reset</button>
                    </div>
                </form>
            </div>
        </>
    )
}