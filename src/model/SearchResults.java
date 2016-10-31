package model;


public class SearchResult {
	
	private String myPartNumber;
	private String myNCRNumber;
	private String myFileLocation;
	private String myText;
	
	public SearchResult(String thePartNumber, String theNCRNumber, String theFileLocation, String theText) {
		
		this.myPartNumber = thePartNumber;
		this.myNCRNumber = theNCRNumber;
		this.myFileLocation = theFileLocation;
		this.myText = theText;
	}
	
	public String getPartNumber() {
		return myPartNumber;
	}
	
	public String getNCRNumber() {
		return myNCRNumber;
	}
	
	public String getFileLocation() {
		return myFileLocation;
	}
	
	public String getText() {
		return myText;
	}

}
