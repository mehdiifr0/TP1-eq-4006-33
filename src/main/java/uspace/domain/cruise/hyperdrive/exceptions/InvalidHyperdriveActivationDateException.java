package uspace.domain.cruise.hyperdrive.exceptions;

public class InvalidHyperdriveActivationDateException extends RuntimeException {

    public InvalidHyperdriveActivationDateException() {
        super("Activation date or deactivation date is outside the cruise timeframe");
    }
}
