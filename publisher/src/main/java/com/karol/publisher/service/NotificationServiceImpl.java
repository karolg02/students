package com.karol.publisher.service;

import com.karol.publisher.model.Notification;
import com.karol.publisher.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public NotificationServiceImpl(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentNotification(Long studentId) {
        Student student = restTemplate.exchange("http://localhost:8080/students/" + studentId, HttpMethod.GET, HttpEntity.EMPTY, Student.class).getBody();

        Notification notification = getNotification(student);

        rabbitTemplate.convertAndSend("kurs", notification);
    }

    private static Notification getNotification(Student student) {
        Notification notification = new Notification();
        notification.setEmail(student.getEmail());
        notification.setTitle("Witaj! " + student.getFirstName());
        notification.setBody("Milo, ze jestes z nami " + student.getLastName());
        return notification;
    }
}
