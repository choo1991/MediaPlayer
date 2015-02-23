package application;
	
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	Player player;
	FileChooser fileChooser;
	
	@Override
	public void start(Stage primaryStage) {
		
		MenuItem openItem = new MenuItem("Open");
		Menu file = new Menu("File");
		MenuBar menu = new MenuBar();
		
		file.getItems().add(openItem);
		menu.getMenus().add(file);
		fileChooser = new FileChooser();
		
		// currently crashes when trying to open a URL
		openItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				player.player.pause();
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						player = new Player(file.toURI().toURL().toExternalForm());
						Scene scene = new Scene(player, 1280, (720 + 35 + 25), Color.WHITE);
						primaryStage.setScene(scene);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		// create a new player with the video URL (currently pointing to Kingsman trailer)
		player = new Player("http://videos.hd-trailers.net/a78b60a9-b902-48eb-b7cd-0c126864ae8c_5njosb4jBZYN1AmDxOnKuX1ta2xt0xdvZlQyq9Cm_zqGIIeicnkfjN7zpnS0QSPHHeaxVQKacNE-_10_0.mp4");
		player.setTop(menu);
		Scene scene = new Scene(player, 1280, (720 + 35 + 25), Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
