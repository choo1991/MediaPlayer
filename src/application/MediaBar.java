package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

// horizontal box that will contain control buttons for the media at the bottom of video
public class MediaBar extends HBox {
	
	Slider time = new Slider();
	Slider volume = new Slider();
	Button playButton = new Button("||");
	Label volumeLabel = new Label("Volume: ");
	MediaPlayer mediaPlayer;
	
	public MediaBar(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
		
		setAlignment(Pos.CENTER); // align elements of box to center
		setPadding(new Insets(5, 10, 5, 10));
		
		volume.setPrefWidth(70); // set preferred width for volume slider
		volume.setMinWidth(30);
		volume.setValue(100);
		
		// if more space is needed for the time slider, allocate space if available
		HBox.setHgrow(time, Priority.ALWAYS); 
		
		playButton.setPrefWidth(30);
		
		getChildren().add(playButton);
		getChildren().add(time);
		getChildren().add(volumeLabel);
		getChildren().add(volume);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Status playerStatus = mediaPlayer.getStatus();
				
				if (playerStatus == Status.PLAYING) {
					// has the video reached the end
					if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
						mediaPlayer.seek(mediaPlayer.getStartTime());
						mediaPlayer.play();
					} else {
						mediaPlayer.pause();
						playButton.setText(">");
					}
				}
				
				if (playerStatus == Status.PAUSED || playerStatus == Status.HALTED || playerStatus == Status.STOPPED) {
					mediaPlayer.play();
					playButton.setText("||");
				}
			}
		});
		
		// attach a listener to the current time of the media player
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable obs) {
				updateValues();
			}
		});
		
		// attach a listener to the time slider and change video's location on click
		time.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable obs) {
				if (time.isPressed()) {
					mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(time.getValue() / 100));
				}
			}
		});
		
		// attach a listener to the volume slider and change the video's volume on click
		volume.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable obs) {
				mediaPlayer.setVolume(volume.getValue() / 100);
			}
		});
	}
	
	protected void updateValues() {
		Platform.runLater(new Runnable() {
			public void run() {
				// update time value of slider as a value to percentage
				time.setValue(mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
			}
		});
	}
}
