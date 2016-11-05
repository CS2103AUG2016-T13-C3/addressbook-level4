package seedu.malitio.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.malitio.model.task.ReadOnlyDeadline;


public class DeadlineCard extends UiPart{
    

    private static final String FXML = "DeadlineListCard.fxml";

    @FXML
    private HBox cardPane2;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label due;

    @FXML
    private Label tags;

    private ReadOnlyDeadline deadline;
    private int displayedIndex;

    public DeadlineCard(){

    }

    public static DeadlineCard load(ReadOnlyDeadline deadline, int displayedIndex){
        DeadlineCard card = new DeadlineCard();
        card.deadline = deadline;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
    	//@@author A0122460W
    	if (deadline.getCompleted()){
    		name.setText(deadline.getName().fullName);
    		id.setStyle("-fx-text-fill: gray;");
    		name.setStyle("-fx-text-fill: gray;");
    		due.setStyle("-fx-text-fill: gray;");
    		tags.setStyle("-fx-text-fill: gray;");
    		name.getStylesheets().addAll(getClass().getResource("/view/strikethrough.css").toExternalForm());
    	} else {
    		name.setText(deadline.getName().fullName);
    	}
    	
    	//@@author
    	if (deadline.isMarked()) {
    	    cardPane2.setStyle("-fx-background-color: yellow;");
    	} else {
    	    cardPane2.setStyle("-fx-background-color: white;");
    	}
    	
    	//@@author A0122460W
    	if (deadline.getDue().compareTo(new Date())<0 && !deadline.getCompleted()){
    		name.setText(deadline.getName().fullName);
    		cardPane2.setStyle("-fx-background-color: red;");
    	}
    	
    	//@@author
        id.setText("D" + displayedIndex + ". ");
        due.setText("Due: "+ deadline.getDue().toString());
        tags.setText(deadline.tagsString());
    }

    public HBox getLayout() {
        return cardPane2;
    }

    @Override
    public void setNode(Node node) {
        cardPane2 = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
