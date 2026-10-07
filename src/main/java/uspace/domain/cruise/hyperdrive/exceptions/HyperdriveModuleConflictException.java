package uspace.domain.cruise.hyperdrive.exceptions;

public class HyperdriveModuleConflictException extends RuntimeException {

    public HyperdriveModuleConflictException() {
        super("Another hyperdrive module is already active at this time");
    }
}
