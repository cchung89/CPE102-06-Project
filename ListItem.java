import java.util.function.*;

public class ListItem {
	private LongConsumer item;
	private long ord;
	
	public ListItem(LongConsumer item, long ord)
	
	{
		this.item = item;
		this.ord = ord;
	}
	
	public LongConsumer get_item()
	{
		return item;
	}
	
	public long get_ord()
	{
		return ord;
	}

	public boolean equals(ListItem other)
	{
		return this.item == other.item && this.ord == other.ord;
	}
}
