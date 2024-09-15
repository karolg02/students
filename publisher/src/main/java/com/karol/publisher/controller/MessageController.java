package com.karol.publisher.controller;

import com.karol.publisher.model.Notification;
import com.karol.publisher.service.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final NotificationService notificationService;

    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/notifications")
    public String sendStudentNotification(@RequestParam Long studentId){
        notificationService.sendStudentNotification(studentId);
        return "Wiadomosc zostala wyslana do studenta o id: " + studentId;
    }
}
