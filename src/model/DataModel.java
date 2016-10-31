package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class DataModel {

	private List<SearchResult> myResults = new ArrayList<SearchResult>();

	private String mySearchTerms;

	private String myRootSearchDirectory;

	public DataModel() {

		mySearchTerms = "";
		myRootSearchDirectory = "";

	}

	public List<SearchResult> getResultsList() {

		return myResults;
	}

	public void search(String theSearchTerms, String theSearchDirectory) {

		mySearchTerms = theSearchTerms;
		myRootSearchDirectory = theSearchDirectory;

		File[] files = new File(myRootSearchDirectory).listFiles();

		showFiles(files);

	}

	/**
	 * Iterate through the files under the selected root directory.
	 * 
	 * @param theFiles
	 */
	private void showFiles(File[] theFiles) {

		for (File file : theFiles) {

			if (file.isDirectory()) {

				showFiles(file.listFiles()); // Calls same method again.

			} else {
				if (isNCRTag(file.getName())) {
					extractText(file);
				}
			}

		}
	}

	/**
	 * Extracts text from pdf file and checks it for any search terms.
	 * 
	 * @param theFile
	 */
	private void extractText(File theFile) {

		String theNCRNumber = theFile.getName();
		PDDocument pdf;
		String plainTextPDF = "";
		try {
			pdf = PDDocument.load(theFile);
			PDFTextStripper stripper = new PDFTextStripper();
			plainTextPDF = stripper.getText(pdf);
			pdf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (StringUtils.containsIgnoreCase(plainTextPDF, mySearchTerms)) {
			extractData(plainTextPDF, theNCRNumber, theFile.getParent());

		}

	}

	private void extractData(String thePlainTextPDF, String theNCRNumber,
	        String theFileLocation) {

		// ------------- locate part number ---------------//
		int count = 1;
		String partNumber = "(?<=Company\\r\\n).*";
		String partNumberMatch = "";

		Pattern pattPartNum = Pattern.compile(partNumber,
		        Pattern.CASE_INSENSITIVE & Pattern.MULTILINE);
		Matcher mPartNum = pattPartNum.matcher(thePlainTextPDF);

		while (mPartNum.find() && count > 0) {
			partNumberMatch = mPartNum.group();
			count--;
		}

		// ---------------- locate search term ------------------//
		String searchTerm = "((?:[a-zA-Z'-]+[^a-zA-Z'-]+){0,10}(?i)"
		        + mySearchTerms + "(?:[^a-zA-Z'-]+[a-zA-Z'-]+){0,10})";
		String searchTermMatch = "";

		Pattern pattSearchTerm = Pattern.compile(searchTerm,
		        Pattern.CASE_INSENSITIVE & Pattern.MULTILINE);
		Matcher mSearchTerm = pattSearchTerm.matcher(thePlainTextPDF);

		while (mSearchTerm.find()) {

			searchTermMatch = mSearchTerm.group();

		}

		// ---------------- create result object ------------------//
		myResults.add(new SearchResult(partNumberMatch, theNCRNumber,
		        theFileLocation, searchTermMatch));

	}

	public void clearResultsList() {

		if (myResults.size() > 0) {
			myResults.clear();
		}

	}

	/**
	 * 
	 * Checks if file is right kind ("^N")
	 * 
	 * @param name
	 * @return
	 */
	private boolean isNCRTag(String name) {

		return name.substring(0, 1).equals("N") ? true : false;
	}

}
