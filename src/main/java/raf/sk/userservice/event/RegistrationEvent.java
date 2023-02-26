package raf.sk.userservice.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import raf.sk.userservice.model.UserEntity;
@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {

    private UserEntity user;

    public RegistrationEvent(UserEntity user) {
        super(user);
        this.user = user;
    }
}
