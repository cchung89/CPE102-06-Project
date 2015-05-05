public class Ore 
	extends Mineral
{
	public Ore (String name,Point position, int rate)
	{
		super(name,position,rate);
	}
	
	//method overloading
	public Ore(String name, Point position)
	{
		super(name,position, 5000);
	}
	
	//method implemented for the next assignments
	/*
	public String entity_string()
	
	public void schedule_ore(WorldModel world, int ticks, PImage i_store)
	
	public Action create_ore_action(WorldModel world, PImage i_store)
	*/
	
}