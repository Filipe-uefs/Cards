package com.cards.dto.error;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseDTO {

    private String message;

    private List<String> errors;

}
