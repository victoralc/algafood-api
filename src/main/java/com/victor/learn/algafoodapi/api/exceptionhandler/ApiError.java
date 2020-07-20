package com.victor.learn.algafoodapi.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {
    private Integer status;
    private String type;
    private String title;
    private String detail;
}
