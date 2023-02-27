package raf.sk.userservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.notification.NotificationDto;
import raf.sk.userservice.event.RegistrationEvent;
import raf.sk.userservice.mapper.NotificationMapper;
import raf.sk.userservice.model.ConformationToken;
import raf.sk.userservice.model.UserEntity;
import raf.sk.userservice.service.ConformationTokenService;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

    private final String NOTIFICATION_TYPE = "CONFIRM_REGISTRATION";

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;
    private ConformationTokenService tokenService;
    private RabbitTemplate rabbitTemplate;
    private NotificationMapper notificationMapper;
    private ObjectMapper objectMapper;

    public RegistrationListener(ConformationTokenService tokenService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, NotificationMapper notificationMapper) {
        this.tokenService = tokenService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        UserEntity registeredUser = event.getUser();
        ConformationToken conformationToken = tokenService.createToken(registeredUser, NOTIFICATION_TYPE);

        NotificationDto notificationInfo = notificationMapper.userToNotificationDto(registeredUser, conformationToken.getToken());
        notificationInfo.setNotificationType(NOTIFICATION_TYPE);

        rabbitTemplate.convertAndSend(exchange, routingKey, serialize(notificationInfo));
        tokenService.saveConformationToken(conformationToken);
    }

    private String serialize(NotificationDto notificationDto){

        try {
            return objectMapper.writeValueAsString(notificationDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
