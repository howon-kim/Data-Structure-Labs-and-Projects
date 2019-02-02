public class Body {
    // Constant Variable
    private static final double gForce = 6.67 * Math.pow(10, -11);

    // Variables
    public double xxPos, yyPos;
    public double xxVel, yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double xxDiff = xxPos - b.xxPos;
        double yyDiff = yyPos - b.yyPos;
        return Math.sqrt((xxDiff * xxDiff) + (yyDiff * yyDiff));
    }

    public double calcForceExertedBy(Body b) {
        double dist = Math.pow(this.calcDistance(b), 2);
        return (gForce * mass * b.mass) / dist;
    }

    public double calcForceExertedByX(Body b) {
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);
        double dx = b.xxPos - xxPos;
        return (force * dx) / dist;
    }

    public double calcForceExertedByY(Body b) {
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);
        double dy = b.yyPos - yyPos;
        return (force * dy) / dist;
    }

    public double calcNetForceExertedByX(Body[] b) {
        double netForce = 0;
        for (Body i : b) {
            if (!this.equals(i))
                netForce += this.calcForceExertedByX(i);
        }
        return netForce;
    }

    public double calcNetForceExertedByY(Body[] b) {
        double netForce = 0;
        for (Body i : b) {
            if (!this.equals(i))
                netForce += this.calcForceExertedByY(i);
        }
        return netForce;
    }

    public void update(double dt, double fX, double fY){
        double xxAcc = fX / this.mass;
        double yyAcc = fY / this.mass;

        this.xxVel = this.xxVel + dt * xxAcc;
        this.yyVel = this.yyVel + dt * yyAcc;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw(){
        StdDraw.picture(xxPos, yyPos, "/images/" + imgFileName);
    }
}
