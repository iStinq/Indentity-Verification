/*
 * 
 */
package com.example.Springbootproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Springbootproject.Security.KeycloakUserService;
import com.example.Springbootproject.Service.EmailService;
import com.example.Springbootproject.model.ConfirmationToken;
import com.example.Springbootproject.model.Register;
import com.example.Springbootproject.repository.ConfirmationTokenRepository;
import com.example.Springbootproject.repository.RegisterRepository;
import org.springframework.security.core.Authentication;




@RestController
@RequestMapping
public class RegisterController {

	@Autowired
	private RegisterRepository registerRepo;
	
	@Autowired
	EmailService emailService = new EmailService();
	
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepo;
	
	@Autowired
    private KeycloakUserService keycloakUserService;
	
	//Get all users
	@GetMapping("/users")
	public List<Register> getAllUsers() {
		return registerRepo.findAll();
	}
	
	//Save a new user
	@PostMapping("/users")
	public boolean saveUser(@RequestBody Register r) {
		
		Register existingUser = registerRepo.findByEmail(r.getEmail());
		
		if(existingUser != null) {
			 return false;
		}
		else {
			registerRepo.save(r);
			
			try {
                keycloakUserService.createUser(r);
            } catch (Exception e) {
            	System.out.println("Error");
                e.printStackTrace();
                return false;
            }
			
			ConfirmationToken confirmationToken = new ConfirmationToken(r);
            confirmationTokenRepo.save(confirmationToken);
            
			String email = r.getEmail();
			String subject = "Email Verification";
			String body = "Hi Mr." + r.getUsername() + " Please Click this link in order to verify your email: "+"http://localhost:8090/confirm-account?token="+confirmationToken.getConfirmationToken();
			emailService.sendEmail(email, subject, body);
			
			return true;
		}
	}
	
	@GetMapping("/confirm-account")
	public String confirmUserAccount(@RequestParam("token") String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
        	Register user = registerRepo.findByEmail(token.getUserEntity().getEmail());
            user.setValid(true);
            registerRepo.save(user);
            return "Your account has been verified, you can now log in to your account.";
        }
        else
        {
            return "There was an unexpected error!";
        }
    }
	@GetMapping("/users/{id}")
	public Register getUserById(@PathVariable Long id) {
		
		Register user = registerRepo.findById(id).orElse(null);
		
		return user;
	}
	
	@GetMapping("/users/email/{email}")
	public Boolean isRegistered(@PathVariable String email) {
		
		Register user = registerRepo.findByEmail(email);
		
		return user != null;
	}
	
	@GetMapping("/users/email/{email}/{password}")
	public Boolean isCorrectPassword(@PathVariable String email, @PathVariable String password) {
		
		Register user = registerRepo.findByEmail(email);
	    return user != null && user.getPassword().equals(password);
	}
	
	@GetMapping("/users/account-validation/{email}")
	public boolean isValidEmail(@PathVariable String email) {
		
		Register user = registerRepo.findByEmail(email);
		
		return user.isValid();
	}
	
	@GetMapping("/users/otp-validation")
	public boolean isValidOTP(@RequestParam String otpNumber) {
		
		if(otpNumber.equals("000000")){
			return true;
		}
	
		return false;
	}
	
	@GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
}
