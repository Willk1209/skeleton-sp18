/*
public class NBody{
    public static double readRadius(String fnm){
	In in = new In(fnm);
	in.readInt();
	double r = in.readDouble();		
        return r;
}
}
*/
//package Project0;



public class NBody {

    public static double readRadius(String filename){
        In in =new In(filename);
        in.readInt();
        double Radius=in.readDouble();
        return Radius;
    }

    /**

     * object return to body
     */

    public static Body[] readBodies(String filename){
        In in=new In(filename);
        int num = in.readInt();
        Body[] body=new Body[num];

        in.readDouble();
        for(int i=0;i<num;i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            body[i] = new Body(xP, yP, xV, yV, m, img);

        }
        return body;
    }

    /**
     * Draw the initial universe state
     */
    public static void main(String[] args) {
        /**
         * Get data --finished
         */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double uniRadius = NBody.readRadius(fileName);
        Body[] Planets = NBody.readBodies(fileName);


        StdDraw.setScale(-uniRadius, uniRadius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");


        for (Body var : Planets) {
            var.draw();
        }


        StdDraw.enableDoubleBuffering();


        for(double t=0;t<=T;t+=dt){
            //创造xForce和YForce数组
            double[] xForce=new double[Planets.length];
            double[] yForce=new double[Planets.length];
            //计算netForce
            for (int i=0;i<Planets.length;i++){
                xForce[i]=Planets[i].calcNetForceExertedByX(Planets);
                yForce[i]=Planets[i].calcNetForceExertedByY(Planets);
            }
            //更新每个星体 update
            for (int i=0;i<Planets.length;i++){
                Planets[i].update(dt,xForce[i],yForce[i]);
            }
            //绘制背景图
            StdDraw.picture(0,0,"images/starfield.jpg");
            //绘制每个星体的图片
            for (int i=0;i<Planets.length;i++){
                Planets[i].draw();
            }
            //显示屏幕外的缓冲区（双缓冲时动画在屏幕外）
            StdDraw.show();
            //暂停10毫秒
            StdDraw.pause(10);
        }







        /**
         * Print out the final state of the universe when time reaches T
         */
        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                    Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);

        }

    }

}
