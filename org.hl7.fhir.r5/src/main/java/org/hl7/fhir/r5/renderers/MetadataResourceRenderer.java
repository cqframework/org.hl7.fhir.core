package org.hl7.fhir.r5.renderers;

import org.hl7.fhir.exceptions.DefinitionException;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.exceptions.FHIRFormatError;
import org.hl7.fhir.r5.model.*;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers;
import org.hl7.fhir.r5.renderers.utils.RenderingContext;
import org.hl7.fhir.r5.renderers.utils.Resolver;
import org.hl7.fhir.r5.terminologies.CodeSystemUtilities;
import org.hl7.fhir.r5.utils.ToolingExtensions;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class MetadataResourceRenderer extends CanonicalResourceRenderer {
  public MetadataResourceRenderer(RenderingContext context) {
    super(context);
  }

  public MetadataResourceRenderer(RenderingContext context, Resolver.ResourceContext rcontext) {
    super(context, rcontext);
  }

  protected boolean renderMetadata(XhtmlNode x, BaseWrappers.ResourceWrapper r) throws FHIRFormatError, DefinitionException, IOException {
    x.h3().tx("Summary");
    XhtmlNode tbl = x.table("grid");
    if (r.has("url")) {
      row(tbl, "Defining URL", r.get("url").primitiveValue());
    }
    if (r.has("version")) {
      row(tbl, "Version", r.get("version").primitiveValue());
    }
    BaseWrappers.PropertyWrapper identifiers = r.getChildByName("identifier");
    if (identifiers != null && identifiers.hasValues()) {
      for (BaseWrappers.BaseWrapper i : identifiers.getValues()) {
        row(tbl, "Identifier", display(i));
      }
    }
    if (r.has("name")) {
      row(tbl, "Name", r.get("name").primitiveValue());
    }
    if (r.has("title")) {
      row(tbl, "Title", r.get("title").primitiveValue());
    }
    row(tbl, "Status", r.get("status").primitiveValue());
    if (r.has("experimental")) {
      row(tbl, "Experimental", r.get("experimental").primitiveValue());
    }
    if (r.has("date")) {
      row(tbl, "Date", r.get("date").primitiveValue());
    }
    if (r.has("description")) {
      addMarkdown(row(tbl, "Definition"), r.get("description").primitiveValue());
    }
    if (r.has("publisher")) {
      row(tbl, "Publisher", r.get("publisher").primitiveValue());
    }
    // TODO: committeeLink
    //if (r.hasExtension(ToolingExtensions.EXT_WORKGROUP)) {
    //  renderCommitteeLink(row(tbl, "Committee"), r);
    //}
    if (r.has("copyright")) {
      addMarkdown(row(tbl, "Copyright"), r.get("copyright").primitiveValue());
    }
    // TODO: useContext
    //if (r.hasUseContext()) {
    //  XhtmlNode td = row(tbl, "Usage");
    //  XhtmlNode usageTbl = td.table("grid");
    //  for (UsageContext uc : r.getUseContext()) {
    //    row(usageTbl, display(uc.getCode()), display(uc.getValue()));
    //  }
    //}
    // TODO: Jurisdiction
    //if (r.hasJurisdiction()) {
    //  XhtmlNode td = row(tbl, "Jurisdiction");
    //  XhtmlNode tr = td.table("grid").tr();
    //  for (CodeableConcept j : r.getJurisdiction()) {
    //    tr.td().tx(display(j));
    //  }
    //}
    if (r.has("purpose")) {
      addMarkdown(row(tbl, "Purpose"), r.get("purpose").primitiveValue());
    }
    // TODO: Usage
    if (r.has("approvalDate")) {
      row(tbl, "Approved", r.get("approvalDate").primitiveValue());
    }
    if (r.has("lastReviewDate")) {
      row(tbl, "Last Reviewed", r.get("lastReviewDate").primitiveValue());
    }
    if (r.has("effectivePeriod")) {
      row(tbl, "Effective", r.get("effectivePeriod").primitiveValue());
    }
    // TODO: Topic
    return false;
  }

  protected boolean renderMetadata(XhtmlNode x, MetadataResource r) throws FHIRFormatError, IOException, DefinitionException {
    x.h3().tx("Summary");
    XhtmlNode tbl = x.table("grid");
    if (r.hasUrl()) {
      row(tbl, "Defining URL", r.getUrl());
    }
    if (r.hasVersion()) {
      row(tbl, "Version", r.getVersion());
    }
    if (r.hasIdentifier()) {
      for (Identifier i : r.getIdentifier()) {
        row(tbl, "Identifier", display(i));
      }
    }
    if (r.hasName()) {
      row(tbl, "Name", gt(r.getNameElement()));
    }
    if (r.hasTitle()) {
      row(tbl, "Title", gt(r.getTitleElement()));
    }
    row(tbl, "Status", r.getStatus().toCode());
    if (r.hasExperimental()) {
      row(tbl, "Experimental", gt(r.getExperimentalElement()));
    }
    if (r.hasDate()) {
      row(tbl, "Date", display(r.getDateElement()));
    }
    if (r.hasDescription()) {
      addMarkdown(row(tbl, "Definition"), r.getDescription());
    }
    if (r.hasPublisher()) {
      row(tbl, "Publisher", gt(r.getPublisherElement()));
    }
    if (r.hasExtension(ToolingExtensions.EXT_WORKGROUP)) {
      renderCommitteeLink(row(tbl, "Committee"), r);
    }
    if (r.hasCopyright()) {
      addMarkdown(row(tbl, "Copyright"), r.getCopyright());
    }
    if (r.hasUseContext()) {
      XhtmlNode td = row(tbl, "Usage");
      XhtmlNode usageTbl = td.table("grid");
      for (UsageContext uc : r.getUseContext()) {
        row(usageTbl, display(uc.getCode()), display(uc.getValue()));
      }
    }
    if (r.hasJurisdiction()) {
      XhtmlNode td = row(tbl, "Jurisdiction");
      XhtmlNode tr = td.table("grid").tr();
      for (CodeableConcept j : r.getJurisdiction()) {
        tr.td().tx(display(j));
      }
    }
    if (r.hasPurpose()) {
      addMarkdown(row(tbl, "Purpose"), r.getPurpose());
    }
    // TODO: Usage
    if (r.hasApprovalDate()) {
      row(tbl, "Approved", display(r.getApprovalDateElement()));
    }
    if (r.hasLastReviewDate()) {
      row(tbl, "Last Reviewed", display(r.getLastReviewDateElement()));
    }
    if (r.hasEffectivePeriod()) {
      row(tbl, "Effective", display(r.getEffectivePeriod()));
    }
    // TODO: Topic
    return false;
  }

  protected XhtmlNode row(XhtmlNode tbl, String name) {
    XhtmlNode tr = tbl.tr();
    XhtmlNode td = tr.td();
    td.tx(translate("ns.summary", name));
    return tr.td();
  }

  protected XhtmlNode row(XhtmlNode tbl, String name, String value) {
    XhtmlNode td = row(tbl, name);
    td.tx(value);
    return td;
  }

  protected boolean hasCT(BaseWrappers.PropertyWrapper prop, String type) throws UnsupportedEncodingException, FHIRException, IOException {
    if (prop != null) {
      for (BaseWrappers.BaseWrapper cd : prop.getValues()) {
        BaseWrappers.PropertyWrapper telecoms = cd.getChildByName("telecom");
        if (getContactPoint(telecoms, type) != null) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean hasCT(List<ContactDetail> list, String type) {
    for (ContactDetail cd : list) {
      for (ContactPoint t : cd.getTelecom()) {
        if (type.equals(t.getSystem().toCode())) {
          return true;
        }
      }
    }
    return false;
  }

  protected ContactPoint getContactPoint(BaseWrappers.PropertyWrapper telecoms, String value) throws UnsupportedEncodingException, FHIRException, IOException {
    for (BaseWrappers.BaseWrapper t : telecoms.getValues()) {
      if (t.has("system")) {
        String system = t.get("system").primitiveValue();
        if (value.equals(system)) {
          return (ContactPoint) t.getBase();
        }
      }
    }
    return null;
  }


  protected void renderParameter(XhtmlNode t, BaseWrappers.BaseWrapper p, boolean doco) throws UnsupportedEncodingException, FHIRException, IOException {
    XhtmlNode tr = t.tr();
    tr.td().tx(p.has("name") ? p.get("name").primitiveValue() : null);
    tr.td().tx(p.has("use") ? p.get("use").primitiveValue() : null);
    tr.td().tx(p.has("min") ? p.get("min").primitiveValue() : null);
    tr.td().tx(p.has("max") ? p.get("max").primitiveValue() : null);
    tr.td().tx(p.has("type") ? p.get("type").primitiveValue() : null);
    if (doco) {
      tr.td().tx(p.has("documentation") ? p.get("documentation").primitiveValue() : null);
    }
  }

  protected void renderParameter(XhtmlNode t, ParameterDefinition p, boolean doco) {
    XhtmlNode tr = t.tr();
    tr.td().tx(p.getName());
    tr.td().tx(p.getUse().getDisplay());
    tr.td().tx(p.getMin());
    tr.td().tx(p.getMax());
    tr.td().tx(p.getType().getDisplay());
    if (doco) {
      tr.td().tx(p.getDocumentation());
    }
  }

  protected void renderArtifact(XhtmlNode t, BaseWrappers.BaseWrapper ra, BaseWrappers.ResourceWrapper lib, boolean label, boolean display, boolean citation) throws UnsupportedEncodingException, FHIRException, IOException {
    XhtmlNode tr = t.tr();
    tr.td().tx(ra.has("type") ? ra.get("type").primitiveValue() : null);
    if (label) {
      tr.td().tx(ra.has("label") ? ra.get("label").primitiveValue() : null);
    }
    if (display) {
      tr.td().tx(ra.has("display") ? ra.get("display").primitiveValue() : null);
    }
    if (citation) {
      tr.td().markdown(ra.has("citation") ? ra.get("citation").primitiveValue() : null, "Citation");
    }
    if (ra.has("resource")) {
      renderCanonical(lib, tr.td(), ra.get("resource").primitiveValue());
    } else {
      tr.td().tx(ra.has("url") ? ra.get("url").primitiveValue() : null);
    }
  }

  protected void renderArtifact(XhtmlNode t, RelatedArtifact ra, Resource lib, boolean label, boolean display, boolean citation) throws IOException {
    XhtmlNode tr = t.tr();
    tr.td().tx(ra.getType().getDisplay());
    if (label) {
      tr.td().tx(ra.getLabel());
    }
    if (display) {
      tr.td().tx(ra.getDisplay());
    }
    if (citation) {
      tr.td().markdown(ra.getCitation(), "Citation");
    }
    if (ra.hasResource()) {
      renderCanonical(lib, tr.td(), ra.getResource());
    } else {
      tr.td().tx(ra.getUrl());
    }
  }

  protected void participantRow(XhtmlNode t, String label, BaseWrappers.BaseWrapper cd, boolean email, boolean phone, boolean url) throws UnsupportedEncodingException, FHIRException, IOException {
    XhtmlNode tr = t.tr();
    tr.td().tx(label);
    tr.td().tx(cd.get("name") != null ? cd.get("name").primitiveValue() : null);
    BaseWrappers.PropertyWrapper telecoms = cd.getChildByName("telecom");
    if (email) {
      renderContactPoint(tr.td(), getContactPoint(telecoms, "email"));
    }
    if (phone) {
      renderContactPoint(tr.td(), getContactPoint(telecoms, "phone"));
    }
    if (url) {
      renderContactPoint(tr.td(), getContactPoint(telecoms, "url"));
    }
  }

  protected void participantRow(XhtmlNode t, String label, ContactDetail cd, boolean email, boolean phone, boolean url) {
    XhtmlNode tr = t.tr();
    tr.td().tx(label);
    tr.td().tx(cd.getName());
    if (email) {
      renderContactPoint(tr.td(), cd.getEmail());
    }
    if (phone) {
      renderContactPoint(tr.td(), cd.getPhone());
    }
    if (url) {
      renderContactPoint(tr.td(), cd.getUrl());
    }
  }

}
