package com.fss;

import neuralnetwork.data.DataPackage;
import neuralnetwork.data.DataVector;

public class MainClassifier {

	public static void main(String[] args) {

		int lSize = 10; // Liczba próbek uczących na klasę (możesz zwiększyć dla lepszych wyników)

		// 1. Load data
		DataPackage all = new DataPackage();
		all.setFieldSeparator(",");
		all.setDecimalSeparator('.');
		// Upewnij się, że ścieżka do pliku jest poprawna dla Twojego komputera!
		all.loadTextFile("/Users/katarzyna/Downloads/wines.txt");

		// Divide classes
		DataPackage class1 = all.removeVectorsByColumnValue(0, 1);
		DataPackage class2 = all.removeVectorsByColumnValue(0, 2);
		DataPackage class3 = all.removeVectorsByColumnValue(0, 3);

		// Create test and learning packages
		DataPackage testPkg = new DataPackage();

		// Learning package (zbiór uczący - nasze "wzorce" reguł)
		DataPackage learnPkg = class1.removeRandomVectors(lSize);
		learnPkg.add(class2.removeRandomVectors(lSize));
		learnPkg.add(class3.removeRandomVectors(lSize));

		// Test package (pozostałe dane do sprawdzenia skuteczności)
		testPkg.add(class1);
		testPkg.add(class2);
		testPkg.add(class3);

		// 2. & 3.

		int correctPredictions = 0;
		int totalTests = testPkg.size();

		System.out.println("Rozpoczynam klasyfikację " + totalTests + " wektorów");

		for (DataVector testVector : testPkg.getList()) {

			double maxResult = -1.0;
			double predictedClass = -1.0;

			// Porównujemy testowy wektor z każdą "regułą" (wierszem) ze zbioru uczącego
			for (DataVector ruleVector : learnPkg.getList()) {

				// Używamy sumowania (lub mnożenia) wartości funkcji przynależności
				// Treść zadania sugerowała sumowanie:
				double compliance = 0.0;

				// Dla każdej z 13 cech (kolumny 1-13, bo 0 to klasa)
				for (int i = 1; i < 14; i++) {
					// Szerokość (width) bierzemy z zakresu danej kolumny w zbiorze uczącym
					double columnRange = learnPkg.getColumnRange(i);

					compliance += getGaussian(
							testVector.get(i),
							columnRange,
							ruleVector.get(i) // środek Gaussa to wartość z reguły
					);
				}

				// Szukamy reguły o maksymalnym dopasowaniu
				if (compliance > maxResult) {
					maxResult = compliance;
					predictedClass = ruleVector.get(0);
				}
			}

			// Sprawdzenie poprawności
			double realClass = testVector.get(0);
			if (predictedClass == realClass) {
				correctPredictions++;
			}
		}

		// 4. Show results
		double accuracy = (double) correctPredictions / totalTests * 100;

		System.out.println("Statystyki klasyfikatora:");
		System.out.println("Poprawne trafienia: " + correctPredictions + "/" + totalTests);
		System.out.printf("Skuteczność: %.2f%%%n", accuracy);
	}


	public static double getGaussian(double value, double width, double center) {
		// Jeśli zakres jest zerowy, unikamy dzielenia przez zero
		if (width == 0) width = 0.001;

		double w = 1.0 / width;
		double tmp = value - center;
		return Math.exp((-tmp * tmp) / (2 * Math.pow(width * 0.1, 2)));
		// Uwaga: dodałem mnożnik 0.1 do width, aby funkcja nie była zbyt "rozlana"
	}
}