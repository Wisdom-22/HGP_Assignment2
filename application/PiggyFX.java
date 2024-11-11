//HGP Assignment 02 Template
//TODO Chukwuemeka Wisdom Arinze - 2970177 - HCI and GUI - Assignment 2

package application;
import java.time.LocalDate;
import java.time.LocalTime;

//standard javafx imports
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PiggyFX extends Application {
	//declare components that require class scope
	Image img; 
	ImageView imv; 
	Label lblWelcome; 
	Label lblGoal; 
	Label displayAmount;
	Button btnAdd, btnRemove, showHistory, closeButton; 
	ProgressBar prog;
	
	double goal = 250; 
	double currentAmt = 0;
	String yourName = "Chukwuemeka Wisdom Arinze"; 
	String yourNum = "2970177"; 
	String amountProblem; 
	String image = "Assets/piggy1.png";
	
	TextArea txtAreaAddHistory;
	TextArea txtAreaWithdrawHistory;
	String addHistory = "";
	String withdrawHistory = "";
	

	//instantiate components using keyword 'new'
	public PiggyFX() {
		lblWelcome = new Label("My Piggy Bank:");
		lblGoal = new Label("Goal:");
		displayAmount = new Label("");
		btnAdd = new Button("Add Money");
		btnRemove = new Button("Withdraw Money");
		showHistory = new Button("History");
		prog = new ProgressBar(0);
		amountProblem = "";
		
		txtAreaAddHistory = new TextArea();
		txtAreaWithdrawHistory = new TextArea();
		closeButton = new Button("Close");
		
		//create the image and image view for the image at the centre of the main user interface
		try {
			img = new Image(image);
			imv = new ImageView(img);
			
		}catch(Exception exception) {
			System.err.println("Could not load image in main view!");
			exception.printStackTrace();
		}

	}
	
	// event handling
	@Override
	public void init() {
		btnAdd.setOnAction(event -> showAddDialog());//event that triggers the dialog that allows the user to add money to the piggy bank
		
		btnRemove.setOnAction(event -> showRemoveDialog());//event that triggers the dialog that allows the user to withdraw money to the piggy bank
		
		showHistory.setOnAction(event -> displayHistory()); //event that triggers the history of all transactions in the application
		
	}
	
	
	// method to show a dialog on clicking 'Add Money' button
	private void showAddDialog() {
		Stage stage = new Stage(); //create the stage for the dialog that allows the user to add money into the piggy bank
		stage.setTitle("Add to Piggy Bank"); //sets the title of the dialog
		
		//sets the width and height of the dialog
		stage.setWidth(500);
		stage.setHeight(250);
		
		//creates the labels and text area for the dialog that allows the user to add money into the piggy bank 
		Label lblAmount = new Label("Amount:");
		TextField tfAmount = new TextField();
		
		//styles both the label and the text area
		lblAmount.setStyle("-fx-font-size: 12pt;");
		tfAmount.setStyle("-fx-font-size: 12pt;");
		
		//creates the two buttons in the dialog that allows the user to add money into the piggy bank
		Button addButton = new Button("Add");
		Button closeButton = new Button("Close");
		
		//style the two buttons
		closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		addButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		
		//create the main layout
		BorderPane border = new BorderPane();
		border.setStyle("-fx-background-color: rgb(100, 140, 140);"); //style the main layout
		
		//creates the first sub layout
		HBox hbox = new HBox();
		hbox.getChildren().addAll(lblAmount, tfAmount); //adds the elements int the sub layout
		hbox.setAlignment(Pos.CENTER);
		hbox.setMaxWidth(500);
		hbox.setPadding(new Insets(50));
		hbox.setSpacing(10);
		
		//creates the second sub layout
		HBox hboxButtons = new HBox();
		hboxButtons.getChildren().addAll(addButton, closeButton); //adds the elements into the second sub layout
		hboxButtons.setAlignment(Pos.CENTER);
		hboxButtons.setPadding(new Insets(20));
		hboxButtons.setSpacing(10);
		
		//position the all the sub layout in different positions
		border.setCenter(hbox);
		border.setBottom(hboxButtons);
		
		//create the function for the close button
		closeButton.setOnAction(event -> stage.close());
		
		//creates what will happen when the user presses the add button 
		addButton.setOnAction(event -> {
			double progress = 0;
			double amount =0;
			boolean isNumber = false;
			boolean isProblem = false;
			
			//trying to make sure that the number entered in the text field is a double
			try {
				amount = Double.parseDouble(tfAmount.getText());
				if(amount > 0 || amount < 0) {
					isNumber = true;
				}
				
			}catch(NumberFormatException exception) {
				System.out.println("Please Enter only numbers!");
				amountProblem = "Please Enter only numbers or your input is not a number!\n";
				isProblem = true;
				isNumber = false;
			}
			
			if(tfAmount.getText().isBlank() || tfAmount.getText().equals("")) {
				isProblem = true;
				amountProblem = amountProblem + "The amount field is empty!\n";
				System.out.println("The amount field is empty!");
			}else if(isNumber == true) {
				
				if(amount > 0 && amount <= goal){
					System.out.println("Progressing...");
					progress = prog.getProgress();
					
					double calculatedAmount = amount / goal;
					progress = Math.round(progress * 100);
					progress = progress / 100;
					double currentAmount = calculatedAmount + progress;
					
					//formatting the current amount that will be displayed in the label so that it will be a value that is in two decimal places 
					currentAmount = currentAmount * 100;
					currentAmount = Math.floor(currentAmount);
					currentAmount = currentAmount / 100;
					
					System.out.println("Progress: " + progress + " Amount: " + amount + " Calculated Amount: " + calculatedAmount + " Current Amount: "
					+ currentAmount);
					
					displayAmount.setText("€"+currentAmount);//setting the amount in the label in the main ui
					prog.setProgress(currentAmount); //updates the progress bar with the amount entered by the user
					currentAmt = currentAmount; //assigning the currentAmount to currentAmt so that the value will become global 
					updatePiggyBankImage();
					
					//getting the date and time for every transaction for history
					addHistory = "Amount: €" + amount 
							+"\nDate : " + LocalDate.now()
							+"\nTime : " + LocalTime.now() + "\n\n";
					
					txtAreaAddHistory.appendText(addHistory);//appends the text to the history text area 
					
					System.out.println();
				
					stage.close();
				}
				
				//makes sure that the user entered a number greater than 1
				if(amount < 1) {
					amountProblem = amountProblem + "The amount of money is less than 1!\n";
					isProblem = true;
				}else if(amount == 0) {
					amountProblem = amountProblem + "The amount of money is 0!\n";
					isProblem = true;
				}
				
				//makes sure that the user enters a number that is not greater than goal
				if(amount > goal) {
					amountProblem = amountProblem + "The amount of money is greater than the goal!\n";
					isProblem = true;
				}
				
				if(progress > 1) {
					progress = 1;
				}
								
			}
			
			//if there are any issues trigger the method that displays the issues
			if(isProblem == true) {
				displayProblems();
			}

		});
		
		//creates the scene
		Scene scene = new Scene(border);
		
		//sets the scene
		stage.setScene(scene);
		
		//show the scene
		stage.show();
	}
	
	// method to show a dialog on clicking the 'Withdraw Money' button
	private void showRemoveDialog() {
		Stage stage = new Stage();//creates the stage
		stage.setTitle("Withdraw From Piggy Bank"); //sets the title of the dialog 
		
		//sets the width and height of the dialog
		stage.setWidth(500);
		stage.setHeight(250);
		
		//creates the ui elements in the remove dialog
		Label lblAmount = new Label("Amount:");
		TextField tfAmount = new TextField();
		
		//sets the style for the ui elements in the remove dialog
		lblAmount.setStyle("-fx-font-size: 12pt;");
		tfAmount.setStyle("-fx-font-size: 12pt;");
		
		//creates the two buttons in the remove dialog
		Button withdrawButton = new Button("Withdraw");
		Button closeButton = new Button("Close");
		
		//style the buttons in the remove dialog
		withdrawButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		
		//creates the main layout
		BorderPane border = new BorderPane();
		border.setStyle("-fx-background-color: rgb(100, 140, 140);"); //style the background color of the main ui
		
		//creates the first sub layout
		HBox hboxLabels = new HBox();
		hboxLabels.getChildren().addAll(lblAmount, tfAmount); //add all the ui elements in the sub layout
		hboxLabels.setAlignment(Pos.CENTER);
		hboxLabels.setMaxWidth(500);
		hboxLabels.setPadding(new Insets(50));
		hboxLabels.setSpacing(10);
		
		//creates the second sub layout
		HBox hboxButtons = new HBox();
		hboxButtons.getChildren().addAll(withdrawButton, closeButton);//add all the ui elements in the sb layout
		hboxButtons.setAlignment(Pos.CENTER);
		hboxButtons.setPadding(new Insets(20));
		hboxButtons.setSpacing(10);
		
		//position all the sub layouts 
		border.setCenter(hboxLabels);
		border.setBottom(hboxButtons);
		
		closeButton.setOnAction(event -> stage.close()); //add functionality to the close button
		withdrawButton.setOnAction(event ->{
			double progress = 0; //variable that will contain the progress of the progress bar
			double amount = 0; //variable that will contain the amount of the piggy bank 
			boolean isNumber = false; //boolean variable that will be true or false if the text in the text field is a number
			boolean isProblem = false; //boolean variable that will be true or false if there is a problem with the validation of the text from the text field
			//making sure that the text entered in the text field is a number
			try {
				amount = Double.parseDouble(tfAmount.getText());
				if(amount > 0 || amount < 0) {
					isNumber = true;
				}
				
			}catch(NumberFormatException exception) {
				System.out.println("Please Enter only numbers!");
				amountProblem = "Please Enter only numbers or your input is not a number!";
				isProblem = true;
				isNumber = false;
			}
			
			if(tfAmount.getText().isBlank() || tfAmount.getText().equals("")) {
				isProblem = true;
				amountProblem = "The amount field is empty!";
				System.out.println("The amount field is empty!");
			}
			
			if(isNumber == true) {
				if((  amount > 0  &&  amount <= goal  &&  ( amount / goal) < (prog.getProgress())  || (amount == goal)) ){
					//Double d = (Double) amount;
					System.out.println("Progressing...");
					//System.out.println(d);
					progress = prog.getProgress();
					
					//trying to change the amount entered in the text field to a double number that is in two decimal places
					double calculatedAmount = amount / goal;
					progress = Math.round(progress * 100);
					progress = progress / 100;
					double currentAmount = progress - calculatedAmount;
					
					//formatting the current amount that will be displayed in the label so that it will be a value that is in two decimal places 
					currentAmount = currentAmount * 100;
					currentAmount = Math.floor(currentAmount);
					currentAmount = currentAmount / 100;
					
					System.out.println("Progress: " + progress + " Amount: " + amount + " Calculated Amount: " + calculatedAmount + " Current Amount: "
					+ currentAmount);
					
					//set the text to the displayAmount label
					displayAmount.setText("€"+currentAmount);
					prog.setProgress(currentAmount); //set the progress bar to the amount gotten from the text field
					currentAmt = currentAmount;
					//call the method that will update the piggy bank image
					updatePiggyBankImage();
					
					//getting the date and time for every transaction
					withdrawHistory ="Amount: €" + amount 
									 +"\nDate : " + LocalDate.now()
							         +"\nTime : " + LocalTime.now() + "\n\n";
					
					//appending the text to the withdraw transaction text area in the history dialog
					txtAreaWithdrawHistory.appendText(withdrawHistory);				
					System.out.println();
				
					stage.close(); //close the dialog
				}else if(amount > goal) {
					amountProblem = "The amount of money you want to withdraw exceeds the amount in the Piggy Bank!";
					isProblem = true;
					System.out.println("The amount of money you want to withdraw exceeds the amount in the Piggy Bank!");
					
				}else if(amount > (prog.getProgress() * goal)) {
					amountProblem = "The amount of money you want to withdraw exceeds the amount in the Piggy Bank!";
					isProblem = true;
					System.out.println("The amount of money you want to withdraw exceeds the amount in the Piggy Bank!");
				}
				
				//make sure that numbers entered are not less than 1
				if(amount < 1) {
					amountProblem = "The amount of money is less than 1!";
					isProblem = true;
					
				}else if(amount == 0) {
					amountProblem = "The amount of money is 0!";
					isProblem = true;
					
				}
				
				//keep the progress at 1 even if the amount has exceeded 1
				if(progress > 1) {
					progress = 1;
				}
			}
			
			//if at any time the isProblem is true then call the displayProblems() method
			if(isProblem == true) {
				displayProblems();
			}
		});
		
		//create the scene for the remove dialog 
		Scene scene = new Scene(border);
		
		//set the scene for the remove dialog
		stage.setScene(scene);
		
		//show the scene
		stage.show();
	}
	
	//method that will display an alert if there is a problem with the number or text entered in the text field 
	public void displayProblems() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Caution!");
		
		alert.setHeaderText("Amount Entered has some error!");
		alert.setContentText(amountProblem + "\n"); //display the problem
		alert.showAndWait();
	}
	
	//method that will update the image according to the number entered in the text field
	public void updatePiggyBankImage() {
		if(currentAmt == 0) {
			imv.setImage(new Image("Assets/piggy1.png"));
			
		}else if(currentAmt >= 0.1 && currentAmt <= 0.33) {
			imv.setImage(new Image("Assets/piggy2.png"));
			
		}else if(currentAmt >= 0.34 && currentAmt <= 0.66) {
			imv.setImage(new Image("Assets/piggy3.png"));
			
		}else if(currentAmt >= 0.67 && currentAmt <= 0.99) {
			imv.setImage(new Image("Assets/piggy4.png"));
		
		}else if(currentAmt >= 1){
			imv.setImage(new Image("Assets/piggy5.png"));
		}
		
	}
	
	//method that will display all the transactions entered in the text field of both the add and remove dialog
	private void displayHistory() {
		Stage stage = new Stage();
		
		stage.setTitle("History"); //set the title of the history dialog
		
		//set the width and height of the dialog
		stage.setWidth(500);
		stage.setHeight(500);
		
		//labels that will be displayed in the history dialog
		Label lblAddHistoryHeading = new Label("Add History");
		Label lblWithdrawHistoryHeading = new Label("Withdraw History");
	
		//close button that will close the dialog
		closeButton.setOnAction(event -> stage.close());
		closeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;"); //styling the close button
		
		//styling the labels in the show history dialog
		lblAddHistoryHeading.setStyle("-fx-text-fill: black; -fx-font-size: 15");
		lblWithdrawHistoryHeading.setStyle("-fx-text-fill: black; -fx-font-size: 15");
		
		//creating the only layout in the show history dialog
		GridPane grid = new GridPane();
		grid.setStyle("-fx-background-color: rgb(100, 140, 140);"); //styling the only layout
		grid.add(lblAddHistoryHeading, 0, 0); //aligning the lblAddHistoryHeading label to column 0, row 0
		grid.add(txtAreaAddHistory, 0, 2); //aligning the txtAreaAddHistory label to column 0, row 2
		
		grid.add(lblWithdrawHistoryHeading, 0, 3);  //aligning the lblWithdrawHistoryHeading label to column 0, row 3
		grid.add(txtAreaWithdrawHistory, 0, 4, 1, 2);//aligning the lblWithdrawHistoryHeading label to column 0, row 4, starting from column 1, end at row 2
		grid.add(closeButton, 0, 6); //aligning the closeButton to column 0, row 6
		
		//txtAreaAddHistory.setDisable(true);
		//txtAreaWithdrawHistory.setDisable(true);
		
		//creating the scene
		Scene scene = new Scene(grid);
		
		//setting the scene
		stage.setScene(scene);
		
		//showing the dialog
		stage.show();
		
	}

	//window management and layouts
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("PiggyFX - " + yourName + " - " + yourNum); //setting the title of the window
		
		//setting the width and height of the window
		primaryStage.setWidth(800); 
		primaryStage.setHeight(500);
		
		//setting the logo of the application
		try {
			primaryStage.getIcons().add(new Image("Assets/logo.jpg"));
		}catch(Exception e) {
			System.err.println("Something went wrong with the icon!");
		}
		
		//main layout
		BorderPane border = new BorderPane();
		
		GridPane grid = new GridPane(); //create the grid pane layout
		grid.setPadding(new Insets(10)); //add padding to the grid 
		grid.add(lblWelcome, 0, 0); //align the lblWelcome label at column 0, row 0
		grid.add(lblGoal, 0, 1); //align the lblGoal label at column 0, row 1
		grid.add(displayAmount, 1, 1); //align the displayAmount label at column 1, row 1
		
		lblWelcome.setStyle("-fx-font-size: 18pt"); //styling lblWelcome label
		
		
		VBox vbox = new VBox(); //create the vbox layout
		vbox.getChildren().addAll(btnAdd, btnRemove, showHistory);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		
		//styling the all the buttons
		btnAdd.setStyle("-fx-background-color: red;");
		btnRemove.setStyle("-fx-background-color: red;");
		showHistory.setStyle("-fx-background-color: red;");
		
		HBox hbox = new HBox(); //create the HBOX
		hbox.getChildren().addAll(prog); //add the progress bar to the hbox
		hbox.setAlignment(Pos.BASELINE_CENTER); //position the hbox at the center
		
		border.setLeft(grid); //set the grid at the left
		border.setCenter(imv); //set the image view at the centre
		border.setRight(vbox); //set the vbox at the right
		border.setBottom(hbox); //set the hbox at the bottom
		
		
		imv.fitWidthProperty().bind(primaryStage.widthProperty().divide(2));
		imv.setPreserveRatio(true);		
		prog.prefWidthProperty().bind(primaryStage.widthProperty().subtract(160));
		
		prog.setStyle("-fx-accent: red"); //styling the progress bar
		
		Scene scene = new Scene(border);
		
		scene.getStylesheets().add("Assets/piggyStyle.css"); //get the style sheet to style the application
		
		primaryStage.setScene(scene); //set the scene
		
		primaryStage.show();
	}

	//launch the application
	public static void main(String[] args) {
		launch(args);
	}
}