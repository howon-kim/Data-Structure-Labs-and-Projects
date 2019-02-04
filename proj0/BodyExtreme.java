public class BodyExtreme{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel; 
	public double mass;
	public String imgFileName;


	public BodyExtreme(double xP, double yP, double xV, double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV; 
		this.yyVel = yV; 
		this.mass = m; 
		this.imgFileName = img; 
	}

	public BodyExtreme(BodyExtreme p){
		this.xxPos = p.xxPos; 
		this.yyPos = p.yyPos; 
		this.xxVel = p.xxVel; 
		this.yyVel = p.yyVel; 
		this.mass = p.mass; 
		this.imgFileName = p.imgFileName; 
	}

	public double calcDistance(BodyExtreme p){
		double dx = this.xxPos - p.xxPos; 
		double dy = this.yyPos - p.yyPos; 
		double r = Math.sqrt(dx * dx + dy * dy); 
		return r; 
	}

	public double calcForceExertedBy(BodyExtreme p){
		double g = 6.67 * Math.pow(10, -11);
		double r = calcDistance(p);
		double force = g * this.mass * p.mass / (r * r);
		return force; 
	}

	public double calcForceExertedByX(BodyExtreme p){
		double force = calcForceExertedBy(p);
		double dx = p.xxPos - this.xxPos; 
		double r = calcDistance(p);
		double calcForceExertedByX = force * dx / r;
		return calcForceExertedByX; 
	}

	public double calcForceExertedByY(BodyExtreme p){
		double force = calcForceExertedBy(p);
		double dy = p.yyPos - this.yyPos; 
		double r = calcDistance(p);
		double calcForceExertedByY = force * dy / r;
		return calcForceExertedByY; 
	}

	public double calcNetForceExertedByX(BodyExtreme[] allBodys) {
		double force = 0; 
		for (BodyExtreme p : allBodys){
			if (p == this){
				continue;
			}
			else{
				force += calcForceExertedByX(p);
			}
		}
		return force;
	}

	public double calcNetForceExertedByY(BodyExtreme[] allBodys) {
		double force = 0; 
		for (BodyExtreme p: allBodys){
			if (p == this){
				continue;
			}
			else{
				force += calcForceExertedByY(p);
			}
		}
		return force;
	}


	public void update(double dt, double fX, double fY){
		double accelerationX = fX / this.mass;
		double accelerationY = fY / this.mass;

		this.xxVel = this.xxVel + accelerationX * dt;
		this.yyVel = this.yyVel + accelerationY * dt;
		this.xxPos = this.xxPos + this.xxVel * dt;
		this.yyPos  = this.yyPos + this.yyVel * dt;
	}


	public void update_W(){
		this.yyPos += 10e9;

	}


	public void update_A(){  //LEFT 
		this.xxPos -= 10e9;

	}

	public void update_D(){  // RIGHT
		this.xxPos += 10e9;

	}


	public void update_S(){  //DOWN
		this.yyPos -= 10e9;

	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
		

	}





}
