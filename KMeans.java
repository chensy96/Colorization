import java.util.Random;

public class KMeans {
	private int K; // number of classes
	int C;
	Characteristic[][] pixelProperties;
	private Characteristic[] means;
	private int numDimensions;
	private int count;

	public KMeans(int numClasses, int numDims, int numRows, int numCols) {
		K = numClasses;

		numDimensions = numDims;

		means = new Characteristic[K];
		for (int i = 0; i < means.length; i++) {
			means[i] = new Characteristic(numDimensions);
		}

		pixelProperties = new Characteristic[numRows][numCols];
		;
	}

	public Characteristic[] runKmeans() {
		randomizeMeans();
		// add random initial numbers to the means array of characteristic.
		RGBImage lbldImg = new RGBImage(pixelProperties.length, pixelProperties[0].length);

		Characteristic[] oldMeans = new Characteristic[K];

		for (int i = 0; i < K; i++) {
			oldMeans[i] = new Characteristic(numDimensions);
		}

		while (!isthesame(means, oldMeans) && count < 50) {
			for (int i = 0; i < K; i++) {
				oldMeans[i] = means[i];
			}

			for (int r = 2; r < lbldImg.getNumRows() - 2; r++) {
				for (int c = 2; c < lbldImg.getNumCols() - 2; c++) {

					int classNum = 0;
					classNum = determineClass(pixelProperties[r][c]);
					// System.out.println(classNum);
					pixelProperties[r][c].setClassNumber(classNum);
					// divided in to k groups
					// recalculate mean for each group
				}
			}
			recomputeMeans();
			count++;
		}

		return means;

	}

	private boolean isthesame(Characteristic[] c1, Characteristic[] c2) {

		for (int i = 0; i < K; i++) {
			for (int j = 0; j < numDimensions; j++) {
				if (c1[i].getVal(j) != c2[i].getVal(j)) {

					return false;
				}
			}
		}
		// System.out.println("yes");
		return true;
	}

	private void randomizeMeans() {
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < numDimensions; j++) {

				Random r = new Random();
				double rand = 0.0 + r.nextDouble() * 1.0;

				means[i].setVal(j, rand);
			}
		}
	}

	private void recomputeMeans() {
		// System.out.println("we have entered here!");
		// will need a sum per K of all the items belonging to each class
		Characteristic[] sums = new Characteristic[K];
		for (int s = 0; s < K; s++) {
			sums[s] = new Characteristic(numDimensions);
		}

		for (int i = 0; i < K; i++) {
			double gnums = 0;

			for (int r = 2; r < pixelProperties.length - 2; r++) {
				for (int c = 2; c < pixelProperties[0].length - 2; c++) {

					if (pixelProperties[r][c].getClassNumber() == i) {
						sums[i].addValues(pixelProperties[r][c]);
						gnums = gnums + 1;
					}
				}
			}

			for (int j = 0; j < numDimensions; j++) {
				double newVal = sums[i].getVal(j) / gnums;
				means[i].setVal(j, newVal);
			}
			// go into every group
			// go into every char in the group
			// add up all the char[i] in that group and calculate a mean
			// set the means into a new double array and into a char
			// put those k chars in the means array

		}
	}

	public void assignPixelProperties(int r, int c, double[] props) {
		pixelProperties[r][c] = new Characteristic(numDimensions);
		pixelProperties[r][c].setValues(props);
	}

	private int determineClass(Characteristic c) {
		int cls = 0;
		double current = 10000;

		for (int i = 0; i < K; i++) {

			if (current > c.disIntensity(c, means[i])) {
				current = c.disIntensity(c, means[i]);
				cls = i;
			}
		}

		return cls;
		// call distance in Characteristic class
		// to determine smallest distance between c and each mean
		// return class associated with the smallest distance
	}

}
