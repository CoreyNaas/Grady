/*------
 * Copyright 2015 (C) Corey Naas
 * Created: 2015-05-02
 ------*/
 
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

 
public class Grady extends Application {
//{ <- folding in np++
	//declare globton of textfields lol
	/*This will be improved once the program actually does something other
	than sit around in the ram and eat my transistors*/
	ArrayList<TextField> tfClass = new ArrayList<TextField>();
	ArrayList<TextField> tfCurrentGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfTargetGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfRemainingGrade = new ArrayList<TextField>();
	ArrayList<TextField> tfGradeNeeded = new ArrayList<TextField>();
//}

	public void start(Stage priStage){	
		//Create grid-based UI
		GridPane gPane = new GridPane();
		gPane.setHgap(5);
		gPane.setVgap(5);
		
		//populate ArrayLists
		for(int i = 0; i < 7; i++){ tfClass.add(new TextField("Class " + i)); }
		for(int i = 0; i < 7; i++){ tfCurrentGrade.add(new TextField("Current Grade")); }
		for(int i = 0; i < 7; i++){ tfTargetGrade.add(new TextField("Target Grade")); }
		for(int i = 0; i < 7; i++){ tfRemainingGrade.add(new TextField("Remaining Grade")); }
		for(int i = 0; i < 7; i++){ tfGradeNeeded.add(new TextField("Grade Needed")); }
		
		for(int i = 0; i < 7; i++){ gPane.add(tfClass.get(i), 1, i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfCurrentGrade.get(i), 2, i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfTargetGrade.get(i), 3, i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfRemainingGrade.get(i), 4, i); }
		for(int i = 0; i < 7; i++){ gPane.add(tfGradeNeeded.get(i), 5, i); }
		
		
		//creates priScene and sets in stage priStage
		Scene priScene = new Scene(gPane, 500, 600);
		priStage.setTitle("Grady - Grade Calculator");
		priStage.setScene(priScene);
		priStage.show();
	}

/* 	public static void main(String[] args) {
		System.out.println("lol");
	} */
}

