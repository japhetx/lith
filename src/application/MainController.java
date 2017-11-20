package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

	@FXML
	PieChart chart;

	ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
            new PieChart.Data("Stage 1", 147721.00),
            new PieChart.Data("Stage 2", 125641.50),
            new PieChart.Data("Stage 3", 68691.00),
            new PieChart.Data("Stage 4", 76623.00),
            new PieChart.Data("Stage 5", 55237.00),
            new PieChart.Data("Stage 6", 113320.00)
            );

	@FXML
	ListView<String> lstviewProject;

	ObservableList<String> listProject = FXCollections.observableArrayList("Two-Storey Subd.,"
			+ " w/ Balcony(90 sqm. Bldg., 72sqm. Lot)");

	@FXML
	Spinner<Integer> spnrStage;

	@FXML
	ComboBox<String> cmbStageSchedule;

	ObservableList<String> listStageSchedule =
		    FXCollections.observableArrayList(
		        "Stage 1",
		        "Stage 2",
		        "Stage 3"
		    );


	@FXML
	ComboBox<String> cmbStageMaterial;

	ObservableList<String> listStageMaterial =
		    FXCollections.observableArrayList(
		        "Stage 1",
		        "Stage 2",
		        "Stage 3"
		    );

	@FXML
	TableView<Schedule> tblSchedule;

	@FXML
	TableColumn<Schedule, Integer> clmNumberSchedule;

	@FXML
	TableColumn<Schedule, String> clmDescSchedule;

	ObservableList<Schedule> listSchedule = FXCollections.observableArrayList(
			new Schedule (1, "Staking & Layout / Provision of barracks"),
			new Schedule (2, "Excavation")
			);

	@FXML
    private TableView<Material> tblMaterial;

    @FXML
    private TableColumn<Material, Integer> clmQtyMaterial;

    @FXML
    private TableColumn<Material, String> clmUnitMaterial;

    @FXML
    private TableColumn<Material, String> clmDescMaterial;

    @FXML
    private TableColumn<Material, Integer> clmUnitCostMaterial;

    @FXML
    private TableColumn<Material, Integer> clmTotalCostMaterial;

    ObservableList<Material> listMaterial = FXCollections.observableArrayList(
			new Material (1, "Dondon", "Leadman", 238, 32130),
			new Material (2, "Bert", "Carpenter", 750, 9000)
			);

    @FXML
	ListView<String> lstMaterial;

	ObservableList<String> listCostbook = FXCollections.observableArrayList("Cement",
			"Sand (1 truckload)", "3/4 Gravel (1 truckload)", "16mmo def. bar",
			"12mmo def. bar");


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chart.setData(pieChartData);
		chart.setTitle("Project Stages");

		lstviewProject.setItems(listProject);

		int initialValue = 1;

		SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
		spnrStage.setValueFactory(valueFactory);

		cmbStageSchedule.setItems(listStageSchedule);
		cmbStageMaterial.setItems(listStageMaterial);

		clmNumberSchedule.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("id"));
		clmDescSchedule.setCellValueFactory(new PropertyValueFactory<Schedule, String>("descr"));
		tblSchedule.setItems(listSchedule);

		clmQtyMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("Qty"));
		clmUnitMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("Unit"));
		clmDescMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("Description"));
		clmUnitCostMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("UnitCost"));
		clmTotalCostMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("TotalCost"));
		tblMaterial.setItems(listMaterial);

		lstMaterial.setItems(listCostbook);
	}



}
