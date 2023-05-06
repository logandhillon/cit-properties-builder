module net.ldm.citpropertiesbuilder {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.apache.commons.io;


	opens net.ldm.citpropertiesbuilder to javafx.fxml;
	exports net.ldm.citpropertiesbuilder;
	exports net.ldm.citpropertiesbuilder.ui;
	opens net.ldm.citpropertiesbuilder.ui to javafx.fxml;
	exports net.ldm.citpropertiesbuilder.file;
	opens net.ldm.citpropertiesbuilder.file to javafx.fxml;
}