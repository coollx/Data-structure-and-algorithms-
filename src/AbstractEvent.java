abstract class AbstractEvent implements Event, Comparable<Event> {
    private final double time;
    private Particle a;
    private Particle b;
    private int collisionCount = 0;

    AbstractEvent(double time, Particle a, Particle b) {
        this.time = time;
        this.a = a;
        this.b = b;

        if (a != null) collisionCount += a.getCollisionCount();
        if (b != null) collisionCount += b.getCollisionCount();
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public Particle getParticle1() {
        return a;
    }

    @Override
    public Particle getParticle2() {
        return b;
    }

    @Override
    public int compareTo(Event x) {
        if (getTime() < x.getTime())      return -1;
        else if (getTime() > x.getTime()) return 1;
        else                              return 0;
    }

    @Override
    public boolean wasSuperveningEvent() {
        int cnt = 0;
        if (a != null) cnt += a.getCollisionCount();
        if (b != null) cnt += b.getCollisionCount();
//        System.out.println(cnt);
//        System.out.println(collisionCount);
        return cnt != collisionCount;
    }
}
