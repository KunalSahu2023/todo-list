package com.developer.todolist.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoMessage {

    private Long id;
    private String title;
    private boolean completed;
}