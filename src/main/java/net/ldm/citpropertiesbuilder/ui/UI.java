package net.ldm.citpropertiesbuilder.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import net.ldm.citpropertiesbuilder.file.FileManager;
import net.ldm.citpropertiesbuilder.file.ListParser;
import net.ldm.citpropertiesbuilder.file.UserSettings;

import java.nio.file.Paths;

public class UI {
	public static void build(Stage stage) {
		stage.setTitle("CIT Properties Builder");
		stage.setScene(buildScene(stage));
		stage.show();
	}

	private static Scene buildScene(Stage stage) {
		ChoiceBox<String> typeInput = new ChoiceBox<>();
		typeInput.getItems().addAll("Item", "Enchantment", "Armor");
		typeInput.setValue("Item");
		typeInput.setPrefWidth(Double.MAX_VALUE);

		ComboBox<String> itemInput = new ComboBox<>();
		itemInput.getItems().addAll(ListParser.IDS);
		itemInput.setEditable(true);
		itemInput.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (ListParser.IDS.contains(newValue)) return;
			itemInput.getItems().setAll(ListParser.IDS.stream()
					.filter(item -> item.toLowerCase().contains(newValue.toLowerCase())).toList());
		});
		itemInput.setPrefWidth(Double.MAX_VALUE);

		ComboBox<String> textureInput = new ComboBox<>();
		textureInput.getItems().addAll(ListParser.TEXTURES);
		textureInput.setEditable(true);
		textureInput.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (ListParser.TEXTURES.contains(newValue)) return;
			textureInput.getItems().setAll(ListParser.TEXTURES.stream()
					.filter(item -> item.toLowerCase().contains(newValue.toLowerCase())).toList());
		});
		textureInput.setPrefWidth(Double.MAX_VALUE);

		TextField displayName = new TextField();

		CheckBox displayNameWildcards = new CheckBox("Surround with wildcards? (Recommended)");
		displayNameWildcards.setSelected(true);
		displayNameWildcards.setTooltip(new Tooltip("If this box is checked, the item only has to contain the name, not exactly match it."));

		Label savedTo = new Label("Will be saved to: " + FileManager.getPropertiesPath());
		savedTo.setTooltip(new Tooltip(FileManager.getPropertiesPath().toString()));

		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> FileManager.saveProperties(typeInput.getValue(), itemInput.getValue(),
				textureInput.getValue(), displayName.getText(), displayNameWildcards.isSelected()));

		Button setDirButton = new Button("Change Active Directory");
		setDirButton.setOnAction(event -> {
			Popup popup = new Popup();
			TextField input = new TextField(UserSettings.getTexturePackDirectory().toString());
			Button confirm = new Button("Confirm");
			confirm.setOnAction(event1 -> {
				if (!Paths.get(input.getText()).isAbsolute()) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid path!", ButtonType.OK);
					popup.hide();
					alert.showAndWait();
					popup.show(stage);
				} else {
					UserSettings.setTexturePackDirectory(Paths.get(input.getText()));
					savedTo.setText("Will be saved to: " + FileManager.getPropertiesPath());
					savedTo.setTooltip(new Tooltip(FileManager.getPropertiesPath().toString()));
					popup.hide();
				}
			});

			BorderPane root = new BorderPane(new VBox(new Label("Change Active Directory"), input, confirm));
			root.setBackground(Background.fill(Color.WHITE));
			root.setPadding(new Insets(32));
			root.setPrefWidth(600);
			root.setBorder(Border.stroke(Color.GRAY));
			popup.getContent().setAll(root);
			popup.show(stage);
		});

		HBox buttonWrapper = new HBox(saveButton, setDirButton);
		buttonWrapper.setSpacing(8);

		VBox root = new VBox(buildInput("Type", typeInput), buildInput("Affected Item", itemInput),
				buildInput("Texture", textureInput), new VBox(new Label("Display Name"), displayName,
				displayNameWildcards), buttonWrapper, savedTo);
		root.setPadding(new Insets(16));
		root.setSpacing(16);
		return new Scene(root, 300, 400);
	}

	private static VBox buildInput(String prompt, Node input) {
		return new VBox(new Label(prompt), input);
	}
}
