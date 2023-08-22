module Caixa {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.net.http;
	
	opens application to javafx.graphics, javafx.fxml;
}
