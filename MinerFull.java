
public class MinerFull extends Miner 
{
	
	public MinerFull(String name, int resource_limit, Point position, int rate, int animation_rate)
	{
		super(name,position,rate,resource_limit,animation_rate);
	}
	
	pubilc boolean miner_to_smith(world,smith)
	{
	 Point entity_pt = this.get_position();
	 if (!= smith)
	 {
		 return ([this.position], false);
	 }
	 
	 Point smith_pt = smith.get_position();
	 if (adjacent(entity_pt,smith_pt))
	 {
		smith.set_resource_count(smith.get_resource_count() + this.get_resource_count())
		this.set_resource_count(0);
		return ([],true);
		
		
	 }
	 else
	 {
		 Point new_pt = this.next_position(world,smith_pt)
				 return (move_entity(this,new_pt),false);
	 }
	 
	 
	 
		 
	 
	}
	
	
}