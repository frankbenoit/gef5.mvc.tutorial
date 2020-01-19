package gef5.mvc.tutorial.policies;

import org.eclipse.core.commands.*;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.runtime.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.operations.*;

import gef5.mvc.tutorial.parts.*;

public class ChangeTextNodePositionOperation extends AbstractOperation implements ITransactionalOperation {

	private TextNodePart part;
	private Point oldPos;
	private Point newPos;

	public ChangeTextNodePositionOperation(TextNodePart part, Point oldPos, Point newPos) {
		super("Change TextNode Position");
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.part = part;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		part.setPosition(newPos);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		part.setPosition(oldPos);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isNoOp() {
		return false;
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

}
