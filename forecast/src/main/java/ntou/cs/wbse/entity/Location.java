package ntou.cs.wbse.entity;

import java.util.List;

public class Location {
	private String locationName;
	private List<WeatherElement> weatherElement;
	private String note;
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public List<WeatherElement> getWeatherElement() {
		return weatherElement;
	}
	public void setWeatherElement(List<WeatherElement> weatherElement) {
		this.weatherElement = weatherElement;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
