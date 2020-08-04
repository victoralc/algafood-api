package com.victor.learn.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.victor.learn.algafoodapi.domain.exception.BusinessException;
import com.victor.learn.algafoodapi.domain.exception.EntityInUseException;
import com.victor.learn.algafoodapi.domain.exception.EntityNotFoundException;
import com.victor.learn.algafoodapi.domain.exception.ValidationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String SYSTEM_ERROR_MESSAGE = "An unexpected error occurred. Try again and if the problem persists contact the administrator";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBiding((PropertyBindingException) rootCause, headers, status, request);
        }

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = "The request body contains something invalid. Please check possible error.";

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.BUSINESS_ERROR;
        String detail = e.getMessage();

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = e.getMessage();

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail) {
        return ApiError.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUse(EntityInUseException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.ENTITY_IN_USE;
        String detail = e.getMessage();

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorType apiErrorType = ApiErrorType.SYSTEM_ERROR;
        String detail = SYSTEM_ERROR_MESSAGE;

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = "Th resource '%s' doesn't exists. Please check if the resource is correct.";

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .userMessage(SYSTEM_ERROR_MESSAGE)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title((String) body)
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .userMessage(SYSTEM_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBiding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ApiErrorType errorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' doesn't exists. Please fix or remove this property.", path);
        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(SYSTEM_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ApiErrorType errorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' has the value '%s' but it is an invalid type. " +
                "Please fix it with a compatible type '%s'", path, ex.getValue(), ex.getTargetType().getSimpleName());
        ApiError error = createApiErrorBuilder(status, errorType, detail)
                .userMessage(SYSTEM_ERROR_MESSAGE)
                .build();

        return super.handleExceptionInternal(ex, error, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.INVALID_PARAMETER;

        String detail = String.format("The url parameter '%s' that has value '%s', "
                        + "it is invalid. Please fix and use a valid type %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidacaoException(ValidationException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.INVALID_INPUT;
        String detail = "Invalid input. Please check the input objects";
        
        List<ApiError.Object> errors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return ApiError.Object.builder().name(name).userMessage(message).build();
                })
                .collect(Collectors.toList());

        ApiError error = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).objects(errors).build();
        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}
