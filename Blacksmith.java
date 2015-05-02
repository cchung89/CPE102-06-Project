public class Blacksmith extends Character
{
	private int resource_distance;
	private int resource_count ;
	
	public Blacksmith(String name, Point position, int resource_limit, int rate, int resource_distance)
	{
		super(name,position, rate,resource_limit);
		this.resource_distance = resource_distance;
		this.resource_count = 0;
		
	}
	

	public  int get_resource_distance()
		{

			return resource_distance;
		}


	//entity string



}