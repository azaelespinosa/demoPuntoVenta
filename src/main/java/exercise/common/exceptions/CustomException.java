package exercise.common.exceptions;

public class CustomException extends RuntimeException {

    private CustomErrorMessage customErrorMessage;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable err) {
        super(message, err);
    }

    public CustomException(CustomErrorMessage customErrorMessage) {
        super(customErrorMessage.getMessage());
        this.customErrorMessage = customErrorMessage;
    }

    public CustomErrorMessage getCustomErrorMessage() {
        return customErrorMessage;
    }
}
