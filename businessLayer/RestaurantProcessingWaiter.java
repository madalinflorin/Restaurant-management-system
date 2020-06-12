package businessLayer;

import java.util.ArrayList;

public interface RestaurantProcessingWaiter {

	/**
	* @post orderItem().count() == oldOrderItem().count() + 1
	*/
	
	public void addNewOrderItem(Order a,ArrayList<MenuItem> b);
	
}
