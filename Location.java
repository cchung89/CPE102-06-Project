public abstract class Location 
	extends Entity
{
	private Point position;
	
	public Location(String name, Point position)
	{
      super(name);
      this.position = position;
	}

	public void set_position(Point point)
	{
		position = point;
	}

	public Point get_position()
	{
		return position;
	}
}

