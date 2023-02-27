package raf.sk.userservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.notification.NotificationDto;
import raf.sk.userservice.model.UserEntity;

@Component
public class NotificationMapper {

    public NotificationDto userToNotificationDto(UserEntity registeredUser, String token){
        NotificationDto notificationInfo = new NotificationDto();

        notificationInfo.setToken(token);
        notificationInfo.setReceiverEmail(registeredUser.getEmail());
        notificationInfo.setReceiverFirstname(registeredUser.getName());
        notificationInfo.setReceiverLastname(registeredUser.getLastname());

        return notificationInfo;
    }
}
