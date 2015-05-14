import java.util.*;
public class OrderedList {
	private ArrayList<ListItem> list = new ArrayList<ListItem>() ;
	
	public OrderedList()
	{
		this.list = list ;
	}
	
	public void insert( Function item, int ord )
	{
		int size = list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).get_ord() < ord)
		{
			idx+=1;
		}
		
		this.list.add(idx, ListItem(item,ord));
	}

	public void remove(Function item)
	{
		int size = list.size();
		int idx = 0;
		while(idx<size && this.list.get(idx).get_item() != item)
		{
			idx += 1;
		}
		
		if (idx<size)
		{
			this.list.remove(idx) ;
		}
	}
	
	public ListItem head()
	{
		if (!this.list.isEmpty())
		{
		return this.list.get(0);
	
		}	
		
		else
		{
			return null;
		}
		
	
	}
	
	public ListItem pop()
	{
		if (!this.list.isEmpty())
		{
			return this.list.pop(0);
		}
	}

}
