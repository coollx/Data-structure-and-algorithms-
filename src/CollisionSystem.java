public class CollisionSystem {
    //private int collisionCount;
    private Particle[] system;
    private MinPQ<Event> pq;
    private double systemTime;

    public CollisionSystem(Particle[] s) {
        system = s;
        pq = new MinPQ<Event>(s.length);
        double t;
        systemTime = 0.0;

        for (int i = 0; i < system.length; i++) {
            t = system[i].collidesY();
            //System.out.println(t);
            if (t != -1) {
                pq.insert(new HoriCollision(t, system[i]));
                //pq.insert(new ReDraw(t));
            }

            t = system[i].collidesX();
            //System.out.println(t);
            if (t != -1) {
                pq.insert(new VerCollison(t, system[i]));
                //pq.insert(new ReDraw(t));
            }

            for (int j = i+1; j < system.length; j++) {
                t = system[i].collides(system[j]);
                //System.out.println(t);
                if (t != -1) {
                    //System.out.println("这里不应该出现");
                    pq.insert(new Collision(t, system[i], system[j]));
                    //pq.insert(new ReDraw(t));
                }
            }

            //System.out.println(pq.size());
        }
    }

    public void begin() {
        System.out.println(pq.isEmpty());
        while(true) {
            Event e = pq.delMin();
            //StdDraw.clear(StdDraw.LIGHT_GRAY);
            if (!e.wasSuperveningEvent()) {

//                for (Particle p : system) {
//                    StdDraw.clear(StdDraw.LIGHT_GRAY);
//                    p.move(e.getTime() - systemTime);
//                }
                double a = e.getTime();
                System.out.println(e.getTime());

                for (double inct = e.getTime() - systemTime; inct > 0; inct = inct - 4) {
                    System.out.println(e.getTime() + "event time");
                    System.out.println(inct + "     incttt");
                    System.out.println(systemTime + "   system");
                    StdDraw.clear(StdDraw.LIGHT_GRAY);
                    for (Particle p : system) {
                        p.move(4);
                    }
//                    for (Particle p : system) {
//                        p.reDraw();
//                    }
                }

                System.out.println(a);
                systemTime = a;

                if (e instanceof HoriCollision) {
                    e.getParticle1().bounceY();
                    insert(e.getParticle1(), systemTime);
                    System.out.println("水平");
                } else if (e instanceof VerCollison) {
                    e.getParticle1().bounceX();
                    insert(e.getParticle1(),  systemTime);
                    System.out.println("vertical");
                } else if (e instanceof Collision) {
                    e.getParticle1().bounce(e.getParticle2());
                    insert(e.getParticle1(), systemTime);
                    insert(e.getParticle2(), systemTime);
                    System.out.println("出现");
                }

            }
        }
    }

//    private void insert(Particle a, Particle b, double time) {
//        if (a != null && a.collidesY() != -1.0)
//            pq.insert(new HoriCollision(time + a.collidesY(), a));
//
//        if (a != null && a.collidesX() != -1.0)
//            pq.insert(new VerCollison(time + a.collidesX(), a));
//
//        if (b != null && b.collidesY() != -1.0)
//            pq.insert(new HoriCollision(time + b.collidesY(), b));
//
//        if (b != null && b.collidesX() != -1.0)
//            pq.insert(new VerCollison(time + b.collidesX(), b));
//
//        for (Particle p : system) {
//            if (a != null && a.collides(p) != -1.0)
//                pq.insert(new Collision(time + a.collides(p), a, p));
//            if (b != null && b.collides(p) != -1.0)
//                pq.insert(new Collision(time + b.collides(p), b, p));
//        }
//    }

    private void insert(Particle a, double time) {
        if (a != null && a.collidesY() != -1.0)
            pq.insert(new HoriCollision(time + a.collidesY(), a));

        if (a != null && a.collidesX() != -1.0)
            pq.insert(new VerCollison(time + a.collidesX(), a));

        for (Particle p : system) {
            if (a.collides(p) != -1.0)
                pq.insert(new Collision(time + a.collides(p), a, p));
        }
    }

}
