package fuzzy.validation;

public class InvalidInputException extends RuntimeException{
    private final String variableName;
    private final double invalidValue;

    public InvalidInputException(String variableName, double invalidValue, String message) {
        super(String.format("Invalid value %f for variable %s: %s", invalidValue, variableName, message));
        this.variableName = variableName;
        this.invalidValue = invalidValue;
    }
    public String getVariableName() {return variableName;}
    public double getInvalidValue() {return invalidValue;}

}
