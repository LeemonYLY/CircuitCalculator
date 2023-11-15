package circuitCalculator;

public class CircuitCalculator {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Please provide 4 double values as arguments.");
			return;
		}

		double a = Double.parseDouble(args[0]); // resistance1 (ohms)
		double b = Double.parseDouble(args[1]); // resistance2 (ohms)
		double c = Double.parseDouble(args[2]); // capacitance1 (farads)
		double d = Double.parseDouble(args[3]); // capacitance2 (farads)

//		if (!isValidResistance(a) || !isValidResistance(b) || !isValidCapacitance(c) || !isValidCapacitance(d)) {
//			System.out.println("Invalid input values. Please check the input ranges.");
//			return;
//		}

		double e = func1(a, b); // equivalent parallel resistance (ohms)
		double f = func2(a, b, c, e); // equivalent series capacitance (farads)
		double g = func3(a, c, d, f); // total impedance (ohms)

		g = Math.round(g * 100) * 1.0 / 100;
		System.out.println(g);
	}

	// Calculate equivalent parallel resistance
	public static double func1(double resistance1, double resistance2) {
		return (resistance1 * resistance2) / (resistance1 + resistance2);
	}

	// Calculate equivalent series capacitance
	public static double func2(double resistance1, double resistance2, double capacitance1,
			double equivalentResistance) {
		double capacitance2 = capacitance1 * (resistance1 / (resistance1 + resistance2));
		capacitance1 = capacitance1 * equivalentResistance;
		capacitance1 %= 1e4;
		capacitance2 %= 1e4;
		return capacitance1 + capacitance2;
	}

	// Calculate total impedance
	public static double func3(double resistance1, double capacitance1, double capacitance2,
			double equivalentCapacitance) {
		double angularFrequency = 2 * Math.PI * 50; // assuming a frequency of 50 Hz
		double capacitiveReactance = 100000 / (angularFrequency * equivalentCapacitance);
		double s = (capacitance1 * capacitance2) / (capacitance1 + capacitance2);
		return (Math.sqrt(Math.pow(resistance1, 2) + Math.pow(capacitiveReactance, 2)) * s) % 10000;
	}

	public static boolean isValidResistance(double resistance) {
		return resistance >= 0 && resistance <= 1000; // 0 ohms to 1 megaohm
	}

	public static boolean isValidCapacitance(double capacitance) {
		return capacitance >= 1 && capacitance <= 100; // 1 picofarad to 1 millifarad
	}
}
