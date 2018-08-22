package com.chequer.tendermint.controller;

import com.chequer.tendermint.support.api.ApiException;
import com.chequer.tendermint.support.api.ApiStatus;
import com.chequer.tendermint.support.api.response.ApiResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@ControllerAdvice
public class BaseController {

    @Inject
    protected LocalValidatorFactoryBean validator;

    protected static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    protected static final String TEXT_PLAIN_UTF_8 = "text/plain; charset=UTF-8";

    protected static final String TEXT_HTML_UTF_8 = "text/html; charset=UTF-8";

    protected static final String TEXT_HTML = "text/html;";

    protected static final String IMAGE_SVG = "image/svg+xml;";

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public ApiResponse ok() {
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse ok(String message) {
        return ApiResponse.of(ApiStatus.SUCCESS, message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse handleForbidden(Exception e) {
        return ApiResponse.error(ApiStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ApiResponse handleBadRequestException(Exception e) {
        errorLogging(e);
        return ApiResponse.error(ApiStatus.BAD_REQUEST, "BAD_REQUEST");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ApiResponse handleRequestParameterException(MissingServletRequestParameterException e) {
        return ApiResponse.error(ApiStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {ApiException.class})
    @ResponseBody
    public ApiResponse handleApiException(ApiException apiException) {
        return ApiResponse.error(ApiStatus.getApiStatus(apiException.getCode()), apiException.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse HttpRequestMethodNotSupportedException(Throwable throwable) {
        return ok("Not Supported Method");
    }

    @ExceptionHandler(Throwable.class)
    public ApiResponse handleException(Throwable throwable) {
        errorLogging(throwable);

        ApiResponse apiResponse = ApiResponse.error(ApiStatus.SYSTEM_ERROR, throwable.getMessage());

        Throwable rootCause = ExceptionUtils.getRootCause(throwable);

        if (rootCause != null) {
            if (rootCause instanceof SQLException) {
                String message = String.format("데이터 처리중 에러가 발생하였습니다.\n시스템 관리자에게 문의하세요.\n\n에러내용 : %s", rootCause.getLocalizedMessage());
                apiResponse = ApiResponse.error(ApiStatus.SYSTEM_ERROR, message);
            }
        }
        return apiResponse;
    }

    protected void errorLogging(Throwable throwable) {
        if (logger.isErrorEnabled()) {

            String message = throwable.getMessage();

            Throwable rootCause = ExceptionUtils.getRootCause(throwable);

            if (rootCause != null) {
                throwable = rootCause;

                if (throwable.getMessage() != null) {
                    message = throwable.getLocalizedMessage();
                }
            }

            if (throwable.getMessage() != null) {
                logger.error(message, throwable);
            } else {
                if (throwable instanceof NullPointerException) {
                    message = "java.lang.NullPointException";
                }
                if (message == null) {
                    message = "ERROR";
                }
                logger.error(message, throwable);
            }
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        ApiResponse error = ApiResponse.error(ApiStatus.SYSTEM_ERROR, fieldError.getDefaultMessage());
        error.getError().setRequiredKey(fieldError.getField());
        return error;
    }
}
