import java.util.ArrayList;

import processing.core.*;


public class WorldView 

	
{
	private int view_cols;
	private int view_rows;
	private PApplet screen;
	private WorldModel world;
	private int tile_width;
	private int tile_height;
	private int num_rows;
	private int num_cols;
	private Rectangle viewport;
	
	

    public WorldView(int view_cols, int view_rows, PApplet screen, WorldModel world , int tile_width, int tile_height)
    {
    	this.view_cols = view_cols;
    	this.view_rows = view_rows;
    	this.screen = screen;
    	this.world = world;
    	this.tile_width = tile_width;
    	this.tile_height = tile_height;
    	this.num_cols = world.num_cols;
    	this.num_rows = world.num_rows;
    	this.viewport = Rectangle(0,0,view_cols,view_rows);
    }
    
    public void draw_background()
    {
    	for (int y = 0; y < this.viewport.height; y++)
    	{
    		for(int x = 0; x < this.viewport.width; x++)
    		{
    			Point w_pt = viewport_to_world(this.viewport(this.viewport,Point(x,y)));
    			PImage img = this.world.get_background_image(w_pt);
    			this.screen.image(img,x * this.tile_width , y * this.tile_height);
    					
    		}
    			
    	}
    }
    
    public void draw_entities()
    {
    	for (Location entity: this.world.get_entities() )
    	{
    		if (this.viewport.collidepoint(entity.get_position().x, entity.get_position().y ))
    		{
    			Point v_pt = world_to_viewport(this.viewport, entity.get_position());
    			this.screen.image(entity.get_image(), v_pt.x * this.tile_width, v_pt.y * this.tile_height);
    		}
    	}
    }
    
    public void draw_viewport()
    {
    	this.draw_background();
    	this.draw_entities();
    }

public void update_view(Point view_delta)
{
	Rectangle this.viewport = create_shifted_viewport(this.viewport , view_delta, this.num_rows , this.num_cols);
	this.draw_viewport();
	//display.update draw??
	
	
}

public void update_view_tiles(ArrayList<Tile> tiles)
{   ArrayList<Tile> rect;
	for(Tile tile : tiles)
	{
		if (this.viewport.collidepoint(tile.get_x() , tile.get_y()))
	}
}



















}
