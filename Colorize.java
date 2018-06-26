
/*

	Author: Michael Eckmann
	Skidmore College
	for Spring 2017
	Digital Image Processing Course


*/
import java.io.*;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Colorize {
	private static Characteristic[] ccluster;
	private static Characteristic[] gcluster;
	private static Characteristic[] rand;

	public static void main(String args[]) throws IOException {

		RGBImage colorInput = new RGBImage(args[0]);
		RGBImage grayInput = new RGBImage(args[1]);
		int k = 3;

		// compute the mean and standard deviation of the luminace of each image
		colorInput.computeIntensityMeanAndStdDev();
		grayInput.computeIntensityMeanAndStdDev();

		// only remap the luminances of the source to match the distribution of
		// the target
		colorInput.remapIntensity(grayInput); // remap the luminances of the
												// color (to match the
												// distribution of the
												// grayscale)

		KMeans km = new KMeans(k, 2, colorInput.getNumRows(), colorInput.getNumCols());

		System.out.println("Just created the KMeans object");

		for (int r = 2; r < colorInput.getNumRows() - 2; r++) {
			for (int c = 2; c < colorInput.getNumCols() - 2; c++) {

				double[] cproperties = new double[2];
				cproperties[0] = colorInput.getPixel(r, c).getIntensity() / 255.0;

				double sum = 0;

				for (int r1 = r - 2; r1 < r + 3; r1++) {
					for (int c1 = c - 2; c1 < c + 3; c1++) {

						sum += colorInput.getPixel(r1, c1).getIntensity() / 255.0;

					}
				}
				double mean = sum / 25.0;
				double sum2 = 0;
				double maxsd = -10000;
				double minsd = 10000;
				for (int r2 = r - 2; r2 < r + 3; r2++) {
					for (int c2 = c - 2; c2 < c + 3; c2++) {
						if (Math.pow(colorInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2) > maxsd) {
							maxsd = Math.pow(colorInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2);
						}
						if (Math.pow(colorInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2) < minsd) {
							minsd = Math.pow(colorInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2);
						}
					}
				}
				for (int r3 = r - 2; r3 < r + 3; r3++) {
					for (int c3 = c - 2; c3 < c + 3; c3++) {
						sum2 += (Math.pow(colorInput.getPixel(r3, c3).getIntensity() / 255.0 - mean, 2) - minsd)
								/ (maxsd - minsd);
					}
				}
				cproperties[1] = Math.sqrt(sum2 / 25.0);

				km.assignPixelProperties(r, c, cproperties);
			}
		}

		System.out.println("assigned pixel props for color image ");
		ccluster = new Characteristic[k];
		for (int i = 0; i < ccluster.length; i++) {
			ccluster[i] = new Characteristic(2);
		}

		System.out.println("before computing kmeans for color");

		ccluster = km.runKmeans();

		System.out.println("finished computing kmeans for color");
		for (int i = 0; i < k; i++) {
			int numInC = 0;
			for (int r = 2; r < colorInput.getNumRows() - 2; r++) {
				for (int c = 2; c < colorInput.getNumCols() - 2; c++) {
					if (km.pixelProperties[r][c].getClassNumber() == i) {
						numInC++;
					}
				}
			}
			ccluster[i].setTotal(numInC);
		}

		//
		//
		KMeans km1 = new KMeans(k, 2, grayInput.getNumRows(), grayInput.getNumCols());

		for (int r = 2; r < grayInput.getNumRows() - 2; r++) {
			for (int c = 2; c < grayInput.getNumCols() - 2; c++) {

				double[] gproperties = new double[2];
				gproperties[0] = grayInput.getPixel(r, c).getIntensity() / 255.0;

				double sum = 0;

				for (int r1 = r - 2; r1 < r + 3; r1++) {
					for (int c1 = c - 2; c1 < c + 3; c1++) {

						sum += grayInput.getPixel(r1, c1).getIntensity() / 255.0;

					}
				}
				double mean = sum / 25.0;
				double sum2 = 0;
				double maxsd = -10000;
				double minsd = 10000;
				for (int r2 = r - 2; r2 < r + 3; r2++) {
					for (int c2 = c - 2; c2 < c + 3; c2++) {
						if (Math.pow(grayInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2) > maxsd) {
							maxsd = Math.pow(grayInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2);
						}
						if (Math.pow(grayInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2) < minsd) {
							minsd = Math.pow(grayInput.getPixel(r2, c2).getIntensity() / 255.0 - mean, 2);
						}
					}
				}
				for (int r3 = r - 2; r3 < r + 3; r3++) {
					for (int c3 = c - 2; c3 < c + 3; c3++) {
						sum2 += (Math.pow(grayInput.getPixel(r3, c3).getIntensity() / 255.0 - mean, 2) - minsd)
								/ (maxsd - minsd);
					}
				}
				gproperties[1] = Math.sqrt(sum2 / 25.0);

				km1.assignPixelProperties(r, c, gproperties);
			}
		}

		gcluster = new Characteristic[k];
		for (int i = 0; i < gcluster.length; i++) {
			gcluster[i] = new Characteristic(2);
		}

		System.out.println("before computing kmeans for gray");

		gcluster = km1.runKmeans();

		System.out.println("finished computing kmeans for gray");

		for (int i = 0; i < k; i++) {
			int corresColorclustertoGray = 0;
			double current = 10000;

			for (int j = 0; j < k; j++) {

				if (current > ccluster[i].disIntensity(ccluster[i], gcluster[j])) {
					current = ccluster[i].disIntensity(ccluster[i], gcluster[j]);
					corresColorclustertoGray = j;
				}
			}
			ccluster[i].setClassNumber(corresColorclustertoGray);

		}

		System.out.println("after assign each gray cluster to each color cluster");

		System.out.println("next we will put random pixels into pairs etc.");

		Characteristic[][] random1 = new Characteristic[k][];

		for (int i = 0; i < k; i++) {
			random1[i] = new Characteristic[ccluster[i].getTotal()];
		}
		int idx[] = new int[k]; // 5 indices all 0
		for (int r = 2; r < km.pixelProperties.length - 2; r++) {

			for (int c = 2; c < km.pixelProperties[r].length - 2; c++) {
				int cl = km.pixelProperties[r][c].getClassNumber();
				random1[cl][idx[cl]] = new Characteristic(2);
				random1[cl][idx[cl]].setVal(0, r);
				random1[cl][idx[cl]].setVal(1, c);
				idx[cl]++;
			}

		}

		for (int i = 0; i < k; i++) {
			System.out.println("2.----------");

			int[] rl = new int[100];
			int[] cl = new int[100];

			for (int r = 0; r < 100; r++) {
				int randidx = 0;
				randidx = 0 + (int) (Math.random() * (ccluster[i].getTotal() + 1));
				rl[r] = (int) random1[i][randidx].getVal(0);
				cl[r] = (int) random1[i][randidx].getVal(1);
			}

			System.out.println("3.----------");

			colorInput.putrandomIn(rl, cl);

			int halfHeight = 3, halfWidth = 3;
			for (int gr = halfHeight; gr < grayInput.getNumRows() - halfHeight; gr++) {
				for (int gc = halfWidth; gc < grayInput.getNumCols() - halfWidth; gc++) {
					if (km1.pixelProperties[gr][gc].getClassNumber() == ccluster[i].getClassNumber()) {
						grayInput.colorize(colorInput, gr, gc);
					}
				}
			}

		}

		System.out.println("just about to write the image");

		grayInput.writeImage("100pairs" + k + "kcolorized-" + args[1]);

	}

}
