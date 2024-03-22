// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
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
		titleField.textProperty().bind(m.title);
		
		Text textField = new Text();
		textField.textProperty().bind(m.text);
		
		previewField = new TextFlow(textField);

		setTop(titleField);
		setCenter(previewField);
	}
}
