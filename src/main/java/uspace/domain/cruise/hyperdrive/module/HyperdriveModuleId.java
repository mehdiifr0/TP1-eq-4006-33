package uspace.domain.cruise.hyperdrive.module;

public class HyperdriveModuleId {

    private final String id;

    public HyperdriveModuleId(String id) {
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
