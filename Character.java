import java.util.*;
import processing.core.*;

public abstract class Character 
	extends Job
{
	private int rate;
	private int resource_limit;
	private int resource_count;

	public Character(String name, List<PImage> imgs, Point position, int rate, int resource_limit, int resource_count)
	{	
		super(name, imgs, position);
		this.rate = rate;
		this.resource_limit = resource_limit;
		this.resource_count = 0;
	}

	public int get_rate()
	{	
		return rate;
	}

	public void set_resource_count(int n)
	{
		resource_count = n;
	}

	public int get_resource_count()
	{	
		return resource_count;
	}

	public int get_resource_limit()
	{	
		return resource_limit;
	}
}
