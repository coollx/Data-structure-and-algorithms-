import java.lang.UnsupportedOperationException;

public class VerCollison extends AbstractEvent {

    public VerCollison(double time, Particle particle) {
        super(time, particle, null);
    }

    @Override
    public Particle getParticle2() {
        throw new UnsupportedOperationException();
    }
}
