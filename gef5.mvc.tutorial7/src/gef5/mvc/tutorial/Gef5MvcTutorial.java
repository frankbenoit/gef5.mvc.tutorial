package gef5.mvc.tutorial;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;

import javax.xml.bind.*;

import org.eclipse.core.commands.operations.*;
import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.mvc.fx.domain.*;
import org.eclipse.gef.mvc.fx.viewer.*;

import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Gef5MvcTutorial extends Application {

	private Model model;
	private JAXBContext jaxbContext;
	private IDomain domain;
	private IOperationHistory history;
	private IUndoContext undoContext;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		jaxbContext = JAXBContext.newInstance(Model.class, TextNode.class);

		Injector injector = Guice.createInjector(createGuiceModule());

		domain = injector.getInstance(IDomain.class);
		history = injector.getInstance(IOperationHistory.class);
		undoContext = injector.getInstance(IUndoContext.class);

		InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) domain.getAdapter(IViewer.class);

		HBox paneCtrl = new HBox();

		AnchorPane paneDraw = new AnchorPane();
		VBox vbox = new VBox(paneCtrl, paneDraw);
		// vbox.setPrefWidth(Double.MAX_VALUE);
		vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		Button btnUpdateModel = new Button("Random Changes");
		btnUpdateModel.setOnAction(e -> model.doChanges());
		btnUpdateModel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		Button btnUndo = new Button("Undo");
		btnUndo.setMaxWidth(Double.MAX_VALUE);
		btnUndo.setDisable(true);
		btnUndo.setOnAction(e -> {
			try {
				history.undo(undoContext, null, null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		Button btnRedo = new Button("Redo");
		btnRedo.setMaxWidth(Double.MAX_VALUE);
		btnRedo.setDisable(true);
		btnRedo.setOnAction(e -> {
			try {
				history.redo(undoContext, null, null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		history.addOperationHistoryListener(e -> {
			updateUnReDoButton(btnUndo, "Undo", e.getHistory()::getUndoHistory);
			updateUnReDoButton(btnRedo, "Redo", e.getHistory()::getRedoHistory);
		});

		paneCtrl.getChildren().addAll(btnUpdateModel, btnUndo, btnRedo);

		InfiniteCanvas drawingPane = viewer.getCanvas();
		drawingPane.clipContentProperty().set(true);
		paneDraw.getChildren().add(drawingPane);
		paneDraw.setPrefHeight(2000);
		AnchorPane.setTopAnchor(drawingPane, 10d);
		AnchorPane.setLeftAnchor(drawingPane, 10d);
		AnchorPane.setRightAnchor(drawingPane, 10d);
		AnchorPane.setBottomAnchor(drawingPane, 10d);

		primaryStage.setScene(new Scene(vbox));

		primaryStage.setResizable(true);
		primaryStage.setWidth(640);
		primaryStage.setHeight(480);
		primaryStage.setTitle("GEF4 MVC Tutorial 7 - Remove Node");

		primaryStage.show();

		domain.activate();

		viewer.getContents().setAll(createContents());
	}

	private void updateUnReDoButton(Button btn, String label, Function<IUndoContext, IUndoableOperation[]> getHist) {
		IUndoableOperation[] entries = getHist.apply(undoContext);
		btn.setDisable(entries.length == 0);
		if (entries.length == 0) {
			btn.setText(label);
		} else {
			IUndoableOperation currentEntry = entries[entries.length - 1];
			btn.setText(String.format("%s: %s", label, currentEntry.getLabel()));
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(model, new File("model.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List<? extends Object> createContents() {
		if (Files.isReadable(Paths.get("model.xml"))) {
			try {
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				model = (Model) jaxbUnmarshaller.unmarshal(new File("model.xml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (model == null) {
			model = new Model();
			model.addNode(new TextNode(20, 20, "First"));
			model.addNode(new TextNode(20, 120, "Second"));

		}

		return Collections.singletonList(model);
	}

	protected Module createGuiceModule() {
		return new GuiceModule();
	}
}
