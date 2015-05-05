public abstract class Mineral 
	extends Natural
{
	private int rate;

	public Mineral(String name, Point position, int rate)
	{ 
		super(name, position);
		this.rate = rate;
	}

	protected int get_rate()
	{ 
		return rate;
	}
}