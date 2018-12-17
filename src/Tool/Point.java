package Tool;

public class Point {
    int id;
    float weight;
    Point nextPoint;

    public Point(int id,float weight,Point nextPoint)
    {
        this.id = id;
        this.weight = weight;
        this.nextPoint = nextPoint;
    }
}
