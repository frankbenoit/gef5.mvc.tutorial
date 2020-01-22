package gef5.mvc.tutorial.model;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class TextNode {

	public final String POSITION_PROPERTY = "position";
	public final String TEXT_PROPERTY = "text";

	@XmlAttribute
	@XmlID
	String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

	@XmlElement
	private String text = "";

//	private Color color = Color.LIGHTSKYBLUE;

	@XmlElementWrapper(name = "Childs")
	@XmlElement(name = "TextNode")
	@XmlIDREF
	public List<TextNode> childs = new LinkedList<>();

	private TextNode parent;

	private Model model;

	public TextNode() {
	}

	public TextNode(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String textOld = this.text;
		this.text = text;
		pcs.firePropertyChange(TEXT_PROPERTY, textOld, text);
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void addChild(TextNode child) {
		childs.add(child);
		child.setParent(this);
		model.ensureRegistered(child);
	}

	void setParent(TextNode textNode) {
		parent = textNode;
	}

	public TextNode getParent() {
		return parent;
	}

	public boolean isRootNode() {
		return model.getRootNode() == this;
	}

}
