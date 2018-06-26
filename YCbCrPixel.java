/*

	A class that represents 1 pixel of a color image

	The ranges of the Y is 0 .. 255 inclusive, Cb, Cr are doubles that can be positive or negative

	Author: Michael Eckmann
	Skidmore College
	for Spring 2017
	Digital Image Processing Course

*/
public class YCbCrPixel
{
	private int Y;
	private double Cb;
	private double Cr;

	public YCbCrPixel(int Y, double Cb, double Cr)
	{
		if (Y >= 0 && Y <= 255)
			this.Y = Y;
		else
			this.Y = 0;
		this.Cb = Cb;
		this.Cr = Cr;
	}

	public int getY()
	{
		return Y;
	}

	public RGBPixel convertToRGB()
	{
		int r, g, b;

		r = (int)(Y + 1.4 * (Cr - 128));
		g = (int)(Y - 0.344 * (Cb - 128) - 0.714 * (Cr - 128));
		b = (int)(Y + 1.772 * (Cb - 128));
			
		return new RGBPixel(r,g,b);
	}







	
}
