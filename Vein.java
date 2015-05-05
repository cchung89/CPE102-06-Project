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

	//methods implemented for thee next assignment
	/*
	public String entity_string()
	
	public void schedule_vein(WorldModel world, int ticks, PImage i_store)
	
	public Action create_vein_action(WorldModel world, PImage i_store)
	*/
}