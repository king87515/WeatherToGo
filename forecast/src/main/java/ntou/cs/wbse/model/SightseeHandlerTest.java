package ntou.cs.wbse.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class SightseeHandlerTest {
	public static void main(String[] args) {
		try {
			SightseeHandler handler = new SightseeHandler();
			handler.initialize();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}
}
