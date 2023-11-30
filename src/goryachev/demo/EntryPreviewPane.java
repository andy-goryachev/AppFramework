// Copyright © 2023 Andy Goryachev <andy@goryachev.com>
package goryachev.demo;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * Entry Preview Pane.
 */
public class EntryPreviewPane
	extends BorderPane
{
	private final Entry entry;
	private final Label titleField;
	private final TextFlow previewField;
	
	
	public EntryPreviewPane(Entry en)
	{
		this.entry = en;
		
		titleField = new Label();
		titleField.textProperty().bind(en.title);
		
		Text textField = new Text();
		textField.textProperty().bind(en.text);
		
		previewField = new TextFlow(textField);

		setTop(titleField);
		setCenter(previewField);
	}
}
