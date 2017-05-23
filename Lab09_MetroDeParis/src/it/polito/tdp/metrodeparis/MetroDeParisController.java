
	/**
	 * Sample Skeleton for 'MetroDeParis.fxml' Controller Class
	 */

	package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.Util;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
	import javafx.scene.input.MouseEvent;

	public class MetroDeParisController {
		
		Model model;
		
	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="btnPercorso"
	    private Button btnPercorso; // Value injected by FXMLLoader

	    @FXML // fx:id="txtResult"
	    private TextArea txtResult; // Value injected by FXMLLoader
	    
	    @FXML
	    private ComboBox<Fermata> cmbBoxP;

	    @FXML
	    private ComboBox<Fermata> cmbBoxA;
	    
	    void setModel(Model model) {
	    	this.model = model;
	    	
	    	try{
	    		model.creaGrafo();
	    		List<Fermata> stazioni = model.getStazioni();
	    		cmbBoxP.getItems().addAll(stazioni);
	    		cmbBoxA.getItems().addAll(stazioni);
	    		
	    	} catch(RuntimeException e) {
	    		txtResult.setText(e.getMessage());
	    	}
	    }
	    
	    @FXML
	    void doPercorso(ActionEvent event) {
	    		Fermata stazioneP = cmbBoxP.getValue();
	    		Fermata stazioneA = cmbBoxA.getValue();
	    		
	    		if(stazioneP == null || stazioneA == null) {
	    			txtResult.setText("Inserire una stazione di arrivo e una di partenza");
	    			return;
	    		}
	    		
	    		if(stazioneP.equals(stazioneA)) {
	    			txtResult.setText("Inserire una stazione di arrivo diversa da quella di partenza");
	    			return;
	    		}
	    		
	    		try {
	    			
	    			model.calcolaPercorso(stazioneP, stazioneA);
	    			String timeString = it.polito.tdp.metrodeparis.model.Util.timeConverter((int)model.getPercorsoTempoTotale());
	    	
	    		StringBuilder ris = new StringBuilder();
	    		
	    		ris.append(model.getPercorsoEdgeList());
	    		ris.append("\n\nTempo di percorrenza stimato: " + timeString + "\n");

	    		txtResult.setText(ris.toString());
	    		
	    		} catch(RuntimeException e) {
	    			txtResult.setText(e.getMessage());
	    		}
	    	
	    }

	    
	    

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

	    }
	}
	
