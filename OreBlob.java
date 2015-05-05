public class OreBlob 
	extends Destroyer
{
	private int rate;
	
	public OreBlob(String name,Point position, int rate)
		{
			super(name,position);
			this.rate = rate;
		}

	public int get_rate()
		{
		  return rate;
		}
	
	//methods implemented for the next assignments
	/*
	public Action create_ore_blob_action(WorldModel world, PImage i_store)
	
	public Point blob_next_position(Worldmodel world, Point dest_pt)
	
	public Pair<List<Point>, Boolean> blob_to_vein(WorldModel world, Vein vein)
	
	public void schedule_blob(WorldModel world int ticks, PImage i_store)
	*/
}