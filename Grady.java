/*------
 * Grady: Grade Calculator and Manager
 * Copyright 2015 (C) Corey Naas
 * Created: 2015-05-02
 ------*/
 
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

 
public class Grady extends Application {
	//declare globton of textfields lol
	ArrayList<TextField> tfClass = new ArrayList<TextField>();
	ArrayList<TextField> tfCreditHours = new ArrayList<TextField>();
	ArrayList<TextField> tfCurrentGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfTargetGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfRemainingGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfGradeNeeded = new ArrayList<TextField>();
	TextField tfTargetGPA = new TextField("Optional");
	TextField tfNumberOfClasses = new TextField("Required");
	TextField tfCurrentGPA = new TextField();
	Button btDoThings = new Button("Calculate");
	
	public void start(Stage priStage){	
		//Create grid-based UI
		GridPane gPane = new GridPane();
		gPane.setHgap(5);
		gPane.setVgap(5);
		
		//add various elements to gPane
		gPane.add(new Label("Target GPA:"), 1, 0);
		gPane.add(tfTargetGPA, 2, 0);
		gPane.add(new Label("# of Classes"), 4, 0);
		gPane.add(tfNumberOfClasses, 5, 0);
		gPane.add(new Label("Current GPA:"), 2, 8);
		gPane.add(tfCurrentGPA, 3, 8);
		gPane.add(btDoThings, 3, 9);
		
		//populate ArrayLists
		for(int i = 0; i < 7; i++){ tfClass.add(new TextField("Class " + i)); }
		for(int i = 0; i < 7; i++){ tfCreditHours.add(new TextField("Credit Hours ")); }
		for(int i = 0; i < 7; i++){ tfCurrentGrade.add(new TextField("Current Grade")); }
		for(int i = 0; i < 7; i++){ tfTargetGrade.add(new TextField("Target Grade")); }
		for(int i = 0; i < 7; i++){ tfRemainingGrade.add(new TextField("Remaining Grade")); }
		for(int i = 0; i < 7; i++){ tfGradeNeeded.add(new TextField("Grade Needed")); }
		
		//add TextFields to gPane
		for(int i = 0; i < 7; i++){ gPane.add(tfClass.get(i), 1, 1+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfCreditHours.get(i), 2, 1+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfCurrentGrade.get(i), 3, 1+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfTargetGrade.get(i), 4, 1+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfRemainingGrade.get(i), 5, 1+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfGradeNeeded.get(i), 6, 1+i); }
		
		//set UI Properties
		gPane.setAlignment(Pos.CENTER);
		for(int i = 0; i < 7; i++){ tfGradeNeeded.get(i).setEditable(false); }
		tfCurrentGPA.setEditable(false);
		
		//process events
		btDoThings.setOnAction( e -> calculateCurrentGPA());
		
		//creates priScene and sets in stage priStage
		Scene priScene = new Scene(gPane, 780, 300);
		priStage.setTitle("Grady - Grade Calculator");
		priStage.setScene(priScene);
		priStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	} 
	
	//calculates GPA from "current grade" column
	public void calculateCurrentGPA(){
		
		/* Needs to collect nummber from tfCurrentGrade TextField array, math them, and
		output result to tfCurrentGPA  */
		int classNum = Integer.parseInt(tfNumberOfClasses.getText()); 
		double[] currentGrade = new double[7];
		double currentGPA = 0;
		
		for(int i = 0; i < classNum; i++){
			final String Grade = tfCurrentGrade.get(i).getText();
			currentGrade[i] = Double.parseDouble(Grade);
		}
		
		
		for(int i = 0; i < classNum; i++){
			currentGPA = currentGPA + currentGrade[i];
		}
		
		//outputs result of math 
		tfCurrentGPA.setText(Double.toString(currentGPA));
	}
}
