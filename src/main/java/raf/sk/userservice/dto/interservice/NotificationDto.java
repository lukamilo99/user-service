package raf.sk.userservice.dto.interservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
