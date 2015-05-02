public abstract class Mineral extends Location
{
	private int rate;

	public Mineral(String name, Point position, int rate)
	{ super(name, position);
	  this.rate = rate;
	}

	public int get_rate()
		{ return rate;
		}

	}