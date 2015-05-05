public class Vein
	extends Mineral
{
	private int resource_distance;

	public Vein(String name , int rate , Point position, int resource_distance)
	{
		super(name,position,rate);
		this.resource_distance = resource_distance;	
	}
	
	//method overloading 
	public Vein(String name, int rate, Point position)
	{
		super(name,position,rate);
		this.resource_distance = 1 ;
	}

	public int get_resource_distance()
	{
		return resource_distance;
	}

	//public string entity_string
	//schedule methods/actions
}