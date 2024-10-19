import { useState } from "react";
import "./login.css"
import { useNavigate } from "react-router-dom";
import UserService from "../../Services/UserService";
export const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isChecked, setIsChecked] = useState(false);
    sessionStorage.setItem("isAuth", "false");

    const navigate = useNavigate();

    const Register = () => {
        navigate('/register')
    }

    const signInUser = async (e) => {
        e.preventDefault();
        const isRegistered = await UserService.isRegistered(email);
        const isValidEmail = await UserService.isValidEmail(email);
        if (isRegistered.data && isValidEmail.data) {
            const isCorrect = await UserService.isCorrectPassword(email, password)
            if (isCorrect.data) {
                {isChecked && sessionStorage.setItem("isAuth", "true")}
                navigate('/verify');
            } else {
                alert("Incorrect Password!");
            }
        } else {
            alert("This email is not registered, or not yet activated!");
        }
    }

    return (
        <>
            <div className="container">
                <img src="src\Images\logo.png" alt="logo" />
                <p className="wlcm">Welcome to our service</p>
                <div className="top-right-container">
                    <p>Don't have an account?</p>
                    <button onClick={Register}>Register</button>
                </div>
                <div className="form">
                    <div className="form-header">
                        <p>Welcome Back</p>
                        <p>Welcome back! Please enter your details.</p>
                    </div>
                    <div className="form-inputs">
                        <form>
                            <div className="input-container">
                                <div className="first-input">
                                    <label htmlFor="email">Email</label><br />
                                    <input type="text" placeholder="Enter your email..." id="email" onChange={(e) => setEmail(e.target.value)}/>
                                </div>

                                <div className="second-input">
                                    <label htmlFor="password">Password</label><br />
                                    <input type="password" placeholder="****************" id="password" onChange={(e) => setPassword(e.target.value)}/>
                                </div>
                            </div>
                            
                            <div className="afterInputs-container">
                                <div className="rememberMe-container">
                                    <input type="checkbox" value="isRememberMe" id="rememberMe" checked={isChecked} onChange={(e) => setIsChecked(e.target.checked)} />
                                    <p className="paragraph">Remember me</p>
                                </div>
                                <a href="#"><p>Forgot password?</p></a>
                            </div>
                            
                            <button onClick={signInUser}>Sign In</button>
                        </form>
                    </div>
                </div>
                
            </div>
        </>
    )
}