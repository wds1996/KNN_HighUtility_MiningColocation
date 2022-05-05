package BaseClass;

public class Instance {
    /*
     * ´æ·ÅÊµÀı
     */
    private String feature;
    private String id;
    private double x;
    private double y;
    private double u;

    public Instance(String feature, String id, double x, double y, double u) {
        this.feature = feature;
        this.id = id;
        this.x = x;
        this.y = y;
        this.u = u;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

	@Override
	public String toString() {
		return "Instance{" +
				"feature='" + feature + '\'' +
				", id='" + id + '\'' +
				", x=" + x +
				", y=" + y +
				", u=" + u +
				'}';
	}
}
