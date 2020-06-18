package ntou.cs.wbse.model;

import java.io.IOException;
import java.net.URISyntaxException;

import ntou.cs.wbse.model.WeatherHandler;

public class WeatherHandlerTest {
	public static void main(String[] args) {
		try {
			WeatherHandler handler = new WeatherHandler();
			handler.initialize();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}
}
