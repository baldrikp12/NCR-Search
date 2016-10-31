
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Constructs the menu bar. <br>
 * <br>
 * 
 * @company The Boeing Company
 * @author Kenneth Baldridge
 * @version 1.0
 * 
 */
@SuppressWarnings ("serial")
public class NCRSearchMenuBar extends JMenuBar {

	/** Copyright symbol. */
	public static final String COPYRIGHT = "\u00a9";

	private JTextField searchField;

	public NCRSearchMenuBar() {

		super();
		this.add(buildHelpMenu());
		this.add(Box.createGlue());

		buildSearchArea();
		this.add(Box.createHorizontalStrut(20));
	}

	private void buildSearchArea() {

		this.add(new JLabel(" Search: "));
		searchField = new JTextField(20);
		searchField.setBorder(null);
		searchField.setOpaque(false);
		searchField.setBorder(
		        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		searchField.setMaximumSize(searchField.getPreferredSize());

		this.add(searchField);

	}

	/**
	 * Returns the help menu item.
	 * 
	 * @return theHelpMenu
	 */
	private JMenu buildHelpMenu() {

		final JMenu theHelpMenu = new JMenu("Help");
		theHelpMenu.setMnemonic(KeyEvent.VK_H);

		final JMenuItem AboutItem = new JMenuItem("About NCR Search Application",
		        KeyEvent.VK_A);
		AboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						JOptionPane.showMessageDialog(null,
		                        "COPYRIGHT " + COPYRIGHT
		                                + " 2016 The Boeing Company\n"
		                                + "Author: Kenneth Baldridge, AMS ME\n"
		                                + "Email: XXXX\n\n"
		                                + "NCR Search aids in locating previously saved "
		                                + "NCR tags for future reference.",
		                        "About", JOptionPane.INFORMATION_MESSAGE);
					}
				});
			}
		});

		theHelpMenu.add(AboutItem);

		return theHelpMenu;

	}

	public JTextField getSearchField() {

		return searchField;
	}

}
