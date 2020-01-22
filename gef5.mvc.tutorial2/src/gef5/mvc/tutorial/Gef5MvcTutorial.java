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

public class Gef5MvcTutorial extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		Injector injector = Guice.createInjector(createGuiceModule());

		IDomain domain = injector.getInstance(IDomain.class);

		// hook the (single) viewer into the stage
		IViewer viewer = domain.getAdapter(IViewer.class);

		configureStage(primaryStage, viewer, "GEF5 MVC Tutorial 2 - Node with text");

		// activate domain only after viewers have been hooked
		domain.activate();

		// set viewer contents
		viewer.getContents().setAll(createContents());
		
		viewer.getRootPart().getVisual().setEffect(createShadowEffect());
	}

	private void configureStage(final Stage stage, IViewer viewer, String title) {
		stage.setScene(new Scene(viewer.getCanvas()));

		stage.setResizable(true);
		stage.setWidth(640);
		stage.setHeight(480);
		stage.setTitle(title);
		stage.sizeToScene();
		stage.show();
	}
	
	private static Effect createShadowEffect() {
		DropShadow outerShadow = new DropShadow();
		outerShadow.setRadius(3);
		outerShadow.setSpread(0.2);
		outerShadow.setOffsetX(3);
		outerShadow.setOffsetY(3);
		outerShadow.setColor(new Color(0.3, 0.3, 0.3, 0.5));

		Distant light = new Distant();
		light.setAzimuth(-135.0f);

		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(1.0f);
		l.setSpecularConstant(2.0f);
		l.setSpecularExponent(40.0f);

		Blend effects = new Blend(BlendMode.MULTIPLY );
//		effects.setTopInput(l);
		effects.setBottomInput(outerShadow);

		return effects;
	}

	protected List<? extends Object> createContents() {
		InverterModel inv1 = new InverterModel( 120, 210, Orientation.EAST );
		InverterModel inv2 = new InverterModel( 180, 210, Orientation.NORTH );
		return Arrays.asList(new TextModel(), inv1, inv2 );
	}

	protected Module createGuiceModule() {
		return new MvcFxModule() {
			@Override
			protected void configure() {
				super.configure();

				binder().bind(new TypeLiteral<IContentPartFactory>() {
				}).toInstance(new ModelPartFactory());

			}
		};
	}
}
