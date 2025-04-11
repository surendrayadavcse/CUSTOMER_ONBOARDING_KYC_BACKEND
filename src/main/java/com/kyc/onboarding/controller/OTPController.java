package com.kyc.onboarding.controller;

import com.kyc.onboarding.model.OTPVerificationRequest;
import com.kyc.onboarding.service.OTPService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class OTPController {

    @Autowired
    private OTPService otpService;

   
    @GetMapping("/getOTP/{email}")
    public ResponseEntity<?> getOtp(@PathVariable String email) {
        otpService.generateAndSendOTP(email);
        return ResponseEntity.ok("OTP sent toss " + email);
    }

    // Endpoint to verify the OTP entered by the user
    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyOtp(@RequestBody OTPVerificationRequest otpVerificationRequest) {
        boolean isVerified = otpService.verifyOTP(otpVerificationRequest.getEmail(), otpVerificationRequest.getOtp());
        if (isVerified) {
            return ResponseEntity.ok(Map.of("message", "OTP verified successfully"));
        } else {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid OTP"));
        }
    }
}
