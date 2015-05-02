
public class MinerNotFull extends Miner {
	private int resource_count;
	public MinerNotFull(String name,int resource_limit,Point position,int rate,int animation_rate)
	{
		super(name,position,rate,resource_limit,animation_rate);
		this.resource_count = 0 ;
	}
	
	//add Miner to Ore
}
