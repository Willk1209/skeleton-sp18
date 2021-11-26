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
		}return planets;
	}

}