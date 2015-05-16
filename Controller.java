import processing.core.*;

public class Controller {

	private static final int KEY_DELAY = 400;
	private static final int KEY_INTERVAL = 100;

	private static final int TIMER_FREQUENCY = 100;

	public static int [ ] keyPressed (event)
{
	int [ ] value; 
	int x_delta = 0;
	int y_delta = 0;

	switch(key)
	{
	case 'w':
	{
		y_delta -= 1;
	}
	case 's':
	{
		y_delta +=1;	
	}
	case'a':
	{
		x_delta -=1;
	}
	case'd':
	{
		x_delta+=1;
	}
			   
}
value = new int[] {x_delta,y_delta} ;

return  value;

}

/*		
public static void handle_timer_event(WorldModel world, WorldView view)
{
	rects = world.update_on_time( pygame.time.get_ticks())
    view.update_view_tiles( rects)
}
*/

			
public static void  handle_keydown(WorldView view, event)
{
  int [] view_delta = on_keydown(event);
 view.update_view(view_delta);
}

public static void activity_loop(WorldView view,WorldModel world)
{
 pygame.key.set_repeat(KEY_DELAY, KEY_INTERVAL)
 pygame.time.set_timer(pygame.USEREVENT, TIMER_FREQUENCY)

 while 1:
 for event in pygame.event.get():
 if event.type == pygame.QUIT:
return
elif event.type == pygame.USEREVENT:
handle_timer_event(world, view)
 elif event.type == pygame.MOUSEMOTION:
 handle_mouse_motion(view, event)
elif event.type == pygame.KEYDOWN:
			            handle_keydown(view, event)
}




}
