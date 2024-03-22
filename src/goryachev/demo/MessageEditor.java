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
	
	
	public MessageEditor()
	{
		titleField = new TextField();
		titleField.setStyle("-fx-font-size:150%; -fx-font-family:\"Iosevka Fixed SS16\"; -fx-font-weight:bold;");
		titleField.setEditable(false);
		
		messageField = new TextArea();
		messageField.setStyle("-fx-font-family:\"Iosevka Fixed SS16\"; -fx-font-weight:700;");
		messageField.setEditable(false);
		
		setTop(titleField);
		setCenter(messageField);
	}
	
	
	public void setMessage(Message m)
	{
		if(m == null)
		{
			titleField.setText(null);
			messageField.setText(null);
		}
		else 
		{
			titleField.setText(m.getTitle());
			messageField.setText(m.getText());
		}
	}
}
