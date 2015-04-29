public class Vein extends Mineral:
	private double resource_distance ;

	public Vein(name,rate,position,imgs,resource_distance = 1)
	{super(name,imgs,position,rate);
	 this.resource_distance = resource_distance;	
	}

	public double get_resource_distance()
	{
		return resource_distance;

	}

	//public string entity_string
	//schedule methods/actions