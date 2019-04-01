package springdata.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springdata.DTO.ErrorMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(ControllerExceptionHandler.class);
    private static final Logger LOGMAIL = LogManager.getLogger("error-logger");

    private Map<String, String> validationCodeDescription = new HashMap<>();

    @ExceptionHandler(value = {DeleteException.class, UpdateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody ErrorMessage entityExistingProblem(Exception e){
        LOG.warn("Unprocesable springdata.entity {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorMessage internalServerError(Exception e) {
        LOG.warn("Internal Server Error {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage validationProblem(MethodArgumentNotValidException e) {
        LOG.warn("Request validation problem {}", e.getMessage());
        FieldError fe = e.getBindingResult().getFieldError();
        return new ErrorMessage(validationCodeDescription.get(fe.getDefaultMessage()));
    }

    @PostConstruct
    private void intValidationCodeDescription() {
        validationCodeDescription.put("1", "custNum should not be empty");
        validationCodeDescription.put("2", "custNum out of range. custNum should be more then '1'");
        validationCodeDescription.put("3", "custRep should not be empty");
        validationCodeDescription.put("4", "custRep out of range. custRep should be more then '100'");
        validationCodeDescription.put("5","creditLimit out of range. creditLimit should be more then '100'");
        validationCodeDescription.put("6","creditLimit out of range. creditLimit should be less then '999999'");
        validationCodeDescription.put("7","customerDetails should not be empty");
        validationCodeDescription.put("8","company should not be empty");
        validationCodeDescription.put("9","company should consist of 15 letters[a-zA-Z]");
    }
}
