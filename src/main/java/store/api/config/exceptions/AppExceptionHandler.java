package store.api.config.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    private final Logger logger;
    
    public AppExceptionHandler() {
        this.logger = LoggerFactory.getLogger(AppExceptionHandler.class + " [EXCEPTION HANDLER]");
    }


    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ExceptionDto> handleEspinosaException(StoreException exception) {
    	
        return new ResponseEntity<ExceptionDto>(
                new ExceptionDto(exception.getMessage()),
                exception.getStatusCode());
    } 
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGenericException(Exception exception) {
    	logger.error("Erro de sistema", exception);
        return new ResponseEntity<ExceptionDto>(new ExceptionDto(exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
