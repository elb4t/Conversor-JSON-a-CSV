import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.csvreader.CsvWriter;

public class main {
	static boolean primeraFila = false;

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		CsvWriter writer = new CsvWriter("informe.csv");

		try {
			JSONArray arr = (JSONArray) parser.parse(new FileReader(
					"prueba.json"));
			
			jsonObject = (JSONObject) arr.get(0);
			
			
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
		} catch (FileNotFoundException e) {
			// manejo de error
		} catch (IOException e) {
			// manejo de error
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
		System.out.println("Terminado");

	}

}
