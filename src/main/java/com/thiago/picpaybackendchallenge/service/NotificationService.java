package com.thiago.picpaybackendchallenge.service;

import com.thiago.picpaybackendchallenge.domain.user.User;
import com.thiago.picpaybackendchallenge.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        boolean notificationSuccess = notificationResponse.getStatusCode() == HttpStatus.OK;
//        if(!notificationSuccess){
//            System.out.println("Error sending notification.");
//            throw new Exception("Notification service is offline.");
//        }

        System.out.println("Notification sent to user " + user.getFirstName());
    }
}
