import java.lang.UnsupportedOperationException;

public class ReDraw extends AbstractEvent {
    public ReDraw(double time) {
        super(time, null, null);
    }

//    public ReDraw(Particle[] a) {
//        for (Particle p : a) {
//            p.reDraw();
//        }
//    }

    @Override
    public Particle getParticle1() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Particle getParticle2() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean wasSuperveningEvent() {
        throw new UnsupportedOperationException();
    }
}
