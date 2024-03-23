// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * Message Preview Pane.
 */
public class MessagePreviewPane
	extends BorderPane
{
	private final Message message;
	private final Label titleField;
	private final TextFlow previewField;
	
	
	public MessagePreviewPane(Message m)
	{
		this.message = m;
		
		titleField = new Label();
		titleField.textProperty().bind(m.titleProperty());
		titleField.setStyle("-fx-font-weight:bold;");
		
		Text t = new Text();
		t.wrappingWidthProperty().bind(widthProperty());
		t.textProperty().bind
		(
			Bindings.createStringBinding
			(
				() -> AppTools.contractWhitespace(m.getText()),
				m.textProperty()
			)
		);
		
		previewField = new TextFlow(t);

		setTop(titleField);
		setCenter(previewField);
		setPadding(new Insets(2, 4, 2, 4));
	}
}
