package gef5.mvc.tutorial;

import java.util.*;

import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.domain.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.viewer.*;

import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import gef5.mvc.tutorial.parts.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.effect.Light.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Gef5MvcTutorial1 extends Application {

	public static void main(String[] args) {
		System.setProperty("javafx.sg.warn", "true");
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		Injector injector = Guice.createInjector(createGuiceModule());

		IDomain domain = injector.getInstance(IDomain.class);

		// hook the (single) viewer into the stage
		IViewer viewer = domain.getAdapter(IViewer.class);
		
		configureStage(primaryStage, viewer, "GEF5 MVC Tutorial 1 - minimal MVC");

		// activate domain only after viewers have been hooked
		domain.activate();
		
		// set viewer contents
		viewer.getContents().setAll(createContents());
		
		configureGlobalVisualEffect(viewer);
	}

	private void configureStage(final Stage stage, IViewer viewer, String title) {
		Parent canvas = viewer.getCanvas();
		stage.setScene(new Scene(canvas));
		stage.setResizable(true);
		stage.setWidth(640);
		stage.setHeight(480);
		stage.setTitle(title);
		stage.sizeToScene();
		stage.show();
	}

	private void configureGlobalVisualEffect(IViewer viewer) {
		DropShadow outerShadow = new DropShadow();
		outerShadow.setRadius(3);
		outerShadow.setSpread(0.2);
		outerShadow.setOffsetX(3);
		outerShadow.setOffsetY(3);
		outerShadow.setColor(new Color(0.3, 0.3, 0.3, 0.5));

		Distant light = new Distant();
		light.setAzimuth(-135.0f);
		light.setElevation(70);

		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(1.0f);
		l.setSpecularConstant(2.0f);
		l.setSpecularExponent(40.0f);

		Blend effects = new Blend(BlendMode.MULTIPLY );
		effects.setTopInput(l);
		effects.setBottomInput(outerShadow);

		applyEffectOnContentLayer(viewer, effects);
	}

	private void applyEffectOnContentLayer(IViewer viewer, Blend effects) {
		// LayeredRootPart is the default impl for the root part, configured in MvcFxModule
		// see https://www.eclipse.org/forums/index.php/m/1820560/#msg_1820560
		((LayeredRootPart) viewer.getRootPart()).getContentLayer().setEffect(effects);
	}

	protected List<? extends Object> createContents() {
		return Collections.singletonList(new Model());
	}

	protected Module createGuiceModule() {
		return new MvcFxModule() {
			@Override
			protected void configure() {
				
				// set all defaults
				super.configure();

				bind(IContentPartFactory.class).to(ModelPartFactory.class);
			}
		};
	}
}
