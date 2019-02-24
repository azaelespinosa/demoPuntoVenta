package exercise.common.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class CustomErrorMessage implements Serializable {
    private Date timestamp;
    private Integer status;
    private String error;
    private String exception;
    private String message;
    private String path;
}
