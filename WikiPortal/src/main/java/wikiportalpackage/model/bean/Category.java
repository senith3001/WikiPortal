package wikiportalpackage.model.bean;

public class Category {
	protected String CategoryID;
	protected String name;
	
	public Category() {
		
	}
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category(String categoryID, String name) {
		super();
		CategoryID = categoryID;
		this.name = name;
	}
	
	public Category(String name) {
		super();
		
		this.name = name;
	}
}
