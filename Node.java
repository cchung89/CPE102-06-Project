
public class Node
{
	private Node came_from;
	private Point point;
	private int g_score;
	private double f_score;
	
	public Node(Point point)
	{
		this.point = point;
	}
	
	public Point get_point()
	{
		return this.point;
	}
	
	public void set_point(Point pt)
	{
		this.point = pt;
	}
	
	public Node get_came_from()
	{
		return this.came_from;
	}
	
	public void set_came_from(Node pt)
	{
		this.came_from = pt;
	}
	
	public int get_g_score()
	{
		return this.g_score;
	}
	
	public void set_g_score(int g)
	{
		this.g_score = g;
	}
	
	public double get_f_score()
	{
		return this.f_score;
	}
	
	public void set_f_score(double f)
	{
		this.f_score = f;
	}
}
