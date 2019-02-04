import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import static java.lang.Math.pow;

public class NBodyExtreme {

    public static double xxPos;
    public static double yyPos;
    public static boolean wKey = false;
    public static boolean aKey = false;
    public static boolean sKey = false;
    public static boolean dKey = false;
    

    public static BodyExtreme spaceShip = new BodyExtreme(2.2790e+11, 0, 0, 2.4100e+04,  6.4190e+23,  "spaceship.png");
    public static double xForceOfSpaceShip;
    public static double yForceOfSpaceShip;
    // public static double shipXForces = 0.0;
    // public static double shipYForces = 0.0;

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBodyExtreme.readRadius(filename);
        BodyExtreme[] bodies = NBodyExtreme.readBodies(filename);

        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setScale(-radius , radius);
        StdDraw.enableDoubleBuffering();
        

      
        //for (BodyExtreme p: bodies){
        //    p.draw();
        //}
        //StdDraw.picture(0, 0, "images/starfield.jpg");


        //Add all the other planets to the animation 
        double time = 0; 
        while (time != T) {
            StdDraw.clear();
            //StdDraw.picture(0, 0, "images/starfield.jpg", 2, 2, 0);
            StdDraw.picture(0, 0, "images/space.png");
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];

            for (int i = 0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for (int i = 0; i < bodies.length; i++){
                //double mass = 0;

                bodies[i].update(dt, xForces[i], yForces[i]);
                StdDraw.setPenRadius(0.05);
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledCircle(bodies[i].xxPos, bodies[i].yyPos, radius /50 );
            }

            xForceOfSpaceShip = spaceShip.calcNetForceExertedByX(bodies);  //Calc forces exerted on the spaceship
            yForceOfSpaceShip = spaceShip.calcNetForceExertedByY(bodies);
            spaceShip.update(dt, xForceOfSpaceShip, yForceOfSpaceShip);
            System.out.println(spaceShip.xxPos);
            moveSpaceCraft(dt, bodies);
            spaceShip.draw();


            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
    }
    
    public static void moveSpaceCraft(double dt, BodyExtreme[] bodies) {
        // KeyListener : @source : https://gamedev.stackexchange.com/questions/103212/single-key-press
            //hipXForces = spaceShip.calcNetForceExertedByX(bodies);
            //shipYForces = spaceShip.calcNetForceExertedByY(bodies);
        

            if (StdDraw.isKeyPressed(KeyEvent.VK_W)) {
                if (!wKey) {
                    wKey = true;
                    //yyPos += 0.000001; // UP
                    //spaceShip.draw();
                    spaceShip.update_W();
                    
                    //spaceShip.update(dt, shipXForces / 10e9, shipYForces / 10e9);

                }
            } else {
                wKey = false;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                if (!aKey) {
                    aKey = true;
                    //xxPos -= 0.000001; // LEFT
                    //spaceShip.draw();
                    spaceShip.update_A();
                    //spaceShip.update(dt, shipXForces / 10e9, shipYForces / 10e9);
                }
            } else {
                aKey = false;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                if (!sKey) {
                    sKey = true;
                    //yyPos -= 0.000000001; // DOWN
                    //spaceShip.draw();
                    spaceShip.update_S();
                    //spaceShip.update(dt, shipXForces / 10e9, shipYForces / 10e9);
                }
            } else {
                sKey = false;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
                if (!dKey) {
                    dKey = true;
                    //xxPos += 0.000000001; // RIGHT
                    //spaceShip.draw();
                    spaceShip.update_D();
                    //spaceShip.update(dt, shipXForces / 10e9, shipYForces / 10e9);
                }
            } else {
                dKey = false;
            }
        }
    public static double readRadius(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius; 
    }

    public static BodyExtreme[] readBodies(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();

        BodyExtreme[] allBodys = new BodyExtreme[number];
        int i = 0; 
        while (i != number) {
            double xPos = in.readDouble();  //read the first double 
            double yPos = in.readDouble(); 
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            allBodys[i]  = new BodyExtreme(xPos, yPos, xVel, yVel, mass, img);
            i += 1;

        }
        return allBodys; 
    }

}


