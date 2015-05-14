
public class Tile {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Tile(int x, int y, int width, int height)
	{
		this.x = x ;
		this.y =y;
		this.width = width;
		this.height = height ;
	}
	
	public int get_x()
	{
		return x;
	}
	
	public int get_y()
	{
		return y;
	}
	
	public int get_width()
	{
		return width;
	}

	public int get_height()
	{
		return height;
	}
}
