package view;

import java.awt.Rectangle;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ResultsTable {

	/** The Table. */
	private final JTable myTable;

	/** Table Model. */
	private final transient DefaultTableModel tableModel;

	/**
	 * Constructs the table view.
	 *
	 * @param theQueries
	 *            The current queries object.
	 * @param theRows
	 *            The number of rows to build.
	 * @param theColumns
	 *            The number of columns to build.
	 * @throws SQLException
	 *             Throws SQLException up.
	 */
	@SuppressWarnings ("serial")
	public ResultsTable() throws SQLException {

		super();

		this.tableModel = new DefaultTableModel() {

			@Override
			public Class<?> getColumnClass(final int colIndex) {

				final Object value = this.getValueAt(0, colIndex);
				return (value == null ? Object.class : value.getClass());
			}

			@Override
			public boolean isCellEditable(final int row, final int column) {

				return false;

			}

		};

		this.myTable = new JTable(this.tableModel);

		setupTable();

	}

	/**
	 * Sets up the look of the table.
	 *
	 * @throws SQLException
	 */
	private void setupTable() throws SQLException {

		buildHeaders();

		buildTable();

		// myTable.setAutoCreateRowSorter(true);

		this.myTable.setEnabled(false);
		this.myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		renderSelectedRow();

	}

	/**
	 * Builds the column headers of the table.
	 */
	private void buildHeaders() {

		this.tableModel.setColumnCount(3);

		final DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
		TableColumn column;

		column = new TableColumn();
		column.setModelIndex(0);
		column.setHeaderValue("Part#");
		column.setPreferredWidth(55);
		columnModel.addColumn(column);

		column = new TableColumn();
		column.setModelIndex(1);
		column.setHeaderValue("File Location");
		column.setPreferredWidth(40);
		columnModel.addColumn(column);

		column = new TableColumn();
		column.setModelIndex(2);
		column.setHeaderValue("Discrepancy Text");
		column.setPreferredWidth(40);
		columnModel.addColumn(column);

		this.myTable.setColumnModel(columnModel);
		this.myTable.getTableHeader().setReorderingAllowed(false);

	}

	/**
	 * Builds the table based off the resultSet from the Table Query. If new
	 * records have been added since initial launch new empty rows are added
	 * then populated.
	 *
	 * @throws SQLException
	 *             Throws SQLException up.
	 */
	public void buildTable() throws SQLException {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				ResultsTable.this.tableModel.setRowCount(1000);

				for (int row = 0; row < 1000; row++) {

					ResultsTable.this.myTable.setValueAt(Boolean.FALSE, row, 0);

					final String ID = "test String ID";
					ResultsTable.this.myTable.setValueAt(ID, row, 1);

					for (int column = 1; column < 3; column++) {

						String data = "test String Data" + row + "   " + column;

						ResultsTable.this.myTable.setValueAt(data, row, column);

					}
				}

				ResultsTable.this.tableModel.fireTableDataChanged();

			}
		});

	}

	/**
	 * Builds the table based off the resultSet from the Search Query.
	 *
	 * @param theSearch
	 *            String representation of what to search for.
	 * @throws SQLException
	 *             Throws SQLException up.
	 */
	public void buildResultsTable() throws SQLException {

		buildTable();

	}

	/**
	 * Clears the table so new records can be displayed.
	 */
	public void clearTable() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < ResultsTable.this.myTable.getRowCount(); i++) {
					for (int j = 0; j < ResultsTable.this.myTable
		                    .getColumnCount(); j++) {
						ResultsTable.this.myTable.setValueAt("", i, j);
					}
				}
			}
		});
	}

	/** Highlights current row. */
	public void renderSelectedRow() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				isCellVisible(ResultsTable.this.myTable,
		                ResultsTable.this.myTable.getSelectedRow(), 0);
			}
		});

	}

	/**
	 * Figure out if cell is visible.
	 */
	private static void isCellVisible(final JTable table, final int row,
	        final int col) {

		final Rectangle rect = table.getCellRect(row, col, true);
		table.scrollRectToVisible(rect);

	}

	/** Returns the JTable. */
	public JTable getMyTable() {

		return this.myTable;
	}

}
