package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
	
	Media media; // media resource - takes source url of media
	MediaPlayer player; // controls media playback
	MediaView view; // actually shows the view of the media being played
	Pane mediaPane;
	MediaBar mediaBar;
	
	public Player(String file) {
		media = new Media(file);
		player = new MediaPlayer(media);
		view = new MediaView(player);
		mediaPane = new Pane();
		
		mediaPane.getChildren().add(view); // add view of media to a pane
		setCenter(mediaPane); // add media pane to center of the player
		mediaBar = new MediaBar(player);
		
		setBottom(mediaBar);
		setStyle("-fx-background-color: #BFC2C7");
		
		player.play();
	}
	
}
