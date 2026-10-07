package uspace.domain.cruise.hyperdrive.module;

import uspace.domain.cruise.hyperdrive.exceptions.InvalidHyperdriveModuleIdFormatException;

public class HyperdriveModuleId {
    private static final String ID_FORMAT = "HY-[1-9][0-9]{0,2}-[A-Z]";

    private final String id;

    public HyperdriveModuleId(String id) {
        if (id == null || !id.matches(ID_FORMAT)) {
            throw new InvalidHyperdriveModuleIdFormatException();
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        HyperdriveModuleId other = (HyperdriveModuleId) obj;
        return this.id.equals(other.id);
    }
}
