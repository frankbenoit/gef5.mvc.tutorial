package gef5.mvc.tutorial.policies;

import org.eclipse.core.commands.*;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.runtime.*;
import org.eclipse.gef.mvc.fx.operations.*;

import gef5.mvc.tutorial.parts.*;

public class ChangeTextNodeTextOperation extends AbstractOperation implements ITransactionalOperation {

	TextNodePart part;
	private String oldText;
	private String newText;

	public ChangeTextNodeTextOperation(TextNodePart part, String oldText, String newText) {
		super("Change Text in TextNode");
		this.part = part;
		this.oldText = oldText;
		this.newText = newText;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		part.getContent().setText(newText);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		execute(monitor, info);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		part.getContent().setText(oldText);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isNoOp() {
		return oldText.equals(newText);
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

}
