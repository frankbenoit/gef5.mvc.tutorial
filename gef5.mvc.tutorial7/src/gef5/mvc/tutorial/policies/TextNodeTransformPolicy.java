package gef5.mvc.tutorial.policies;

import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.operations.*;
import org.eclipse.gef.mvc.fx.policies.*;

import gef5.mvc.tutorial.parts.*;

public class TextNodeTransformPolicy extends TransformPolicy {

	@Override
	public ITransactionalOperation commit() {
		ITransactionalOperation visualOperation = super.commit();
		ITransactionalOperation modelOperation = createUpdateModelOperation();
		ForwardUndoCompositeOperation commit = new ForwardUndoCompositeOperation("Translate()");
		if (visualOperation != null) {
			commit.add(visualOperation);
		}
		if (modelOperation != null) {
			commit.add(modelOperation);
		}
		return commit.unwrap(true);
	}

	private ITransactionalOperation createUpdateModelOperation() {
		TextNodePart part = (TextNodePart) getHost();
		AffineTransform transform = part.getAdapter(TransformPolicy.class).getCurrentTransform();
		Point newPos = new Point(transform.getTranslateX(), transform.getTranslateY());
		Point oldPos = part.getContent().getPosition();
		return new ChangeTextNodePositionOperation((TextNodePart) getHost(), oldPos, newPos);
	}
}
