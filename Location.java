import java.util.*;
import processing.core.*;

public abstract class Location 
	extends Entity
{
	private Point position;
	
	public Location(String name, List<PImage> imgs, Point position)
	{
      super(name, imgs);
      this.position = position;
	}

	public void set_position(Point pt)
	{
		position = pt;
	}

	public Point get_position()
	{
		return position;
	}
}


