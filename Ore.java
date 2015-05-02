public class Ore extends Mineral
{
	private int rate ;
	public Ore (String name,Point position, int rate)
	{
		super(name,position,rate);
	}
	
	//method overloading
	public Ore(String name, Point position)
	{
		super(name,position,5000);
		
	}

	//public entity string and schedule/action methods


	}