import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.csvreader.CsvWriter;

public class main3 extends JFrame implements ActionListener {
	static boolean primeraFila = false;
	JLabel tJSON, tCSV, tError; // etiqueta o texto no editable
	JTextField cJSON, cCSV; // caja de texto, para insertar datos
	JButton bJSON, bCSV, bConvertir;

	JSONParser parser = new JSONParser();
	JSONObject jsonObject;
	JSONArray arr;
	CsvWriter writer;

	public main3() {
		super(); // usamos el contructor de la clase padre JFrame
		configurarVentana(); // configuramos la ventana
		inicializarComponentes(); // inicializamos los atributos o componentes
	}

	private void configurarVentana() {
		this.setTitle("Conversor JSON a CSV");
		this.setSize(420, 200);
		this.setLocationRelativeTo(null); // centramos la ventana en la pantalla
		this.setLayout(null); // no usamos ningun layout, solo asi podremos dar
								// posiciones a los componentes
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void inicializarComponentes() {
		// creamos los componentes
		tJSON = new JLabel();
		cJSON = new JTextField();
		bJSON = new JButton();
		tCSV = new JLabel();
		cCSV = new JTextField();
		bCSV = new JButton();
		bConvertir = new JButton();
		tError = new JLabel();

		// configuramos los componentes
		tJSON.setText("Archivo JSON:"); // colocamos un texto a la etiqueta
		tJSON.setBounds(10, 10, 100, 30); // colocamos posicion y tamanio al
											// texto (x, y, ancho, alto)
		cJSON.setBounds(100, 10, 200, 30); // colocamos posicion y tamanio a la
											// caja (x, y, ancho, alto)
		bJSON.setText("Abrir"); // colocamos un texto al boton
		bJSON.setBounds(300, 10, 100, 30); // colocamos posicion y tamanio al
											// boton (x, y, ancho, alto)
		bJSON.addActionListener(this); // hacemos que el boton tenga una accion
										// y esa accion estara en esta clase

		tCSV.setText("Archivo CSV:"); // colocamos un texto a la etiqueta
		tCSV.setBounds(10, 60, 100, 30); // colocamos posicion y tamanio al
											// texto (x, y, ancho, alto)
		cCSV.setBounds(100, 60, 200, 30); // colocamos posicion y tamanio a la
											// caja (x, y, ancho, alto)
		bCSV.setText("Guardar"); // colocamos un texto al boton
		bCSV.setBounds(300, 60, 100, 30); // colocamos posicion y tamanio al
											// boton (x, y, ancho, alto)
		bCSV.addActionListener(this); // hacemos que el boton tenga una accion y
										// esa accion estara en esta clase

		bConvertir.setText("CONVERTIR");
		bConvertir.setBounds(150, 95, 100, 30);
		bConvertir.addActionListener(this);
		tError.setForeground(Color.red);
		tError.setText("");
		tError.setBounds(145, 130, 140, 30);

		this.add(tJSON);
		this.add(cJSON);
		this.add(bJSON);
		this.add(tCSV);
		this.add(cCSV);
		this.add(bCSV);
		this.add(bConvertir);
		this.add(tError);
	}

	public static void main(String[] args) {

		main3 m = new main3();
		m.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == bJSON) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JSON", "json");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				cJSON.setText(chooser.getSelectedFile().toString());
			}
			tError.setText("");
		} else if (arg0.getSource() == bCSV) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV",
					"csv");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (!chooser.getSelectedFile().toString().contains(".csv"))
					cCSV.setText(chooser.getSelectedFile().toString() + ".csv");
				else
					cCSV.setText(chooser.getSelectedFile().toString());
			}
			tError.setText("");
		} else if (arg0.getSource() == bConvertir) {
			System.out.println("sdfds");
			if (!cJSON.getText().isEmpty() && !cCSV.getText().isEmpty()) {
				try {
					arr = (JSONArray) parser
							.parse(new FileReader(cJSON.getText()));
					writer = new CsvWriter(cCSV.getText());

					writer.setDelimiter(';');
					writer.write("CODIGO* (Texto)");
					writer.write("NOMBRE* (Texto)");
					writer.write("DESCRIPCION (Texto)");
					writer.endRecord();

					

					for (int i = 0; i < arr.size(); i++) {
						jsonObject = (JSONObject) arr.get(i);

						writer.write(new String(((String) jsonObject.get("CODIGO* (Texto)")).getBytes(),"UTF-8"));

						writer.write(new String(((String) jsonObject.get("NOMBRE* (Texto)")).getBytes(),"UTF-8"));

						writer.write(new String(((String) jsonObject.get("DESCRIPCION (Texto)")).getBytes(),"UTF-8"));

						writer.endRecord();
					}
					writer.close();
					tError.setForeground(Color.black);
					tError.setText("Conversion realizada");
					System.out.println("Terminado");
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else{
				tError.setForeground(Color.red);
				tError.setText("Rellene los campos");
			}
		}

	}

}
