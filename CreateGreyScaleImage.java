/*
	Example program that uses RGBImage class to read and write jpegs.

	Author: Michael Eckmann
	Skidmore College
	for Spring 2017
	Digital Image Processing Course

*/
import java.io.*;

public class CreateGreyScaleImage
{

	public static RGBImage makeGray(RGBImage img)
	{
		RGBImage gray = new RGBImage(img.getHeight(),img.getWidth());
		
		for (int r=0; r < img.getNumRows(); r++)
		{
			for (int c=0; c < img.getNumCols(); c++)
			{
				int grayVal = (int)(img.getPixel(r,c).getRed()*0.299 + img.getPixel(r,c).getGreen()*0.587 + img.getPixel(r,c).getBlue()*0.114);
				gray.setPixel(r,c,grayVal, grayVal, grayVal);
			}
		}

		return gray;	
	}

	// example main that reads the image from args[0] and makes the image grayscale and writes the
        // grayscale version of the input jpeg to file named: args[1]

	public static void main(String args[]) throws IOException
	{
		RGBImage inImg = new RGBImage(args[0]);
		RGBImage outImg;
		outImg = makeGray(inImg);
		outImg.writeImage(args[1]);
	}

}
