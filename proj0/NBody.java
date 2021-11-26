public class NBody{
    public static double readRadius(String fnm){
	In in = new In(fnm);
	in.readInt();
	double r = in.readDouble();		
        return r;
}
}
