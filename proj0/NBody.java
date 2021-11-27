public class NBody {

	public static double readRadius(String path) {
		In in = new In(path);
		in.readInt();
		return in.readDouble();
	}
	public static Planet[] readPlanets(String filename){
		In in = new In(filename);
		int num = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[num];
		for (int i=0; i<num; i++){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Planet planet = new Planet(xxPos, yyPos, xxVel,yyVel,mass,imgFileName);
			planets[i] = planet;
//			System.out.println(imgFileName);
//			System.out.println(xxPos);
//			System.out.println(yyPos);
		}return planets;
	}

//	public static String imageToDraw = ".images/starfield.jpg";



	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int n = planets.length;

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius,radius);
		StdDraw.clear();
		StdDraw.picture(0,0,"./images/starfield.jpg");
		for (Planet p:planets){
			p.Draw();
		}


		double time = 0.0;
		while (time<T) {
			double[] xForces = new double[n];
			double[] yForces = new double[n];
			int pIdx = 0;
			for (Planet p: planets) {
				xForces[pIdx] = p.calcNetForceExertedByX(planets);
				yForces[pIdx] = p.calcNetForceExertedByY(planets);
				pIdx++;
			}
			pIdx = 0;
			for (Planet p:planets){
				p.update(dt, xForces[pIdx],yForces[pIdx]);
				pIdx++;
			}
			StdDraw.setScale(-radius,radius);
			StdDraw.clear();
			StdDraw.picture(0,0,"./images/starfield.jpg");
			for (Planet p:planets){
				StdDraw.picture(p.xxPos, p.yyPos, "./images/"+p.imgFileName);
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
	}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
					planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
		}
	}
}
