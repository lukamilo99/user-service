package raf.sk.userservice.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto{

    @JsonProperty("notificationType")
    private String notificationType;
    @JsonProperty("receiverEmail")
    private String receiverEmail;
    @JsonProperty("receiverFirstname")
    private String receiverFirstname;
    @JsonProperty("receiverLastname")
    private String receiverLastname;
    @JsonProperty("token")
    private String token;
}
