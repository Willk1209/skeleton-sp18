public class TestPlanet{
    public static void main(String[] args){
        Planet star1 = new Planet(10.0,9.0,3.0,4.0,2.0,"Whatever");
        Planet star2 = new Planet(-5.0,3.0,1.0,-2.0,5.0,"No matter what");
	System.out.println(star1.calcForceExertedBy(star2));
}
}
