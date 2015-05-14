
public class ListItem {
	private Function item;
	private int ord;
	
	public ListItem(Function item, int ord)
	
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
