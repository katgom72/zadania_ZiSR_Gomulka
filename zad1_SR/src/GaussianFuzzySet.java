public class GaussianFuzzySet extends FuzzySet {
    private double center;
    private double sigma;

    public GaussianFuzzySet(String name, double center, double sigma) {
        super(name);
        this.center = center;
        this.sigma = sigma;
    }

    @Override
    public double getMembership(double x) {
        return Math.exp(-Math.pow(x - center, 2) / (2 * Math.pow(sigma, 2)));
    }
}