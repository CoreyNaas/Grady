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
	static ArrayList<TextField> tfClass = new ArrayList<TextField>();
	static ArrayList<TextField> tfCreditHour = new ArrayList<TextField>();
	static ArrayList<TextField> tfCurrentGrade = new ArrayList<TextField>();
	static ArrayList<TextField> tfTargetGrade = new ArrayList<TextField>();
	static ArrayList<TextField> tfRemainingGrade = new ArrayList<TextField>();
	static ArrayList<TextField> tfGradeNeeded = new ArrayList<TextField>();
	static TextField tfTargetGPA = new TextField("Optional");
	static TextField tfNumberOfClasses = new TextField("Required");
	static TextField tfCurrentGPA = new TextField();
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
		gPane.add(new Label("Current GPA:"), 2, 9);
		gPane.add(tfCurrentGPA, 3, 9);
		gPane.add(btDoThings, 3, 10);
		
		//add Column titles to gPane
		gPane.add(new Label("Class Name"), 1, 1);
		gPane.add(new Label("Credit Hours"), 2, 1);
		gPane.add(new Label("Current Grade"), 3, 1);
		gPane.add(new Label("Target Grade"), 4, 1);
		gPane.add(new Label("Remaining Grade"), 5, 1);
		gPane.add(new Label("Grade Needed"), 6, 1);
		
		//populate ArrayLists
		for(int i = 0; i < 7; i++){ tfClass.add(new TextField()); }
		for(int i = 0; i < 7; i++){ tfCreditHour.add(new TextField()); }
		for(int i = 0; i < 7; i++){ tfCurrentGrade.add(new TextField()); }
		for(int i = 0; i < 7; i++){ tfTargetGrade.add(new TextField()); }
		for(int i = 0; i < 7; i++){ tfRemainingGrade.add(new TextField()); }
		for(int i = 0; i < 7; i++){ tfGradeNeeded.add(new TextField()); }
		
		//add TextFields to gPane
		for(int i = 0; i < 7; i++){ gPane.add(tfClass.get(i), 1, 2+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfCreditHour.get(i), 2, 2+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfCurrentGrade.get(i), 3, 2+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfTargetGrade.get(i), 4, 2+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfRemainingGrade.get(i), 5, 2+i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfGradeNeeded.get(i), 6, 2+i); }
		
		//set UI Properties
		gPane.setAlignment(Pos.CENTER);
		for(int i = 0; i < 7; i++){ tfGradeNeeded.get(i).setEditable(false); }
		tfCurrentGPA.setEditable(false);
		
		//set TextField widths
		tfTargetGPA.setPrefWidth(25);
		tfNumberOfClasses.setPrefWidth(50);
		tfCurrentGPA.setPrefWidth(50);
		for(int i = 0; i < 7; i++){ tfClass.get(i).setPrefWidth(150); }
		for(int i = 0; i < 7; i++){ tfCreditHour.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfCurrentGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfTargetGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfRemainingGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfGradeNeeded.get(i).setPrefWidth(50); }
		
		
		//process events
		btDoThings.setOnAction( e -> Calculate.calculateCurrentGPA());
		
		//creates priScene and sets in stage priStage
		Scene priScene = new Scene(gPane, 600, 325);
		priStage.setTitle("Grady - Grade Calculator");
		priStage.setScene(priScene);
		priStage.show();
	}

	public static void main(String[] args) {
		System.out.println("Grady (c) 2015 Corey Naas");
		System.out.println("Debug data \\/");
		launch(args);
	} 
}





class Calculate {
	public static void calculateCurrentGPA(){
		
		int classNum = 0;
		int[] creditHour = new int[7];
		double[] currentGrade = new double[7];
		double[] currentGradeCalc = new double[7];
		double[] gradePoint = new double[7];
		double totalGradePoints = 0;
		double currentGPA = 0;
		int totalHours = 0;
		
		//catched exception if no input in "# of classes" text field
		try{
			classNum = Integer.parseInt(Grady.tfNumberOfClasses.getText());
		}catch(NumberFormatException nfe){
				System.out.println("NumberOfClasses error");
			}
		
		//Collects credit hours for each class from tfCreditHour
		for(int i = 0; i < classNum; i++){
			try{
				final String Hour = Grady.tfCreditHour.get(i).getText();
				creditHour[i] = Integer.parseInt(Hour);
			}catch(NumberFormatException nfe){
				System.out.println("CreditHour error");
			}
		}
			
		//collects grades from tfCurrentGrade
		for(int i = 0; i < classNum; i++){
			try{
				final String Grade = Grady.tfCurrentGrade.get(i).getText();
				currentGrade[i] = Double.parseDouble(Grade);
			}catch(NumberFormatException nfe){
				System.out.println("CurrentGrade error");
			}
		}
		
		//Adds total credit hours
		for(int i = 0; i < classNum; i++){
			totalHours = totalHours + creditHour[i];
		}
		
		//convert percentage grades to non-decimal double between 1 and 4
		for(int i = 0; i < classNum; i++){
			if(currentGrade[i] < 100 && currentGrade[i] >= 90){ currentGradeCalc[i] = 4.0; }
			else if(currentGrade[i] < 90 && currentGrade[i] >= 80){ currentGradeCalc[i] = 3.0; }
			else if(currentGrade[i] < 80 && currentGrade[i] >= 70){ currentGradeCalc[i] = 2.0; }
			else if(currentGrade[i] < 70 && currentGrade[i] >= 60){ currentGradeCalc[i] = 1.0; }
		}
		
		//multiply credit hours by grades to get grade points
		for(int i = 0; i < classNum; i++){
			gradePoint[i] = creditHour[i] * currentGradeCalc[i];
		}
		
		//adds total grade points
		for(int i = 0; i < classNum; i++){
			totalGradePoints = totalGradePoints + gradePoint[i];
		}
		
		//divide total grade points by total credit hours
		for(int i = 0; i < classNum; i++){
			currentGPA = totalGradePoints / totalHours;
		}
		
		//outputs result of math 
		Grady.tfCurrentGPA.setText(Double.toString(currentGPA));
		
		//debugging output to console
		System.out.println("Total Credit Hours: " + totalHours);
		System.out.println("Total Grade Points: " + totalGradePoints);
		System.out.println("--------");
	}
}
