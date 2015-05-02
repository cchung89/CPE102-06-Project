
public class MinerNotFull extends Miner {
	private int resource_count;
	
	public MinerNotFull(String name,int resource_limit,Point position,int rate)
	{
		super(name,position,rate,resource_limit);
		this.resource_count = 0 ;
	}
	
    public void set_resource_count()
    {
    	resource_count = 0 ;
    }
	
	//add Miner to Ore
}
