import java.util.*;
import processing.core.*;

public abstract class Mineral 
	extends Natural
{
	private int rate;

	public Mineral(String name, List<PImage> imgs, Point position, int rate)
	{ 
		super(name, imgs, position);
		this.rate = rate;
	}

	protected int get_rate()
	{ 
		return rate;
	}
}