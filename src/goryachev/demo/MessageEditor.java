// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


/**
 * Message Editor.
 */
public class MessageEditor
	extends BorderPane
{
	protected final TextField titleField;
	protected final TextArea messageField;
	private Message msg;
	
	
	public MessageEditor()
	{
		titleField = new TextField();
		titleField.setStyle("-fx-font-size:150%; -fx-font-family:\"Iosevka Fixed SS16\"; -fx-font-weight:bold;");
		
		messageField = new TextArea();
		messageField.setStyle("-fx-font-family:\"Iosevka Fixed SS16\";");
		messageField.setWrapText(true);
		
		setTop(titleField);
		setCenter(messageField);
	}
	
	
	public void setMessage(Message m)
	{
		if(msg != null)
		{
			titleField.textProperty().unbindBidirectional(msg.titleProperty());
			messageField.textProperty().unbindBidirectional(msg.textProperty());
		}

		msg = m;

		if(m == null)
		{
			titleField.setText(null);
			messageField.setText(null);
		}
		else 
		{
			titleField.textProperty().bindBidirectional(m.titleProperty());
			messageField.textProperty().bindBidirectional(m.textProperty());
		}
	}
}
