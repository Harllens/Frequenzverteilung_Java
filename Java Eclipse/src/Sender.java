import java.util.ArrayList;
import java.util.List;


public class Sender
{
    public Sender() {}

    private Integer Sender;
    private double X;
    private double Y;
    private double Radius;
    private int Frequency;
    private List<Integer> Overlaps = new ArrayList<Integer>();
    private List<Integer> DisableFrecuencies = new ArrayList<Integer>();

    // /
    // Get / Set properties
    // /
    int getSender()
    {
	return Sender;
    }

    void setSender(int sender)
    {
	this.Sender = sender;
    }

    double get_X()
    {
	return X;
    }

    void set_X(double x)
    {
	this.X = x;
    }

    double get_Y()
    {
	return Y;
    }

    void set_Y(double y)
    {
	this.Y = y;
    }

    double get_Radius()
    {
	return Radius;
    }

    void set_Radius(double radius)
    {
	this.Radius = radius;
    }

    int getFrequency()
    {
	return Frequency;
    }

    void setFrequency(int frequency)
    {
	this.Frequency = frequency;
    }

    List<Integer> getOverlapsList()
    {
	return Overlaps;
    }

    void setOverlapsList(int overlap)
    {
	this.Overlaps.add(overlap);
    }

    List<Integer> getDisableFrecuencies()
    {
	return DisableFrecuencies;
    }

    void setDisableFrecuencies(int frequency)
    {
	this.DisableFrecuencies.add(frequency);
    }
}
