public class Blacksmith extends Character
{
	private int resource_distance;
	
	
	public Blacksmith(String name, Point position, int resource_limit, int rate, int resource_distance)
	{
		super(name,position, rate,resource_limit, 0);
		this.resource_distance = resource_distance;
		
		
	}
	

	public  int get_resource_distance()
		{

			return resource_distance;
		}


	//entity string



}