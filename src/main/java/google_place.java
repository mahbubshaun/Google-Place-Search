
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultCaret;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class google_place {

	private JFrame frame;

	private static JTextField textField;
	public static JTable table2;
	public static JTable table;
	private JScrollPane scrollPane;
	public static String filename;
	public static DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					google_place window = new google_place();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public google_place() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 703, 653);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		// JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 188, 667, 407);
		frame.getContentPane().add(scrollPane);
		JLabel lblNewLabel = new JLabel("Google Place Search");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(160, 16, 389, 27);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblV = new JLabel("V 1");
		lblV.setForeground(Color.WHITE);
		lblV.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblV.setBounds(10, 19, 60, 19);
		frame.getContentPane().add(lblV);

		textField = new JTextField();
		textField.setBounds(252, 117, 202, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please insert a search query & try again");

				} else {

					g_api();

					JOptionPane.showMessageDialog(null, "API call successful");
				}

			}
		});

		btnSearch.setBounds(311, 155, 89, 23);
		frame.getContentPane().add(btnSearch);

		table = new javax.swing.JTable();

		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Address", "City", "State", "Zip" }));
		table.setBounds(10, 199, 677, 400);

		// jScrollPane1.setViewportView(table);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);

		JButton btnExpoortToCsv = new JButton("EXPOORT TO CSV");
		btnExpoortToCsv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// gets the current month

				DateTime dt = new DateTime();
				int hours = dt.getHourOfDay(); // gets hour of day

				int min = dt.getMinuteOfHour();

				int seconds = dt.getSecondOfMinute();

				int YEAR = dt.getYear();

				int month = dt.getMonthOfYear();

				int day = dt.getDayOfMonth();
				JFileChooser fr = new JFileChooser();
				FileSystemView fw = fr.getFileSystemView();

				File direc = fw.getDefaultDirectory();
				System.out.println(fw.getDefaultDirectory());
				try {

					filename = fw.getDefaultDirectory() + "/" + textField.getText() + "-(" + YEAR + "-" + month + "-"
							+ day + "-" + hours + "-" + min + "-" + seconds + ").xls ";
					HSSFWorkbook workbook = new HSSFWorkbook();
					HSSFSheet sheet2 = workbook.createSheet("FirstSheet");

					FileOutputStream fileOut = new FileOutputStream(filename);
					workbook.write(fileOut);
					fileOut.close();
					workbook.close();
					System.out.println("Your excel file has been generated!");

				} catch (Exception ex) {
					System.out.println(ex);
				}

				try {
					InputStream inp = new FileInputStream(filename);
					Workbook wb = WorkbookFactory.create(inp);
					Sheet sheet = wb.getSheetAt(0);
					int ctr = 1;
					Row row = null;
					Cell cell = null;

					Cell cell2 = null;

					Cell cell3 = null;
					Cell cell4 = null;

					Cell cell5 = null;

					int rows = table.getRowCount();
					//

					try {
						row = sheet.getRow(0);
						if (row == null) {
							row = sheet.createRow(0);
						}
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}
						cell2 = row.getCell(1);
						if (cell2 == null) {
							cell2 = row.createCell(1);
						}

						cell3 = row.getCell(2);
						if (cell3 == null) {
							cell3 = row.createCell(2);
						}

						cell4 = row.getCell(3);
						if (cell4 == null) {
							cell4 = row.createCell(3);
						}
						cell5 = row.getCell(4);
						if (cell5 == null) {
							cell5 = row.createCell(4);
						}

						cell.setCellValue("Name");
						cell2.setCellValue("Address");
						cell3.setCellValue("City");
						cell4.setCellValue("State");
						cell5.setCellValue("Zip");
						FileOutputStream save = new FileOutputStream(filename);
						wb.write(save);
						save.flush();
						save.close();
					} catch (Exception hgh) {

					}
					for (int i = 1; i <= rows; i++) {
						try {
							row = sheet.getRow(i);
							if (row == null) {
								row = sheet.createRow(i);
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}
							cell2 = row.getCell(1);
							if (cell2 == null) {
								cell2 = row.createCell(1);
							}

							cell3 = row.getCell(2);
							if (cell3 == null) {
								cell3 = row.createCell(2);
							}

							cell4 = row.getCell(3);
							if (cell4 == null) {
								cell4 = row.createCell(3);
							}
							cell5 = row.getCell(4);
							if (cell5 == null) {
								cell5 = row.createCell(4);
							}

							cell.setCellValue(String.valueOf(model.getValueAt(i, 0)));
							cell2.setCellValue(String.valueOf(model.getValueAt(i, 1)));
							cell3.setCellValue(String.valueOf(model.getValueAt(i, 2)));
							cell4.setCellValue(String.valueOf(model.getValueAt(i, 3)));
							cell5.setCellValue(String.valueOf(model.getValueAt(i, 4)));
							FileOutputStream save = new FileOutputStream(filename);
							wb.write(save);
							save.flush();
							save.close();
						} catch (Exception err) {

						}

					}

					JOptionPane.showMessageDialog(null, "CSV has been exported to documents directory!");
					inp.close();
					wb.close();

				} catch (Exception er) {
					er.printStackTrace();

				}
			}

		});

		btnExpoortToCsv.setBounds(508, 154, 169, 23);
		frame.getContentPane().add(btnExpoortToCsv);

		JLabel lblSearchKeyword = new JLabel("SEARCH KEYWORD");
		lblSearchKeyword.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblSearchKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchKeyword.setForeground(Color.WHITE);
		lblSearchKeyword.setBounds(252, 87, 202, 19);
		frame.getContentPane().add(lblSearchKeyword);

		JLabel label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBounds(0, -13, 617, 592);
		// frame.getContentPane().add(label);

		ImageIcon img = new ImageIcon(getClass().getResource("images/googlem.jpg"));

		frame.setIconImage(img.getImage());

		label.setIcon(new ImageIcon(getClass().getResource("images/ps.jpg")));

		frame.setResizable(true);
	}

	public static void g_api() {
		SwingWorker<Void, Void> email = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				try {
					String Url_st = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="
							+ textField.getText() + "&key=AIzaSyCUrObvFXKTi4SFxTbs_st3fF8OFSNARZ4";

					if (Url_st.contains(" "))
						Url_st = Url_st.replaceAll(" ", "%20");

					System.out.println(Url_st);

					URL url = new URL(Url_st);

					HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					conn.setRequestMethod("GET");

					int responsecode = conn.getResponseCode();

					if (responsecode != 200)
						throw new RuntimeException("HttpResponseCode: " + responsecode);
					else {

					}

					Scanner sc = new Scanner(url.openStream());
					String inline = "";
					while (sc.hasNext()) {
						inline += sc.nextLine();
					}
					System.out.println("\nJSON data in string format");
					System.out.println(inline);
					sc.close();

					JSONParser parse = new JSONParser();

					JSONObject jobj = (JSONObject) parse.parse(inline);

					JSONArray jsonarr_1 = (JSONArray) jobj.get("results");

					// Get data for Results array
					for (int i = 0; i < jsonarr_1.size(); i++) {
						try{
						// Store the JSON objects in an array
						// Get the index of the JSON object and print the values
						// as per the index
						JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(i);
						System.out.println("Elements under results array");
						System.out.println("\nName: " + jsonobj_1.get("name"));
						System.out.println("Address: " + jsonobj_1.get("formatted_address"));

						String for_add = String.valueOf(jsonobj_1.get("formatted_address"));

						String[] for_add_a = for_add.split(",");

						String address = for_add_a[0].trim();

						String city = for_add_a[1].trim();

						String state = for_add_a[2].trim();

						String[] state_ar = state.split(" ");

						String state_f = state_ar[0].trim();

						String zip = state_ar[1].trim();

						model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[] { jsonobj_1.get("name"), address, city, state_f, zip });
						}catch(Exception kj)
						{
							
						}
					}

				} catch (Exception dg) {
					dg.printStackTrace();
				}
				return null;
			}

		};
		email.execute();

	}
}
