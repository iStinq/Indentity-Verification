import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserService from "../../Services/UserService";
import "./register.css";
import KeycloakService from "../../Services/KeycloakService";

export const Register = () => {
    const [newEmail, setNewEmail] = useState("");
    const [newUsername, setNewUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [newConfirmPassword, setNewConfirmPassword] = useState("");
    const [newPhoneNumber, setNewPhoneNumber] = useState("");
    const [otpNumber, setOtpNumber] = useState("");
    const [isOTPValid, setIsOTPValid] = useState(false);

    const navigate = useNavigate();

    const Login = () => {
        navigate('/')
    }

    const onRegister = async (e) => {
        e.preventDefault();
        if (isOTPValid) {
            if (newPassword == newConfirmPassword) {
                let user = {
                    email : newEmail,
                    username : newUsername,
                    password : newPassword,
                    phoneNumber : newPhoneNumber
                }

                const responseToken = await KeycloakService.getAdminToken();
                const adminToken = responseToken.data.access_token;
                
                const responseUser = await UserService.saveUser(user, adminToken);

                if (responseUser.data) {
                    navigate('/login');
                    alert("Please Verify your email to login!");
                } else {
                    alert("This email already exists!");
                }
                
            } else {
                alert("Confirm password should match the password!");
            }
        } else {
            alert("Please verify your phone first.");
        }
        
    }

    const handleOtp = async (e) => {
        e.preventDefault();
        const isValid = await UserService.isValidOTP(otpNumber);
        if (isValid.data) {
            alert("OTP code is valid!");
            setIsOTPValid(true);
        } else {
            console.log(UserService.isValidOTP(otpNumber));
            alert("OTP code is invalid!");
        }
    }

    return (
        <>
            <div className="register-container">
                <img src="src\Images\logo.png" alt="logo" />
                <div className="register-wlcm">Register to our service here</div>
                <div className="register-top-right-container">
                    <p>Already have an account?</p>
                    <button onClick={Login}>Login</button>
                </div>
                <div className="register-form">
                    <div className="register-form-header">
                        <p>Create you account here</p>
                        <p>Please enter your details below to create a new account.</p>
                    </div>
                    <div className="register-form-inputs">
                        <form>
                            <div className="register-input-container">
                                <div className="register-first-input">
                                    <label htmlFor="email">Email</label><br />
                                    <input type="text" placeholder="Enter your email..." id="email" onChange={(e) => setNewEmail(e.target.value)} required/>
                                </div>

                                <div className="register-second-input">
                                    <label htmlFor="username">Username</label><br />
                                    <input type="text" placeholder="Enter your username..." id="username" onChange={(e) => setNewUsername(e.target.value)}/>
                                </div>

                                <div className="register-third-input">
                                    <label htmlFor="password">Password</label><br />
                                    <input type="password" placeholder="****************" id="password" onChange={(e) => setNewPassword(e.target.value)}/>
                                </div>

                                <div className="register-forth-input">
                                    <label htmlFor="confirm-password">Confirm your password</label><br />
                                    <input type="password" placeholder="****************" id="confirm-password" onChange={(e) => setNewConfirmPassword(e.target.value)}/>
                                </div>

                                <div className="register-fifth-input">
                                    <label htmlFor="pn">Phone number</label><br />
                                    <div className="register-fifth-input-container">
                                        <input type="text" placeholder="Your phone number here..." id="pn" onChange={(e) => setNewPhoneNumber(e.target.value)} required/>
                                        <input type="text" placeholder="OTP number here..." onChange={(e) => setOtpNumber(e.target.value)} required/>
                                        <input type="button" value="Verify code" className="otp-btn" onClick={handleOtp}/>
                                    </div>
                                    
                                </div>
                            </div>
                            
                            <button onClick={onRegister}>Sign Up</button>
                        </form>
                    </div>
                </div>
                
            </div>
        </>
    )

}
