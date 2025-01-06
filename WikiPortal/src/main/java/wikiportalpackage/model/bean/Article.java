package wikiportalpackage.model.bean;

public class Article {
	protected String ArticleID;
	protected String name;
	protected String Description;
	protected String Date;
	protected boolean Display;
	protected String CategoryID;
public Article() {
		
	}
public Article(String articleID, String name, String description, String date, boolean display, String categoryID) {
	super();
	ArticleID = articleID;
	this.name = name;
	Description = description;
	Date = date;
	Display = display;
	CategoryID = categoryID;
}
public String getArticleID() {
	return ArticleID;
}
public void setArticleID(String articleID) {
	ArticleID = articleID;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return Description;
}
public void setDescription(String description) {
	Description = description;
}
public String getDate() {
	return Date;
}
public void setDate(String date) {
	Date = date;
}
public boolean isDisplay() {
	return Display;
}
public void setDisplay(boolean display) {
	Display = display;
}
public String getCategoryID() {
	return CategoryID;
}
public void setCategoryID(String categoryID) {
	CategoryID = categoryID;
}

public Article(String ArticleID, String name, String description, String categoryID, String date) {
	this.ArticleID = ArticleID;
	this.name = name;
	this.Description = description;
	this.Date = date;
}
public Article( String name, String description, String date, boolean display, String categoryID) {
	this.name = name;
	this.Description = description;
	this.Date = date;
	this.Display = display;
	this.CategoryID = categoryID;
}
	

}

