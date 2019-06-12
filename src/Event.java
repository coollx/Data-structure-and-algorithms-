public interface Event extends Comparable<Event> {
    double getTime();
    Particle getParticle1();
    Particle getParticle2();
    int compareTo(Event x);
    boolean wasSuperveningEvent();
}
