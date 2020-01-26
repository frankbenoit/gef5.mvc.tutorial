package gef5.mvc.tutorial.model;

import java.io.*;
import java.util.*;

public class Model implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6693402486686028380L;

	LinkedList<TextNode> nodes = new LinkedList<>();

	public LinkedList<TextNode> getNodes() {
		return nodes;
	}

	public void doChanges() {
		for (TextNode tn : nodes) {
			tn.doChange();
		}
	}

	public void addNode(TextNode textNode) {
		nodes.add(textNode);
	}
}
