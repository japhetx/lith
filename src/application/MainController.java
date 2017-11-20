package application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class MainController implements Initializable {

	//pie chart
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

	//list project
	@FXML
	ListView<String> lstviewProject;

	ObservableList<String> listProject = FXCollections.observableArrayList();

	//spinner stage
	@FXML
	Spinner<Integer> spnrStage;

	//combo sched
	@FXML
	ComboBox<String> cmbStageSchedule;

	ObservableList<String> listStageSchedule =
		    FXCollections.observableArrayList(
		        "Stage 1",
		        "Stage 2",
		        "Stage 3"
		    );

	//combo material
	@FXML
	ComboBox<String> cmbStageMaterial;

	ObservableList<String> listStageMaterial =
		    FXCollections.observableArrayList(
		        "Stage 1",
		        "Stage 2",
		        "Stage 3"
		    );

	//table schedule
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

	//table material
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

    //listview material
    @FXML
	ListView<String> lstMaterial;

	ObservableList<String> listCostbook = FXCollections.observableArrayList("Cement",
			"Sand (1 truckload)", "3/4 Gravel (1 truckload)", "16mmo def. bar",
			"12mmo def. bar");

	//Menu

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuDb;

    @FXML
    private MenuItem menuAbout;

    //Database
  	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  	static final String DB_URL = "jdbc:mysql://localhost/systemedres";
  	static final String USER = "root";
  	static final String PASS = "";

  	//My Project buttons
    @FXML
    private Button btnAddProject;

    @FXML
    private Button btnDeletProject;

    @FXML
    private Button btnEditProject;

    @FXML
    private Button btnSelectProject;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//pie
		chart.setData(pieChartData);
		chart.setTitle("Project Stages");

		// list project
		lstviewProject.setItems(listProject);

		// spinner
		int initialValue = 1;

		SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
		spnrStage.setValueFactory(valueFactory);

		//combo sched material
		cmbStageSchedule.setItems(listStageSchedule);
		cmbStageMaterial.setItems(listStageMaterial);

		// table schedule
		clmNumberSchedule.setCellValueFactory(new PropertyValueFactory<Schedule, Integer>("id"));
		clmDescSchedule.setCellValueFactory(new PropertyValueFactory<Schedule, String>("descr"));
		tblSchedule.setItems(listSchedule);

		// table material
		clmQtyMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("Qty"));
		clmUnitMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("Unit"));
		clmDescMaterial.setCellValueFactory(new PropertyValueFactory<Material, String>("Description"));
		clmUnitCostMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("UnitCost"));
		clmTotalCostMaterial.setCellValueFactory(new PropertyValueFactory<Material, Integer>("TotalCost"));
		tblMaterial.setItems(listMaterial);

		// list costbook
		lstMaterial.setItems(listCostbook);

		// list project from database
		try {
			Connection conn = null;
			Statement stmt = null;

			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();

			String sql = "SELECT * FROM projectinfo";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				lstviewProject.getItems().add(rs.getString("ProjName"));
			}


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//List Project Select 0 at start
		lstviewProject.getSelectionModel().select(0);

	}

	// menu Exit
	public void clickExit(ActionEvent event) throws Exception {
		Platform.exit();
	}

	// menu Db
	public void clickDb(ActionEvent event) throws Exception {

		try {
			Connection conn = null;
			Statement stmt = null;

			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();

			String sql = "SELECT * FROM login";
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()){
				Alert alert = new Alert(AlertType.INFORMATION);
				Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
				stagealert.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
				alert.setTitle("Edres Construction & Supply | Construction Cost Estimate");
				alert.setHeaderText("Checking Database...");
				alert.setContentText("Connected database successfully!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
			stagealert.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
			alert.setTitle("Edres Construction & Supply | Construction Cost Estimate");
			alert.setHeaderText("Database Error");
			alert.setContentText("Contact your software developer to fix this!");

			Exception ex = e;

			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
		}
	}

	// menu About
	public void clickAbout(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
		stagealert.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
		alert.setTitle("Edres Construction & Supply | Construction Cost Estimate");
		alert.setHeaderText("EDRES CONSTRUCTION & SUPPLY \u00a9 2017");
		alert.setContentText("Computerized Construction Cost Estimate for EDRES & Construction Supply");

		alert.showAndWait();
	}

	public void clickAdd(ActionEvent event) throws Exception {
		Optional<String> result;

		TextInputDialog dialog = new TextInputDialog();

		Stage stagealert = (Stage) dialog.getDialogPane().getScene().getWindow();
		stagealert.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
		dialog.setTitle("Edres Construction & Supply | Construction Cost Estimate");

		dialog.setHeaderText("Project Name");
		dialog.setContentText("Please enter name for the project:");
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

		result = dialog.showAndWait();

		if(result.isPresent()){

		// Database
		Connection conn = null;
		Statement stmt = null;

		// Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		// Execute a query
		stmt = conn.createStatement();

		String sql = "INSERT INTO `projectinfo` (`ProjectID`, `ProjName`, `ProjAddr`, `ProjDesc`, `ProjNote`, `CustName`, `CustAddr`, `CustNum`, `CustNote`, `UserName`, `UserAddr`, `UserNum`, `UserNote`) VALUES (NULL, '" + result.get() + "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";

		stmt.executeUpdate(sql);

		//Show result to table
		lstviewProject.getItems().add(result.get());
		}


	}

	public void clickEdit(ActionEvent event) throws Exception {
		Optional<String> result;

		TextInputDialog dialog = new TextInputDialog(lstviewProject.getSelectionModel().getSelectedItem());

		Stage stagealert = (Stage) dialog.getDialogPane().getScene().getWindow();
		stagealert.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
		dialog.setTitle("Edres Construction & Supply | Construction Cost Estimate");

		dialog.setHeaderText("Edit Project Name");
		dialog.setContentText("Please enter name for the project:");

		result = dialog.showAndWait();

		Integer select = lstviewProject.getSelectionModel().getSelectedIndex() + 1;

		if(result.isPresent()){

		// Database
		Connection conn = null;
		Statement stmt = null;

		// Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		// Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		// Execute a query
		stmt = conn.createStatement();

		String sql = "UPDATE `projectinfo` SET ProjName = '" + result.get() +"' WHERE `ProjectID` = '" + select +"'";

		stmt.executeUpdate(sql);

		}
		String sresult = result.get().trim();
		lstviewProject.getItems().set(lstviewProject.getSelectionModel().getSelectedIndex(),sresult);

	}

	public void clickDelete(ActionEvent event) throws Exception {

	}

	public void clickSelect(ActionEvent event) throws Exception {

	}






}
