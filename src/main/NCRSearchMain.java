/**
 *
 * 
 * @company The Boeing Company
 * @author Kenneth Baldridge
 * @version
 * 
 */
package main;

import java.awt.EventQueue;

import controller.SearchController;
import model.DataModel;
import view.GUI;

/**
 * 
 * 
 */
public class NCRSearchMain {

	private NCRSearchMain() {
		// Avoids use of the default constructor.
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				DataModel model = new DataModel();
				GUI view = new GUI(model);
				SearchController controller = new SearchController(model, view);

				view.registerListener(controller);

			}

		});

	}

}
