package controllers;

import java.io.File;
import java.util.List;

import blockchain.Block;
import blockchain.BlockchainService;
import enums.Scenes;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import services.DateService;
import services.FileService;
import uSign.SceneManager;

public class DocumentPreview {
	@FXML
	Label start;
	
	@FXML
	VBox imagesPreview;
	
	SceneManager manager = new SceneManager();
	
	@FXML
	Label addedAt;
	
	@FXML
	Label title;
	
	@FXML
	Label comment;
	
	@FXML
	Label signature;
	
	private boolean resized = false;
	
	@FXML
	protected void initialize() {
		VBox.setMargin(imagesPreview, new Insets(50, 50, 50, 50));
		
		imagesPreview.widthProperty().addListener(event -> {
			if (imagesPreview.getWidth() == 0 || resized == true) {
				return;
			}
			
			resized = true;
			
			try {
				List<WritableImage> previewImages = FileService.getPreview(new File("C:\\Users\\Jarek\\Desktop\\signed\\" + BlockchainService.selectedBlock.getFileName()));

			    for (WritableImage generated : previewImages) {
		    		ImageView previewImage = new ImageView();
		    		
		    		previewImage.setImage(generated);
		    		previewImage.setPreserveRatio(true);
		    		previewImage.setFitWidth(imagesPreview.getBoundsInParent().getWidth() - 100);
		    		
		    		imagesPreview.getChildren().add(previewImage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		try {
			Block block = BlockchainService.selectedBlock;
			
			addedAt.setText("Dodano: " + DateService.toDate(block.timeStamp));
			title.setText("Dokument: " + block.data.fileName);
			comment.setText(block.data.comment == "" ? "Brak" : block.data.comment);
			
			boolean isValid = BlockchainService.selectedBlock.isValid();
			
			signature.setText(isValid ? "Sygnatura prawidłowa" : "Sygnatura nieprawidłowa");
			signature.getStyleClass().add(isValid ? "valid" : "invalid");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void backToHomepage() {		
		manager.openScene(start, Scenes.HOMEPAGE);
	}

	public void backToDocumentsList() {
		manager.openScene(start, Scenes.DOCUMENTS);
	}
}
