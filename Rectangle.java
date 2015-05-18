
public class Rectangle {
	private int left;
	private int top;
	private int height;
	private int width;
	public Rectangle(int left, int top, int width, int height)
	{
		this.left = left; //x
		this.top = top;//y
		this.height = height;
		this.width = width;
	}
	public int get_height()
	{
		return height;
	}
	
	public int get_width()
	
	{
		return width;
	}
	
	public int get_left()
	{
		return left;
	}
	
	public int get_top()
	{
		return top;
	}
	public boolean collidepoint(int x2, int y2) 
	{
		//if (left < x2 && left < (x2 + width) && y2 < top &&  y2 > (top - height) )
		return (((x2 <= (left + width)) && (x2 >= left)) && (y2 <= (top + height)) && (y2 >= top));
	}
}
