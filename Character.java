public abstract class Character extends Job:
	private int rate;
	private int resource_limit;
	private int resource_count;

	public Character(String name, imgs, Point position,int rate,int resource_limit)
	{	super();
		this.rate = rate;
		this.resource_limit = resource_limit;
		this.resource_count = resource_limit ;

	}

	public int get_rate()
	{	return rate;


	}

	public void set_resource_count(int n);
	{
		resource_count = n ;
	}

	public int get_resource_count()
	{	return resource_count;

	}

	public int get_resource_limit()
	{	return resource_limit; 

	}