public class IrisClassifier {
    public static void main(String[] args) {

        IrisClassModel setosa = new IrisClassModel("Setosa",
                new double[]{5.00, 3.42, 1.46, 0.24}, // średnie
                new double[]{0.35, 0.38, 0.17, 0.10}  // odchylenia
        );

        IrisClassModel versicolor = new IrisClassModel("Versicolor",
                new double[]{5.93, 2.77, 4.26, 1.32},
                new double[]{0.51, 0.31, 0.46, 0.19}
        );

        double[] newFlower = {5.1, 3.5, 1.4, 0.2}; // Typowa Setosa

        double scoreSetosa = setosa.calculateTotalMembership(newFlower);
        double scoreVersicolor = versicolor.calculateTotalMembership(newFlower);

        System.out.println("Wynik Setosa: " + scoreSetosa);
        System.out.println("Wynik Versicolor: " + scoreVersicolor);

        if (scoreSetosa > scoreVersicolor) {
            System.out.println("Klasyfikacja: To jest IRIS SETOSA");
        } else {
            System.out.println("Klasyfikacja: To jest IRIS VERSICOLOR");
        }
    }
}