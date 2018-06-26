/*

	A class that represents 1 pixel of a color image

	The ranges of the red, green and blue values are 0 .. 255 inclusive

	Author: Michael Eckmann
	Skidmore College
	for Spring 2017
	Digital Image Processing Course

*/
public class RGBPixel {
	private int r;
	private int g;
	private int b;

	public RGBPixel(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public int getRed() {
		return r;
	}

	public int getGreen() {
		return g;
	}

	public int getBlue() {
		return b;
	}

	public void setRed(int r) {
		if (r >= 0 && r <= 255)
			this.r = r;
		else {
			if (r < 0) {
				this.r = 0;
			} else {
				this.r = 255;
			}
		}
	}

	public void setGreen(int g) {
		if (g >= 0 && g <= 255)
			this.g = g;
		else {
			if (g < 0) {
				this.g = 0;
			} else {
				this.g = 255;
			}
		}
	}

	public void setBlue(int b) {
		if (b >= 0 && b <= 255)
			this.b = b;
		else {
			if (b < 0) {
				this.b = 0;
			} else {
				this.b = 255;
			}
		}
	}

	public int getIntensity() {
		return (int) (getRed() * 0.299 + getGreen() * 0.587 + getBlue() * 0.114);
	}

	public YCbCrPixel convertToYCbCr() {
		int Y = getIntensity();
		double Cb, Cr;

		Cb = -0.17 * r - 0.33 * g + 0.5 * b + 128;
		Cr = 0.5 * r - 0.42 * g - 0.08 * b + 128;

		return new YCbCrPixel(Y, Cb, Cr);
	}

}
