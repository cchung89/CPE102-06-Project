
public abstract class Entity:
	private String name;
	//private  imgs;
	//private current_img;

	public Entity (String name,//imgs)
	{
		this.name = name;
		this.imgs = imgs;
		this.current_img = 0;

	}

	public get_images()
	{
		return imgs;

	}

	public get_image()
	{
		return imgs[current_img];
	}

	public get_name()
	{
		return name;
	}

	public next_image()
	{

		current_img = (current_img + 1) % imgs.size();
	}