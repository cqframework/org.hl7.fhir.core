package org.hl7.fhir.r5.renderers;

import org.hl7.fhir.r5.renderers.utils.RenderingContext;
import org.hl7.fhir.r5.renderers.utils.Resolver;

public abstract class CanonicalResourceRenderer extends ResourceRenderer {
  public CanonicalResourceRenderer(RenderingContext context) {
    super(context);
  }

  public CanonicalResourceRenderer(RenderingContext context, Resolver.ResourceContext rcontext) {
    super(context, rcontext);
  }

}
