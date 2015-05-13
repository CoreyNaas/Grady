/*------
 * Grady: Grade Calculator and Manager
 * Copyright 2015 (C) Corey Naas
 * Created: 2015-05-02
 ------*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
 
public class Grady extends Application {
	static int classNum = 0;
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
	Button btCalculateGPA = new Button("Calculate");
	Button btCalculateNeeded = new Button("Calculate");
	Button btSaveGrades = new Button("Save");
	Button btLoadGrades = new Button("Load");
	static CheckBox chkSave = new CheckBox("Save");
	
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
		gPane.add(btCalculateGPA, 3, 10);
		gPane.add(btCalculateNeeded, 6, 9);
		gPane.add(btSaveGrades, 4, 10);
		gPane.add(btLoadGrades, 5, 10);
		gPane.add(chkSave, 4, 9);
		
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
		for(int i = 0; i < 7; i++){
			gPane.add(tfClass.get(i), 1, 2+i);
			gPane.add(tfCreditHour.get(i), 2, 2+i);
			gPane.add(tfCurrentGrade.get(i), 3, 2+i);
			gPane.add(tfTargetGrade.get(i), 4, 2+i);
			gPane.add(tfRemainingGrade.get(i), 5, 2+i);
			gPane.add(tfGradeNeeded.get(i), 6, 2+i);
		}
		
		//set UI Properties
		gPane.setAlignment(Pos.CENTER);
		for(int i = 0; i < 7; i++){ tfGradeNeeded.get(i).setEditable(false); }
		tfCurrentGPA.setEditable(false);
		
		//set TextField widths
		tfTargetGPA.setPrefWidth(50);
		tfNumberOfClasses.setPrefWidth(50);
		tfCurrentGPA.setPrefWidth(50);
		for(int i = 0; i < 7; i++){ tfClass.get(i).setPrefWidth(150); }
		for(int i = 0; i < 7; i++){ tfCreditHour.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfCurrentGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfTargetGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfRemainingGrade.get(i).setPrefWidth(25); }
		for(int i = 0; i < 7; i++){ tfGradeNeeded.get(i).setPrefWidth(50); }
		
		
		//process events
		btCalculateGPA.setOnAction( e -> Calculate.calculateCurrentGPA());
		btCalculateNeeded.setOnAction( e -> Calculate.calculateGradeNeeded());
		btSaveGrades.setOnAction( e -> GradeFile.saveGrades());
		btLoadGrades.setOnAction( e -> GradeFile.loadGrades());
		
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
		
		int[] creditHour = new int[7];
		double[] currentGrade = new double[7];
		double[] currentGradeCalc = new double[7];
		double[] gradePoint = new double[7];
		double totalGradePoints = 0;
		double currentGPA = 0;
		int totalHours = 0;
		
		//catched exception if no input in "# of classes" text field
		try{
			Grady.classNum = Integer.parseInt(Grady.tfNumberOfClasses.getText());
		}catch(NumberFormatException nfe){
			System.out.println("NumberOfClasses error: add number of classes");
			JOptionPane.showMessageDialog(null, "Add number of classes");
		}
		
		//Collects credit hours for each class from tfCreditHour
		for(int i = 0; i < Grady.classNum; i++){
			try{
				final String Hour = Grady.tfCreditHour.get(i).getText();
				creditHour[i] = Integer.parseInt(Hour);
			}catch(NumberFormatException nfe){
				System.out.println("CreditHour error: credit hour entry(s) missing");
				JOptionPane.showMessageDialog(null, "Credit hour entry(s) missing");
			}
		}
			
		//collects grades from tfCurrentGrade
		for(int i = 0; i < Grady.classNum; i++){
			try{
				final String Grade = Grady.tfCurrentGrade.get(i).getText();
				currentGrade[i] = Double.parseDouble(Grade);
			}catch(NumberFormatException nfe){
				System.out.println("CurrentGrade error: current grade entry(s) missing");
				JOptionPane.showMessageDialog(null, "Current grade entry(s) missing");
			}
		}
		
		//Adds total credit hours
		for(int i = 0; i < Grady.classNum; i++){
			totalHours = totalHours + creditHour[i];
		}
		
		//convert percentage grades to non-decimal double between 1 and 4
		for(int i = 0; i < Grady.classNum; i++){
			if(currentGrade[i] < 100 && currentGrade[i] >= 90){ currentGradeCalc[i] = 4.0; }
			else if(currentGrade[i] < 90 && currentGrade[i] >= 80){ currentGradeCalc[i] = 3.0; }
			else if(currentGrade[i] < 80 && currentGrade[i] >= 70){ currentGradeCalc[i] = 2.0; }
			else if(currentGrade[i] < 70 && currentGrade[i] >= 60){ currentGradeCalc[i] = 1.0; }
		}
		
		//multiply credit hours by grades to get grade points
		for(int i = 0; i < Grady.classNum; i++){
			gradePoint[i] = creditHour[i] * currentGradeCalc[i];
		}
		
		//adds total grade points
		for(int i = 0; i < Grady.classNum; i++){
			totalGradePoints = totalGradePoints + gradePoint[i];
		}
		
		//divide total grade points by total credit hours
		for(int i = 0; i < Grady.classNum; i++){
			currentGPA = totalGradePoints / totalHours;
		}
		
		//outputs result of math 
		Grady.tfCurrentGPA.setText(Double.toString(currentGPA));
		
		//debugging output to console
		System.out.println("Total Credit Hours: " + totalHours);
		System.out.println("Total Grade Points: " + totalGradePoints);
		System.out.println("--------");
	}
	
	public static void calculateGradeNeeded(){
	
		double[] currentGrade = new double[7];
		double[] targetGrade = new double[7];
		double[] remainingGrade = new double[7];
		
		//catched exception if no input in "# of classes" text field
		try{
			Grady.classNum = Integer.parseInt(Grady.tfNumberOfClasses.getText());
		}catch(NumberFormatException nfe){
				System.out.println("NumberOfClasses error: add number of classes");
				JOptionPane.showMessageDialog(null, "Add number of classes");
			}
		
		//collects grades from tfCurrentGrade
		for(int i = 0; i < Grady.classNum; i++){
			try{
				final String Grade = Grady.tfCurrentGrade.get(i).getText();
				currentGrade[i] = Double.parseDouble(Grade);
			}catch(NumberFormatException nfe){
				System.out.println("CurrentGrade error: current grade entry(s) missing");
				JOptionPane.showMessageDialog(null, "Current grade entry(s) missing");
			}
		}
		
		//collects target grade from tfTargetGrade
		for(int i = 0; i < Grady.classNum; i++){
			try{
				final String Grade = Grady.tfTargetGrade.get(i).getText();
				targetGrade[i] = Double.parseDouble(Grade);
			}catch(NumberFormatException nfe){
				System.out.println("targetGrade error: target grade entry(s) missing");
				JOptionPane.showMessageDialog(null, "Target grade entry(s) missing");
			}
		}
		
		//collects remaining grade from tfRemainingGrade
		for(int i = 0; i < Grady.classNum; i++){
			try{
				final String Grade = Grady.tfRemainingGrade.get(i).getText();
				remainingGrade[i] = Double.parseDouble(Grade);
			}catch(NumberFormatException nfe){
				System.out.println("remainingGrade error: remaining grade entry(s) missing");
				JOptionPane.showMessageDialog(null, "Remaining grade entry(s) missing");
			}
		}
		
		//calculates average grade needed on remaining assignments to get target overall grade
		for(int i = 0; i < Grady.classNum; i++){
			double gradeNeeded = targetGrade[i]*100.0;
			gradeNeeded = (currentGrade[i] * remainingGrade[i] +100 * (targetGrade[i] - currentGrade[i]))/ remainingGrade[i];
			Grady.tfGradeNeeded.get(i).setText(Double.toString(gradeNeeded));
		}
		
	}
}

class GradeFile{
	public static void saveGrades(){
		if (Grady.chkSave.isSelected() == true){
		
			//catched exception if no input in "# of classes" text field
			try{
				Grady.classNum = Integer.parseInt(Grady.tfNumberOfClasses.getText());
			}catch(NumberFormatException nfe){
					System.out.println("NumberOfClasses error: add number of classes");
				}
			
			//create File object for grades.txt
			File file = new File("grades.txt");
			
			//write grade data to grades.txt
			try{
				PrintWriter output = new PrintWriter(file);
				output.println(Grady.tfNumberOfClasses.getText() + " ");
				for(int i = 0; i < Grady.classNum; i++){
					output.print(Grady.tfClass.get(i).getText() + " ");
					output.print(Grady.tfCreditHour.get(i).getText() + " ");
					output.print(Grady.tfCurrentGrade.get(i).getText() + " ");
					output.print(Grady.tfTargetGrade.get(i).getText() + " ");
					output.print(Grady.tfRemainingGrade.get(i).getText() + " ");
					output.println();
				}
				output.close();
				Grady.chkSave.setSelected(false);
			}catch(FileNotFoundException ex){
				System.out.println("Write: File not found");
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Write: File not found");
			}
		}
	}
	
	public static void loadGrades(){
		
		//catched exception if no input in "# of classes" text field
		try{
			Grady.classNum = Integer.parseInt(Grady.tfNumberOfClasses.getText());
		}catch(NumberFormatException nfe){
				System.out.println("NumberOfClasses error: add number of classes");
			}
		
		//create File object for grades.txt
		File file = new File("grades.txt");
		
		//read grade data from grades.txt
		try{
			Scanner input = new Scanner(file);
			Grady.classNum = Integer.parseInt(input.next());
			Grady.tfNumberOfClasses.setText(Integer.toString(Grady.classNum));
			for(int i = 0; i < Grady.classNum; i++){
				Grady.tfClass.get(i).setText(input.next());
				Grady.tfCreditHour.get(i).setText(input.next());
				Grady.tfCurrentGrade.get(i).setText(input.next());
				Grady.tfTargetGrade.get(i).setText(input.next());
				Grady.tfRemainingGrade.get(i).setText(input.next());
			}
			input.close();
		}catch(FileNotFoundException ex){
			System.out.println("Read: File not found");
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Read: File not found");
		}
		
		for(int i = 0; i < 7; i++){ Grady.tfGradeNeeded.get(i).setText(""); }
		Grady.tfCurrentGPA.setText("");
	}
	
}

