public class NBody {
    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String filename){
        In in = new In(filename);
        int size = in.readInt();
        Body[] bodies = new Body[size];
        in.readDouble(); // Read Radius but not stored

        for (int i = 0 ; i < size ; i++){
            bodies[i] = new Body(
                    in.readDouble(), // xxPos
                    in.readDouble(), // yyPos
                    in.readDouble(), // xxVal
                    in.readDouble(), // yyVal
                    in.readDouble(), // Mass
                    in.readString()  // imgFileName
            );
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body [] bodies = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        double cTime = 0;
        double [] xForces = new double[bodies.length];
        double [] yForces = new double[bodies.length];

        while(cTime <= T){
            StdDraw.clear();
            StdDraw.picture(0,0,"images/starfield.jpg");

            for (int i = 0 ; i < bodies.length ; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0 ; i < bodies.length ; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
                bodies[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            cTime += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
