package com.exam.model.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomResponse {

    private LocalDateTime timeStramp;

    private String message;

    private Object data ;





}
