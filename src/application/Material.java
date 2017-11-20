package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Material {
	private final SimpleIntegerProperty Qty;
	private final SimpleStringProperty Unit;
	private final SimpleStringProperty Description;
	private final SimpleIntegerProperty UnitCost;
	private final SimpleIntegerProperty TotalCost
	;
	public Material(Integer Qty,  String Unit, String Description, Integer UnitCost,
			Integer TotalCost) {
		super();
		this.Qty = new SimpleIntegerProperty(Qty);
		this.Unit = new SimpleStringProperty(Unit);
		this.Description = new SimpleStringProperty(Description);
		this.UnitCost = new SimpleIntegerProperty(UnitCost);
		this.TotalCost = new SimpleIntegerProperty(TotalCost);
	}

	public Integer getQty() {
		return Qty.get();
	}

	public String getUnit() {
		return Unit.get();
	}

	public String getDescription() {
		return Description.get();
	}

	public Integer getUnitCost() {
		return UnitCost.get();
	}

	public int getTotalCost() {
		return TotalCost.get();
	}



}
