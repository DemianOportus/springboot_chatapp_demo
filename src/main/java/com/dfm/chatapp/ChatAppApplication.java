package com.dfm.chatapp;

import com.dfm.chatapp.service.PatientDoctorMatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class ChatAppApplication {

    private final PatientDoctorMatcher patientDoctorMatcher;

    public ChatAppApplication(PatientDoctorMatcher patientDoctorMatcher) {
        this.patientDoctorMatcher = patientDoctorMatcher;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatAppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void matchEmAll() {
        patientDoctorMatcher.matchDoctorsWithPatients();
    }

    // Hashing algorithm
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
