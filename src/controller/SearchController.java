package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.DataModel;
import view.GUI;
import view.ResultsTable;

@SuppressWarnings ("serial")
public class SearchController extends Component implements ActionListener {

	private static final String SEARCHLOCATION = "C:\\Users\\XXXX";

	private DataModel myModel;

	private GUI myView;

	private String mySearchTerms;

	public SearchController(DataModel theModel, GUI theView) {

		this.myModel = theModel;
		this.myView = theView;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		mySearchTerms = e.getActionCommand().toString();

		myModel.search(mySearchTerms, SEARCHLOCATION); // CHANGE HERE FOR DIFF
		                                               // FOLDERS

		myView.occupyWithResults(myModel.getResultsList());

		myModel.clearResultsList();

	}

}
