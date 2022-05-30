package br.com.product.api.productapi.configuration.exception;

import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}
