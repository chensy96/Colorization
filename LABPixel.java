/*

	A class that represents 1 pixel of a color image in l-alpha-beta color space

	Author: Michael Eckmann
	Skidmore College
	for Spring 2017
	Digital Image Processing Course

*/
public class LABPixel
{
	private double l;
	private double alpha;
	private double beta;

	public static int count = 0;
	public static int count0 = 0;
	public static int count255 = 0;

	public LABPixel(double l, double a, double b)
	{
		this.l = l;
		this.alpha = a;
		this.beta = b;
	}

	public double getL()
	{
		return l;
	}

	public double getA()
	{
		return alpha;
	}

	public double getB()
	{
		return beta;
	}

	public void setA(double al)
	{
		alpha = al;
	}

	public void setB(double be)
	{
		beta = be;
	}
	public RGBPixel convertToRGB()
	{
		double rd, gd, bd;

		double L,M,S;

		double oneOverSqr3 = 1.0 / Math.sqrt(3);
		double oneOverSqr6 = 1.0 / Math.sqrt(6);
		double oneOverSqr2 = 1.0 / Math.sqrt(2);

		L = oneOverSqr3*l + oneOverSqr6*alpha + oneOverSqr2*beta;
		M = oneOverSqr3*l + oneOverSqr6*alpha - oneOverSqr2*beta;
		S = oneOverSqr3*l - 2*oneOverSqr6*alpha;
		
		L = Math.pow(10,L);
		M = Math.pow(10,M);
		S = Math.pow(10,S);


		rd = 4.4679 * L - 3.5873 * M + 0.1193 * S;
		gd = -1.2186 * L + 2.3809 * M - 0.1624 * S;
		bd = 0.0497 * L - 0.2439 * M + 1.2045 * S;

/*
This was the calculated inverse (slightly diff. from what's in the paper)
4.468669863496255 -3.5886759034721267 0.11960436657860117
-1.2197166276177633 2.3830879129554567 -0.16263011175140055
0.05850847693854589 -0.26107843902769373 1.205665908525623
*/
/*
		rd = 4.468669863496255 * L - 3.5886759034721267 * M + 0.11960436657860117 * S;
		gd = -1.2197166276177633 * L + 2.3830879129554567 * M - 0.16263011175140055 * S;
		bd = 0.05850847693854589 * L - 0.26107843902769373 * M + 1.205665908525623 * S;
*/
		count++;
		if (rd < 0 || gd < 0 || bd < 0)  count0++;
		if (rd > 255 || gd > 255 || bd > 255)  count255++;

		return new RGBPixel((int)rd,(int)gd,(int)bd);

	}







	
}
