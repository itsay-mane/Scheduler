package scheduler;

import java.util.UUID;

public class JobId {
    private UUID id;
    
    private JobId(UUID id) {
        this.id = id;
    }

    public JobId() {
        this(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof JobId && ((JobId) other).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
