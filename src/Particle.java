public class Particle {
    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double mass;
    private double radius;
    private int count;

    public Particle(double xPosition, double yPosition,
                    double xVelocity, double yVelocity,
                    double mass, double radius) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.mass = mass;
        this.radius = radius;
        count = 0;
    }

    //return the time that the particle will collides with a vertical wall.
    public double collidesX() {
        if      (xVelocity < 0) return (radius - xPosition) / xVelocity;
        else if (xVelocity > 0) return (1 - radius - xPosition) / xVelocity;
        else                    return -1;
    }

    //return the time that the particle will collides with a horizontal wall.
    public double collidesY() {
        if      (yVelocity < 0) return (radius - yPosition) / yVelocity;
        else if (yVelocity > 0) return (1 - radius - yPosition) / yVelocity;
        else                    return -1;
    }

    public double collides(Particle other) {
        if (this == other) return -1;
        double dvdr = (other.xVelocity - xVelocity) * (other.xPosition - xPosition)
                    + (other.yVelocity - yVelocity) * (other.yPosition - yPosition);

        if (dvdr >= 0) return -1;
        double dr2 = (other.xPosition - xPosition) * (other.xPosition - xPosition)
                   + (other.yPosition - yPosition) * (other.yPosition - yPosition);
        double dv2 = (other.xVelocity - xVelocity) * (other.xVelocity - xVelocity)
                   + (other.yVelocity - yVelocity) * (other.yVelocity - yVelocity);
        double sigma = this.radius + other.radius;
        double d = (dvdr * dvdr) - dv2 * (dr2 - sigma * sigma);

        if (d < 0) return -1;

        else return -(dvdr + Math.sqrt(d)) / dv2;
    }

    //encounter a vertical wall
    public void bounceX() {
        xVelocity = -xVelocity;
        count++;
    }

    public void bounceY() {
        yVelocity = -yVelocity;
        count++;
    }

    public void bounce(Particle other) {
        double dvdr = (other.xVelocity - xVelocity) * (other.xPosition - xPosition)
                    + (other.yVelocity - yVelocity) * (other.yPosition - yPosition);
        double sigma = radius + other.radius;
        double j = 2 * mass * other.mass * dvdr / ((mass + other.mass) * sigma);
        double jx = (other.xPosition - xPosition) * j / sigma;
        double jy = (other.yPosition - yPosition) * j / sigma;

        xVelocity = xVelocity + jx / mass;
        yVelocity = yVelocity + jy / mass;
        other.xVelocity = other.xVelocity - jx / other.mass;
        other.yVelocity = other.yVelocity - jy / other.mass;

        count++;
        other.count++;
    }

    public int getCollisionCount() {
        return count;
    }

    public void move(double timeIncrement) {
        xPosition += timeIncrement * xVelocity;
        yPosition += timeIncrement * yVelocity;

    }

    public void reDraw() {
        StdDraw.filledCircle(xPosition, yPosition, radius);
        StdDraw.show();
    }

}
