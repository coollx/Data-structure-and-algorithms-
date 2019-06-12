import java.lang.UnsupportedOperationException;

public class HoriCollision extends AbstractEvent {
    public HoriCollision(double time, Particle particle) {
        super(time, particle, null);
    }
}
