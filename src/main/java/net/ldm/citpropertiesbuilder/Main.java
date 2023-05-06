package net.ldm.citpropertiesbuilder;

import javafx.application.Application;
import javafx.stage.Stage;
import net.ldm.citpropertiesbuilder.ui.UI;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		UI.build(primaryStage);
	}

	public static void main(String[] args) {
		launch();
	}
}