package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Nerc> selectNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtMaxHours;

    @FXML
    private Button btnWorst;

    @FXML
    private TextArea txtResult;
    
    @FXML
    void doAnalysis(ActionEvent event) {
    	txtResult.clear();
    	
    	int maxYears = 0;
    	int maxHours = 0;
    	Nerc nerc = selectNerc.getValue();
    	
    	try {
    		maxYears = Integer.parseInt(txtYears.getText());
    		maxHours = Integer.parseInt(txtMaxHours.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero!\n");
    		return;
    	}
    	
    	txtResult.appendText(""+maxYears);
    	txtResult.appendText(""+maxHours);
    	txtResult.appendText(nerc.toString());
    	
    	List<PowerOutages> risultato = new ArrayList<PowerOutages>();
    	risultato.addAll(model.trovaSequenza(nerc, maxYears, maxHours));
    	for(PowerOutages p : risultato)
    		txtResult.appendText(p.toString());
    }

    @FXML
    void initialize() {
        assert selectNerc != null : "fx:id=\"selectNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnWorst != null : "fx:id=\"btnWorst\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	ObservableList<Nerc> nerc = FXCollections.observableArrayList(model.getNercList());
    	
    	selectNerc.setItems(nerc);
    	selectNerc.setValue(nerc.get(0));
    }
}

