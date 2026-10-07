package uspace.domain.cruise.hyperdrive.exceptions;

public class InvalidHyperdriveModuleException extends RuntimeException {

    public InvalidHyperdriveModuleException() {
        super("Hyperdrive module invalid");
    }
}
