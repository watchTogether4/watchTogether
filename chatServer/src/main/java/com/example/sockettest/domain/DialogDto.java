package com.example.sockettest.domain;

import com.example.sockettest.persist.entity.Dialog;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DialogDto {
    private LocalDateTime date;
    private String sender;
    private String message;

    public DialogDto(Dialog dialog) {
        this.sender = dialog.getSender();
        this.message = dialog.getMessage();
        this.date = dialog.getDt();
    }



}
