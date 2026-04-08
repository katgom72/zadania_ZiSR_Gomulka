public class Main {
    public static void main(String[] args) {
        FuzzySet[] sets = {
                new TriangularFuzzySet("Niska temperatura", 10, 15, 20),
                new TrapezoidalFuzzySet("Średnia temperatura", 15, 20, 25, 30),
                new GaussianFuzzySet("Wysoka temperatura", 35, 5)
        };

        double testX = 18.0;

        for (FuzzySet set : sets) {
            System.out.printf("Zbiór: %s, Przynależność dla %.1f: %.2f%n",
                    set.getName(), testX, set.getMembership(testX));
        }
    }
}