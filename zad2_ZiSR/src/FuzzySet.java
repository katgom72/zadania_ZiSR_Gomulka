public abstract class FuzzySet {
    protected String name;

    public FuzzySet(String name) {
        this.name = name;
    }

    public abstract double getMembership(double x);

    public String getName() {
        return name;
    }
}
