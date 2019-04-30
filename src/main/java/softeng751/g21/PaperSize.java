package softeng751.g21;

public enum PaperSize {
    A0(1),
    A1(0.5),
    A2(0.25),
    A3(0.125),
    A4(0.0625),
    A5(0.03125),
    A6(0.015625);

    private double fraction;

    PaperSize(double fraction) {
        this.fraction = fraction;
    }

    public double getFraction() {
        return fraction;
    }
}