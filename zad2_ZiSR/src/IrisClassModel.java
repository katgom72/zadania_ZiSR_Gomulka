public class IrisClassModel {
    String className;
    FuzzySet[] featureSets = new FuzzySet[4];

    public IrisClassModel(String name, double[] means, double[] sigmas) {
        this.className = name;
        for (int i = 0; i < 4; i++) {
            featureSets[i] = new GaussianFuzzySet(name + "_feature_" + i, means[i], sigmas[i]);
        }
    }

    public double calculateTotalMembership(double[] vector) {
        double sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += featureSets[i].getMembership(vector[i]);
        }
        return sum;
    }
}