public class MDSimulation {
    public static void main(String[] args) {
        StdDraw.setPenColor(StdDraw.BLACK);
        // set the scale of the coordinate system
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);

        StdDraw.enableDoubleBuffering();
        Particle[] particles = new Particle[2];
        particles[0] = new Particle(0.5, 0.5, 0.09, 0.05, 2, 0.02);
        particles[1] = new Particle(0.2, 0.1, 0.07, 0.1, 6, 0.02);
        CollisionSystem c = new CollisionSystem(particles);
        c.begin();
    }
}
