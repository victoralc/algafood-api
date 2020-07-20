package com.victor.learn.algafoodapi.api.exceptionhandler;

import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType apiErrorType = ErrorType.BUSINESS_ERROR;
        String detail = e.getMessage();

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).build();
        
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType apiErrorType = ErrorType.ENTITY_NOT_FOUND;
        String detail = e.getMessage();
        
        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ErrorType errorType, String detail) {
        return ApiError.builder().status(status.value())
                    .type(errorType.getUri())
                    .title(errorType.getTitle())
                    .detail(detail);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorType apiErrorType = ErrorType.ENTITY_IN_USE;
        String detail = e.getMessage();

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).build();
        
        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder().title(status.getReasonPhrase()).status(status.value()).build();
        } else if (body instanceof String) {
            body = ApiError.builder().title((String) body).status(status.value()).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
