
public class ListItem {
	private action item;
	private int ord;
	
	public ListItem(action item, int ord)
	
	{
		this.item = item;
		this.ord = ord;
	}
	public action get_item()
	{
		return item;
	}
	public int get_ord()
	{
		return ord;
	}

}
