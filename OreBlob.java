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
	
	/*
		//blob to vein , blob_next_position
	public Point blob_next_position(Worldmodel world, Point dest_pt)
	*/
}