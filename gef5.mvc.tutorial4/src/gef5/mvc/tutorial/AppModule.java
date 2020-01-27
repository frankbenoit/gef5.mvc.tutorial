package gef5.mvc.tutorial;

import org.eclipse.gef.common.adapt.*;
import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.policies.*;
import org.eclipse.gef.mvc.fx.providers.*;

import com.google.inject.multibindings.*;

import gef5.mvc.tutorial.parts.*;

class AppModule extends MvcFxModule {
		@Override
		protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> mapBinder) {
			super.bindAbstractContentPartAdapters(mapBinder);
			// register (default) interaction policies (which are based on
			// viewer
			// models and do not depend on transaction policies)

			addMappingForDefaultRole(mapBinder,
					FocusAndSelectOnClickHandler.class);

			addMappingForDefaultRole(mapBinder,
					HoverOnHoverHandler.class);

			addMappingForRole(mapBinder,
					DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER,
					ShapeOutlineProvider.class );

			// geometry provider for selection handles
			addMappingForRole(mapBinder,
					DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER,
					ShapeOutlineProvider.class);

			addMappingForRole(mapBinder,
					DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER,
					ShapeOutlineProvider.class);

			// geometry provider for hover feedback
			addMappingForRole(mapBinder,
					DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER,
					ShapeOutlineProvider.class);

			// register resize/transform policies (writing changes also to model)
			// this ensures that if the content part implements ITransformableContentPart
			// that the get/set Transformations are called after dragging
			addMappingForDefaultRole(mapBinder,
					TransformPolicy.class);

			// interaction policies to relocate on drag (including anchored elements, which are linked)
			addMappingForDefaultRole(mapBinder,
					TranslateSelectedOnDragHandler.class);
		}

		private void addMappingForDefaultRole(MapBinder<AdapterKey<?>, Object> mapBinder, Class<?> cls) {
			mapBinder.addBinding(AdapterKey.defaultRole()).to(cls);
		}
		private void addMappingForRole(MapBinder<AdapterKey<?>, Object> mapBinder, String roleName, Class<?> cls) {
			mapBinder.addBinding(AdapterKey.role(roleName)).to(cls);
		}

		@Override
		protected void configure() {
			super.configure();
			bind(IContentPartFactory.class).toInstance(new ModelPartFactory());
		}
		
	}