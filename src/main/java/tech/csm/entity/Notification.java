package tech.csm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Notification implements Serializable {

	private Long notificationId;
    private String notificationType;
    private String title;
    private String message;
    private String priority;
    private String recipientType;
    private String recipientId;
    private String relatedEntityType;
    private Long relatedEntityId;
    private String status;
    private String emailSent;
    private String inAppSent;
    private LocalDateTime createdDate;
    private LocalDateTime readDate;
    private String readBy;
	
}
