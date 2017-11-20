package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.Optional;



public class Main extends Application {

	//Database
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/testlogin";
	static final String USER = "root";
	static final String PASS = "";

	public static String usernameX;
	public static String passwordX;


	@Override
	public void start(Stage primaryStage) {
		try {
			Connection conn = null;
			Statement stmt = null;

			try{
			  // Register JDBC driver
			  Class.forName("com.mysql.jdbc.Driver");

			  // Open a connection
			  System.out.println("Connecting to a selected database...");
			  conn = DriverManager.getConnection(DB_URL, USER, PASS);
			  System.out.println("Connected database successfully...");

		      // Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();

		      String sql = "SELECT * FROM login";
		      ResultSet rs = stmt.executeQuery(sql);

		      while(rs.next()){
		    	//Retrieve by column name
		          usernameX = rs.getString("username");
		          passwordX = rs.getString("password");
		      }

		      }catch(SQLException se){
	          //Handle errors for JDBC
			  se.printStackTrace();
			  }catch(Exception e){
			  //Handle errors for Class.forName
			  e.printStackTrace();
			  }finally{
			  //finally block used to close resources
			  try{
			  if(conn!=null)
			  conn.close();
			  }catch(SQLException se){
			  se.printStackTrace();
			  }//end finally try
			  }//end try
			  System.out.println("Goodbye!");

			  ///

			  // Create the custom dialog.
			  Dialog<Pair<String, String>> dialog = new Dialog<>();
			  dialog.setTitle("Construction Cost Estimate");
			  dialog.setHeaderText("Please Sign in to continue...");

			  // Set the icon (must be included in the project).
			  dialog.setGraphic(new ImageView(this.getClass().getResource("locked.png").toString()));


			  // Set the button types.
			  ButtonType loginButtonType = new ButtonType("Sign in", ButtonData.OK_DONE);
			  dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

			  // Create the username and password labels and fields.
			  GridPane grid = new GridPane();
			  grid.setHgap(10);
			  grid.setVgap(10);
			  grid.setPadding(new Insets(20, 20, 10, 10));

			  TextField username = new TextField();
			  username.setPrefWidth(200);
			  username.setPromptText("Username");
			  PasswordField password = new PasswordField();
			  password.setPromptText("Password");

			  grid.add(new Label("Username:"), 0, 0);
			  grid.add(username, 1, 0);
			  grid.add(new Label("Password:"), 0, 1);
			  grid.add(password, 1, 1);

			  dialog.getDialogPane().setContent(grid);

			  // Request focus on the username field by default.
			  Platform.runLater(() -> username.requestFocus());

			  // Convert the result to a username-password-pair when the login button is clicked.
			  dialog.setResultConverter(dialogButton -> {
			      if (dialogButton == loginButtonType) {
			          return new Pair<>(username.getText(), password.getText());
			      }
			      return null;
			  });

			  Optional<Pair<String, String>> result = dialog.showAndWait();

			  if (result.isPresent()){
				  if (username.getText().equals(usernameX)&& password.getText().equals(passwordX)){
					  Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
					  Scene scene = new Scene(root);
					  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					  primaryStage.setScene(scene);
					  primaryStage.resizableProperty().setValue(Boolean.FALSE);
					  primaryStage.setTitle("Edres Construction & Supply | Construction Cost Estimate");
					  primaryStage.show();

				  } else {
					  Alert alert = new Alert(AlertType.ERROR);
					  alert.setTitle("Edres Construction & Supply | Construction Cost Estimate");
					  alert.setHeaderText("Sign in Error.");
					  alert.setContentText("Ooops, wrong username and password!");

					  alert.showAndWait();
				  }
			  }


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
