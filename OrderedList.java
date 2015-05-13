import java.util.*;
public class OrderedList {
	private ArrayList<ListItem> list = new ArrayList<ListItem>() ;
	
	public OrderedList()
	{
		this.list = list ;
	}
	
	public void insert(actions item, int ord )
	{
		int size = list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).get_ord() < ord)
		{
			idx+=1;
		}
		
		this.list.subList(idx,idx) = [ListItem(item,item)] ;
	}

	public void remove(actions item)
	{
		int size = list.size();
		int idx = 0;
		while(idx<size && this.list.get(idx).get_item() != item)
		{
			idx += 1;
		}
		
		if (idx<size)
		{
			this.list.subList(idx, idx+1) = [] ;
		}
	}
	
	public ListItem head()
	{
		return this.list.get(0) if (this.list) {;}else {null;} ;
	}
	
	public ListItem pop()
	{
		if (this.list)
		{
			return this.list.pop(0)
		}
	}

}
