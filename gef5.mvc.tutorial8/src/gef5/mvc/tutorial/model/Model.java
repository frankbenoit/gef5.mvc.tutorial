package gef5.mvc.tutorial.model;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Model {

	@XmlIDREF
	@XmlAttribute(name = "RootNode")
	private TextNode rootNode;

	@XmlElementWrapper(name = "AllNodes")
	@XmlElement(name = "Node")
	public TreeSet<TextNode> allNodes = new TreeSet<>((o1, o2) -> o1.id.compareTo(o2.id));

	private void setTextNodeParents(TextNode parent) {
		for (TextNode c : parent.childs) {
			c.setParent(parent);
			setTextNodeParents(c);
		}

	}

	public void init() {
		TreeSet<String> ids = new TreeSet<>();
		for (TextNode n : allNodes) {
			if (!ids.add(n.id)) {
				throw new RuntimeException();
			}
			n.setModel(this);
			ensureRegistered(n);
		}
		setTextNodeParents(rootNode);
	}

	public void setRootNode(TextNode rootNode) {
		this.rootNode = rootNode;
		ensureRegistered(rootNode);
	}

	public void ensureRegistered(TextNode child) {
		allNodes.add(child);
		child.setModel(this);
	}

	public TextNode getRootNode() {
		return rootNode;
	}

	public void getAllRelations(LinkedList<Object> res) {
		addRelations(res, rootNode);
	}

	private void addRelations(LinkedList<Object> res, TextNode n) {
		for (TextNode c : n.childs) {
			TextNodeRelation r = new TextNodeRelation();
			res.add(r);
			r.setParent(n);
			r.setChild(c);
			addRelations(res, c);
		}
	}

}
