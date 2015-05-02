public class Vein extends Mineral
{
	private int resource_distance = 1 ;

	public Vein(String name , int rate , Point position, int resource_distance)
	{
		super(name,position,rate);
	
		this.resource_distance = resource_distance;	
	}

	public double get_resource_distance()
	{
		return resource_distance;

	}
	
}

	//public string entity_string
	//schedule methods/actions