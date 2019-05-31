import org.jgrapht.graph.DefaultWeightedEdge;

class DistanceEdge extends DefaultWeightedEdge {

    private String label;
    public static String DRIVING_CAR = "Driving_Car";
    public static String FOOT = "Foot";
    public static String STRAIGHT = "Straight";

    public DistanceEdge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString()
    {
        return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
    }
}
