import java.util.*;

import processing.core.*;


public class WorldView 
{
	private int view_cols;
	private int view_rows;
	private WorldModel world;
	private int tile_width;
	private int tile_height;
	private int num_rows;
	private int num_cols;
	private Rectangle viewport;
	private PApplet processor = new PApplet();
	
    public WorldView(PApplet processor, int view_cols, int view_rows, WorldModel world , int tile_width, int tile_height)
    {
    	this.processor = processor;
    	this.world = world;
    	this.tile_width = tile_width;
    	this.tile_height = tile_height;
    	this.num_cols = world.num_cols;
    	this.num_rows = world.num_rows;
    	this.viewport = new Rectangle(0,0,view_cols,view_rows);
    }
    
    public void draw_background()
    {
    	for (int y = 0; y < this.viewport.get_height(); y++)
    	{
    		for(int x = 0; x < this.viewport.get_width(); x++)
    		{
    			Point w_pt = this.viewport_to_world(new Point(x,y));
    			PImage img = this.world.get_background_image(w_pt);
    			processor.image(img,x * this.tile_width , y * this.tile_height);			
    		}	
    	}
    }
    
    public void draw_entities()
    {
    	for (Location entity: this.world.get_entities() )
    	{
    		if (this.viewport.collidepoint(entity.get_position().x, entity.get_position().y ))
    		{
    			Point v_pt = this.world_to_viewport(entity.get_position());
    			processor.image(entity.get_image(), v_pt.x * this.tile_width, v_pt.y * this.tile_height);
    		}
    	}
    }
    
    public void draw_viewport()
    {
    	this.draw_background();
    	this.draw_entities();
    }

    public void update_view(int delta_x, int delta_y)
    {
    	this.viewport = this.create_shifted_viewport(delta_x, delta_y, this.num_rows , this.num_cols);
    	//this.draw_viewport();
	}

	public void update_view_tiles(List<Point> tiles)
	{   
		for(Point tile : tiles)
		{
			if (this.viewport.collidepoint(tile.x , tile.y))
			{
				Point v_pt = this.world_to_viewport(tile);
		        PImage img = this.get_tile_image(v_pt);
		        this.update_tile(v_pt, img);
			}
		}
	}

	public Rectangle update_tile(Point view_tile_pt, PImage surface)
	{
		int abs_x = view_tile_pt.x * this.tile_width;
		int abs_y = view_tile_pt.y * this.tile_height;

		processor.image(surface, abs_x, abs_y);

		return new Rectangle(abs_x, abs_y, this.tile_width, this.tile_height);
	}

	public PImage get_tile_image(Point view_tile_pt)
	{
		Point pt = this.viewport_to_world(view_tile_pt);
		PImage bgnd = this.world.get_background_image(pt);
    	Location occupant = this.world.get_tile_occupant(pt);
    	if (occupant != null)
    	{
    		return occupant.get_image();
    		/*PGraphics graphic = processor.createGraphics(this.tile_width, this.tile_height);
		    graphic.image(bgnd, 0, 0);
		    graphic.image(occupant.get_image(), 0, 0);
		    return graphic.get();*/
		}
		else
		{
		  	return bgnd;
		}
	}

    public Point viewport_to_world(Point pt)
    {
      return new Point(pt.x + viewport.get_left(), pt.y + viewport.get_top()) ;
    }

    public Point world_to_viewport(Point pt)
    {
    	return new Point(pt.x - viewport.get_left(), pt.y - viewport.get_top());
    }
    
    public static int clamp(int v, int low, int high)
    {
    	return Math.min(high, Math.max(v, low)) ;
    }

    public Rectangle create_shifted_viewport(int delta_x, int delta_y, int num_rows, int num_cols)
    {
    	int new_x = clamp(viewport.get_left() + delta_x, 0, num_cols - viewport.get_width());
    	int new_y = clamp(viewport.get_top() + delta_y, 0, num_rows - viewport.get_height());

    	return new Rectangle(new_x, new_y, viewport.get_width(), viewport.get_height());
    }
}
