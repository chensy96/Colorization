public class Characteristic {
	private double[] characteristics;
	private int classNumber;
	private int total;
	private Pair[] randomJitRC;

	public Characteristic(int numDims) {
		characteristics = new double[numDims];
	}

	public void addValues(Characteristic c) {
		for (int i = 0; i < characteristics.length; i++) {
			characteristics[i] = characteristics[i] + c.characteristics[i];
		}
	}

	public void setValues(double[] vals) {
		for (int i = 0; i < vals.length; i++) {
			characteristics[i] = vals[i];
		}
	}

	public double getVal(int idx) {
		return characteristics[idx];
	}

	public void setVal(int idx, double value) {
		characteristics[idx] = value;
	}

	public void setClassNumber(int cN) {
		classNumber = cN;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setTotal(int t) {
		total = t;
	}

	public int getTotal() {
		return total;
	}

	public int getlength() {
		return characteristics.length;
	}

	public double disIntensity(Characteristic c, Characteristic m) {

		double calc1 = m.getVal(0) - c.getVal(0);
		double calc2 = m.getVal(1) - c.getVal(1);

		double dis = Math.sqrt(Math.pow(calc1, 2) + Math.pow(calc2, 2));

		return dis;
	}

	public double disSd(Characteristic c1, Characteristic c2) {
		double dis;
		dis = c1.getVal(0) - c2.getVal(0);
		double absdis = Math.abs(dis);

		return absdis;
	}

}
