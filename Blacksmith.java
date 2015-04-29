public class Blacksmith extends Character:
	private int resource_distance;
	private int resource_count ;
	
	public Blacksmith(name,position,imgs,resource_limit,rate,resource_distance = 1)
	{super(name,imgs,position, rate,resource_limit);
     this.resource_distance = resource_distance;
     this.resource_count = 0; 
	}

	public  int get_resource_distance()
		{

			return resource_distance;
		}

	//entity string



		