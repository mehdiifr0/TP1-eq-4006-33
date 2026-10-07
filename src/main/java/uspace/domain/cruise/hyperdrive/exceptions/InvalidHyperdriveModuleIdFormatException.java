package uspace.domain.cruise.hyperdrive.exceptions;

public class InvalidHyperdriveModuleIdFormatException extends RuntimeException {

    public InvalidHyperdriveModuleIdFormatException() {
        super("Invalid hyperdrive module id format");
    }
}
