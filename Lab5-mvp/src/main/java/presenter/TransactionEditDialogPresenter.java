package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Transaction;

import java.time.format.DateTimeFormatter;

public class TransactionEditDialogPresenter {

	private Transaction transaction;

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField payeeTextField;

	@FXML
	private TextField categoryTextField;

	@FXML
	private TextField inflowTextField;
	
	private Stage dialogStage;
	
	private boolean approved;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setData(Transaction transaction) {
		this.transaction = transaction;
		updateControls();
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		// TODO: implement
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		// TODO: implement
	}
	
	private void updateModel() {
		// TODO: implement
	}
	
	private void updateControls() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter dateConverter = new LocalDateStringConverter(formatter, formatter);

		dateTextField.setText(dateConverter.toString(transaction.getDate()));
		payeeTextField.setText(transaction.getPayee());
		categoryTextField.setText(transaction.getCategory().getName());
		inflowTextField.setText(transaction.getInflow().toString());
	}
}
