import java.util.*;
import java.util.function.*;


public class OrderedList {
	private ArrayList<ListItem> list;
	
	public OrderedList()
	{
		this.list = new ArrayList<ListItem>();
	}
	
	public void insert(LongConsumer item, long ord)
	{
		int size = list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).get_ord() < ord)
		{
			idx++;
		}
		
		this.list.add(idx, new ListItem(item, ord));
	}

	public void remove(Object item)
	{
		int size = list.size();
		int idx = 0;
		while(idx<size && this.list.get(idx).get_item() != item)
		{
			idx++;
		}
		if (idx < size)
		{
			this.list.remove(idx) ;
		}
	}
	
	public ListItem head()
	{
		if (this.list != null)
		{
		return this.list.get(0);
		}	
			return null;
	}
	
	public ListItem pop()
	{
		if (this.list != null)
		{	
			ListItem popped = this.list.get(0);
			this.list.remove(0);
			return popped;
		}
		return null;
	}

}
