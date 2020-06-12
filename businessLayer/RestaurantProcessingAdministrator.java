package businessLayer;

public interface RestaurantProcessingAdministrator {
	
	/**
	* @post menuItem().count() == oldMenuItem().count() + 1
	*/
	public void addNewMenuItem(MenuItem menuItem);
	
	/**
	* @pre !menuItem().empty()
	*/
	
	public void editMenuItem(MenuItem menuItem, MenuItem menuItemModified);
	
	
	/**
	* @pre !menuItem().empty()
	* @post menuItem().count() == oldMenuItem().count() + 1
	*/
	
	public void deleteMenuItem(MenuItem menuItem);
	
	
}
