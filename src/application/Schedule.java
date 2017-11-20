package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Schedule {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty descr;

	public Schedule(Integer id, String descr) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.descr = new SimpleStringProperty(descr);
	}

	public Integer getId() {
		return id.get();
	}

	public String getDescr() {
		return descr.get();
	}




}
