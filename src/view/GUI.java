/**
 *
 * 
 * 
 * @company The Boeing Company
 * @author Kenneth Baldridge
 * @version
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import controller.SearchController;
import model.DataModel;
import model.SearchResult;

/**
 * 
 */
@SuppressWarnings ("serial")
public class GUI extends JFrame {

	private static final Logger LOG = Logger.getLogger(GUI.class.getName());

	private static final Dimension DIMENSION = new Dimension(550, 600);

	/** Set Font. */
	private static final Font FONT = new Font("Times New Roman", Font.PLAIN, 18);

	/** Link Listener for creating a hyperlink. */
	private final LinkListener mylinkListener = new LinkListener();

	/** Panel to display current record ID. */
	private JPanel left_Panel = new JPanel();

	private JLabel resultCountLabel = new JLabel();

	private JEditorPane resultTextPane = new JEditorPane();

	private NCRSearchMenuBar myMenubar;

	private DataModel myModel;

	private ResultsTable myTableView;

	private JPanel myDisplayPanel = new JPanel();

	public GUI(DataModel theModel) {

		super("NCR Search");

		this.myModel = theModel;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(DIMENSION);
		try {
			myTableView = new ResultsTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addComponents();

		buildResultsPane();
		this.pack();
		SetWindowPosition.setPosition(this);
		this.setVisible(true);

	}

	private void addComponents() {

		myMenubar = new NCRSearchMenuBar();

		this.setJMenuBar(myMenubar);

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());

		left_Panel.add(resultCountLabel);
		/** Panel to display buttons. */
		JPanel center_Panel = new JPanel();
		/** Panel to display current index. */
		JPanel right_Panel = new JPanel();
		left_Panel.setBackground(Color.GREEN);
		northPanel.add(left_Panel, BorderLayout.WEST);

		center_Panel.setBackground(Color.GRAY);
		// center_Panel.add(buildFilter());
		northPanel.add(center_Panel, BorderLayout.CENTER);

		right_Panel.setBackground(Color.YELLOW);
		northPanel.add(right_Panel, BorderLayout.EAST);

		this.add(northPanel, BorderLayout.NORTH);

		final JScrollPane spMyTable = new JScrollPane(buildDisplayPanel(),
		        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(spMyTable, BorderLayout.CENTER);

	}

	// private Component buildFilter() {
	//
	// // TODO Auto-generated method stub
	// return null;
	// }

	private JPanel buildDisplayPanel() {

		myDisplayPanel.setBackground(Color.BLUE);
		myDisplayPanel.setLayout(new BoxLayout(myDisplayPanel, BoxLayout.Y_AXIS));

		return myDisplayPanel;
	}

	public void occupyWithResults(List<SearchResult> theResults) {

		String results = "";
		clearResults();
		resultTextPane.setText("");
		setResultcount(theResults.size());

		for (int i = 0; i < theResults.size(); i++) {

			String partNumber = theResults.get(i).getPartNumber();
			String NCRNumber = theResults.get(i).getNCRNumber();
			String fileLocation = theResults.get(i).getFileLocation();
			String NCRText = theResults.get(i).getText();

			results += partNumber + "  ---  " + NCRNumber + "  ---  "
			        + buildPathString(fileLocation) + "  <br>  " + NCRText
			        + "<br><br>";

		}

		resultTextPane.setText(results);
		myDisplayPanel.add(resultTextPane);
		resultTextPane.setCaretPosition(0);
		myDisplayPanel.revalidate();
		myModel.clearResultsList();
	}

	public void occupyWithResultsTest(List<SearchResult> theResults) {

		String results = "";
		clearResults();
		setResultcount(theResults.size());

		// myTableView.buildResultsTable();

		for (int i = 0; i < theResults.size(); i++) {

			String partNumber = theResults.get(i).getPartNumber();
			String NCRNumber = theResults.get(i).getNCRNumber();
			String fileLocation = theResults.get(i).getFileLocation();
			String NCRText = theResults.get(i).getText();

			results += partNumber + "  ---  " + NCRNumber + "  ---  "
			        + buildPathString(fileLocation) + "  <br>  " + NCRText
			        + "<br><br>";

		}

		resultTextPane.setText(results);
		myDisplayPanel.add(resultTextPane);
		resultTextPane.setCaretPosition(0);
		myDisplayPanel.revalidate();
		myModel.clearResultsList();
	}

	private void buildResultsPane() {

	
		resultTextPane.setEditable(false);
		resultTextPane.setContentType("text/html");
		resultTextPane.setFont(FONT);
		resultTextPane.setForeground(Color.WHITE);
		resultTextPane.setBorder(
		        BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
		resultTextPane.addHyperlinkListener(this.mylinkListener);

	}

	public void clearResults() {

		for (int i = 0; i < myDisplayPanel.getComponentCount(); i++) {
			myDisplayPanel.remove(i);
		}

	}

	/*
	 * Builds the url to a file if it exists.
	 *
	 * @param thePath The path to the a file.
	 * 
	 * @return fileLink The html embedded string.
	 */
	private static String buildPathString(final String thePath) {

		String hyperLink = "";

		String fileLink = thePath;

		if (fileLink.contains("\\") || fileLink.contains("/")) {
			fileLink = fileLink.replaceAll("(\\r|\\n|\\r\\n|\\n\\r)+", "");
			fileLink = fileLink.replace("\\", "/").replace(" ", "%20");

		}

		hyperLink = "<a href=\"file:///" + fileLink + "\">\"File Location\"</a>";

		return hyperLink;
	}

	/**
	 * Register the controller as the listener to the search field.
	 * 
	 * @param listener
	 */
	public void registerListener(SearchController theListener) {

		myMenubar.getSearchField().addActionListener(theListener);

	}

	public void setResultcount(int theCount) {

		resultCountLabel.setText(Integer.toString(theCount));
	}

	/**
	 * Turns the url in the jtextfield into a clickable url.
	 */
	private class LinkListener implements HyperlinkListener {

		@Override
		public void hyperlinkUpdate(final HyperlinkEvent theEvent) {

			if (theEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED
			        && Desktop.isDesktopSupported()) {

				try {
					Desktop.getDesktop().browse(theEvent.getURL().toURI());
				} catch (final IOException e1) {

					LOG.log(Level.SEVERE, e1.toString(), e1);
				} catch (final URISyntaxException e1) {

					LOG.log(Level.SEVERE, e1.toString(), e1);
				}

			}

		}

	}

}
