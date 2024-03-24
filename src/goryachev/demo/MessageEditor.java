// Copyright © 2023-2024 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import goryachev.fx.CssStyle;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


/**
 * Message Editor.
 */
public class MessageEditor
	extends BorderPane
{
	public final static CssStyle TITLE = new CssStyle();
	public final static CssStyle EDITOR = new CssStyle();
	protected final TextField titleField;
	protected final TextArea messageField;
	private Message msg;
	
	
	public MessageEditor()
	{
		titleField = new TextField();
		titleField.setStyle("-fx-font-size:150%; -fx-font-weight:bold;");
		TITLE.set(titleField);
		
		messageField = new TextArea();
		messageField.setWrapText(true);
		EDITOR.set(messageField);
		
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
