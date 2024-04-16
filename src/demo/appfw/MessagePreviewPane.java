// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package demo.appfw;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;


/**
 * Message Preview Pane.
 */
public class MessagePreviewPane
	extends BorderPane
{
	private final Message message;
	private final Label titleField;
	private final Label previewField;
	private final Rectangle clip;
	
	
	public MessagePreviewPane(Message m)
	{
		this.message = m;
		
		clip = new Rectangle();
		clip.widthProperty().bind(widthProperty());
		clip.heightProperty().bind(heightProperty());
		
		titleField = new Label();
		titleField.textProperty().bind(m.titleProperty());
		titleField.setStyle("-fx-font-weight:bold;");
		
		previewField = new Label();
		previewField.setTextOverrun(OverrunStyle.CLIP);
		previewField.setMaxWidth(Double.MAX_VALUE);
		previewField.setMaxHeight(Double.MAX_VALUE);
		previewField.setWrapText(true);
		previewField.setAlignment(Pos.TOP_LEFT);
		previewField.setTextAlignment(TextAlignment.LEFT);
		previewField.textProperty().bind
		(
			Bindings.createStringBinding
			(
				() -> AppTools.contractWhitespace(m.getText()),
				m.textProperty()
			)
		);
		

		setTop(titleField);
		setCenter(previewField);
		setPadding(new Insets(2, 4, 2, 4));
		setClip(clip);
	}
}
