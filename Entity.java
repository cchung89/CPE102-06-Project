import java.util.*;

import processing.core.*;

public abstract class Entity
{	
	private String name;
	private List<PImage> imgs;
	private int current_img;
	
	public Entity(String name, List<PImage> imgs)
	{
		this.name = name;
		this.imgs = imgs;
		this.current_img = 0;
	}
	
	public String get_name()
	{
		return name;
	}
	
	public List<PImage> get_images()
	{
		return imgs;
	}
	
	public PImage get_image()
	{
		return imgs.get(current_img);
	}

	public void next_image()
	{
		current_img = (current_img + 1) % imgs.size();
	}
}

