package com.karol.receiver.Controller;

import com.karol.receiver.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;


    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification(){
        Object notification = rabbitTemplate.receiveAndConvert("kurs");
        if(notification instanceof Notification){
            return ResponseEntity.ok((Notification) notification);
        }
        return ResponseEntity.noContent().build();
    }

    @RabbitListener(queues = "kurs")
    public void listenerMessage(Notification  notification){
        System.out.println(notification.getEmail()+" "+ notification.getTitle()+" " + notification.getBody());
    }
}
