package org.roddevv.models;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Notification {
    private String recipientUuid;
    private String title;
    private String content;
    private LocalTime createdAt;
}
