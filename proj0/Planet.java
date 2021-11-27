public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double r = Math.hypot(dx, dy);
        return r;
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67e-11;
        double r1 = calcDistance(p);
        double F1 = G * this.mass * p.mass / (r1 * r1);
        return F1;
    }

    public double calcForceExertedByX(Planet p) {
        double Fx = calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
        return Fx;
    }

    public double calcForceExertedByY(Planet p) {
        double Fy = calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
        return Fy;
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double FxNet = 0;
        for (Planet c : ps) {
            if (!this.equals(c)) {
                FxNet = FxNet + this.calcForceExertedByX(c);
            }
        }
        return FxNet;
    }

    public double calcNetForceExertedByY(Planet[] ps) {
        double FyNet = 0;
        for (Planet c : ps) {
            if (!this.equals(c)) {
                FyNet = FyNet + this.calcForceExertedByY(c);
            }
        }
        return FyNet;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void Draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }
}