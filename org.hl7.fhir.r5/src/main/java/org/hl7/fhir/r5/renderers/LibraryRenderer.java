package org.hl7.fhir.r5.renderers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hl7.fhir.exceptions.DefinitionException;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.exceptions.FHIRFormatError;
import org.hl7.fhir.r5.model.Annotation;
import org.hl7.fhir.r5.model.Attachment;
import org.hl7.fhir.r5.model.Base;
import org.hl7.fhir.r5.model.ContactDetail;
import org.hl7.fhir.r5.model.ContactPoint;
import org.hl7.fhir.r5.model.DataRequirement;
import org.hl7.fhir.r5.model.DomainResource;
import org.hl7.fhir.r5.model.Library;
import org.hl7.fhir.r5.model.ListResource;
import org.hl7.fhir.r5.model.ListResource.ListResourceEntryComponent;
import org.hl7.fhir.r5.model.ParameterDefinition;
import org.hl7.fhir.r5.model.Reference;
import org.hl7.fhir.r5.model.RelatedArtifact;
import org.hl7.fhir.r5.model.Resource;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.BaseWrapper;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.PropertyWrapper;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.ResourceWrapper;
import org.hl7.fhir.r5.renderers.utils.RenderingContext;
import org.hl7.fhir.r5.renderers.utils.Resolver.ResourceContext;
import org.hl7.fhir.r5.renderers.utils.Resolver.ResourceWithReference;
import org.hl7.fhir.r5.terminologies.CodeSystemUtilities;
import org.hl7.fhir.r5.utils.ToolingExtensions;
import org.hl7.fhir.utilities.Utilities;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

public class LibraryRenderer extends MetadataResourceRenderer {

  private static final int DATA_IMG_SIZE_CUTOFF = 4000;

  public LibraryRenderer(RenderingContext context) {
    super(context);
  }

  public LibraryRenderer(RenderingContext context, ResourceContext rcontext) {
    super(context, rcontext);
  }
  
  public boolean render(XhtmlNode x, Resource dr) throws FHIRFormatError, DefinitionException, IOException {
    return render(x, (Library) dr);
  }

  public boolean render(XhtmlNode x, ResourceWrapper lib) throws FHIRFormatError, DefinitionException, IOException {
    PropertyWrapper publishers = lib.getChildByName("contact");
    PropertyWrapper authors = lib.getChildByName("author");
    PropertyWrapper editors = lib.getChildByName("editor");
    PropertyWrapper reviewers = lib.getChildByName("reviewer");
    PropertyWrapper endorsers = lib.getChildByName("endorser");
    if ((publishers != null && publishers.hasValues()) || (authors != null && authors.hasValues()) || (editors != null && editors.hasValues()) || (reviewers != null && reviewers.hasValues()) || (endorsers != null && endorsers.hasValues())) {
      boolean email = hasCT(publishers, "email") || hasCT(authors, "email") || hasCT(editors, "email") || hasCT(reviewers, "email") || hasCT(endorsers, "email");
      boolean phone = hasCT(authors, "phone") || hasCT(editors, "phone") || hasCT(reviewers, "phone") || hasCT(endorsers, "phone"); 
      boolean url = hasCT(authors, "url") || hasCT(editors, "url") || hasCT(reviewers, "url") || hasCT(endorsers, "url"); 
      x.h2().tx("Participants");
      XhtmlNode t = x.table("grid");
      if (publishers != null) {
        for (BaseWrapper cd : publishers.getValues()) {
          participantRow(t, "Publisher", cd, email, phone, url);
        }
      }
      if (authors != null) {
        for (BaseWrapper cd : authors.getValues()) {
          participantRow(t, "Author", cd, email, phone, url);
        }
      }
      if (editors != null) {
        for (BaseWrapper cd : editors.getValues()) {
          participantRow(t, "Editor", cd, email, phone, url);
        }
      }
      if (reviewers != null) {
        for (BaseWrapper cd : reviewers.getValues()) {
          participantRow(t, "Reviewer", cd, email, phone, url);
        }
      }
      if (endorsers != null) {
        for (BaseWrapper cd : endorsers.getValues()) {
          participantRow(t, "Endorser", cd, email, phone, url);
        }
      }
    }
    PropertyWrapper artifacts = lib.getChildByName("relatedArtifact");
    if (artifacts != null && artifacts.hasValues()) {
      x.h2().tx("Related Artifacts");
      XhtmlNode t = x.table("grid");
      boolean label = false;
      boolean display = false;
      boolean citation = false;
      for (BaseWrapper ra : artifacts.getValues()) {
        label = label || ra.has("label");
        display = display || ra.has("display");
        citation = citation || ra.has("citation");
      }
      for (BaseWrapper ra : artifacts.getValues()) {
        renderArtifact(t, ra, lib, label, display, citation);
      }      
    }
    PropertyWrapper parameters = lib.getChildByName("parameter");
    if (parameters != null && parameters.hasValues()) {
      x.h2().tx("Parameters");
      XhtmlNode t = x.table("grid");
      boolean doco = false;
      for (BaseWrapper p : parameters.getValues()) {
        doco = doco || p.has("documentation");
      }
      for (BaseWrapper p : parameters.getValues()) {
        renderParameter(t, p, doco);
      }      
    }
    PropertyWrapper dataRequirements = lib.getChildByName("dataRequirement");
    if (dataRequirements != null && dataRequirements.hasValues()) {
      x.h2().tx("Data Requirements");
      for (BaseWrapper p : dataRequirements.getValues()) {
        renderDataRequirement(x, (DataRequirement) p.getBase());
      }      
    }
    PropertyWrapper contents = lib.getChildByName("content");
    if (contents != null) {
      x.h2().tx("Contents");          
      boolean isCql = false;
      int counter = 0;
      for (BaseWrapper p : contents.getValues()) {
        Attachment att = (Attachment) p.getBase();
        renderAttachment(x, att, isCql, counter, lib.getId());
        isCql = isCql || (att.hasContentType() && att.getContentType().startsWith("text/cql"));
        counter++;
      }
    }
    return false;
  }
    
  public boolean render(XhtmlNode x, Library lib) throws FHIRFormatError, DefinitionException, IOException {
    renderMetadata(x, lib);
    if (lib.hasContact() || lib.hasAuthor() || lib.hasEditor() || lib.hasReviewer() || lib.hasEndorser()) {
      boolean email = hasCT(lib.getContact(), "email") || hasCT(lib.getAuthor(), "email") || hasCT(lib.getEditor(), "email") || hasCT(lib.getReviewer(), "email") || hasCT(lib.getEndorser(), "email");
      boolean phone = hasCT(lib.getContact(), "phone") || hasCT(lib.getAuthor(), "phone") || hasCT(lib.getEditor(), "phone") || hasCT(lib.getReviewer(), "phone") || hasCT(lib.getEndorser(), "phone");
      boolean url = hasCT(lib.getContact(), "url") || hasCT(lib.getAuthor(), "url") || hasCT(lib.getEditor(), "url") || hasCT(lib.getReviewer(), "url") || hasCT(lib.getEndorser(), "url");
      x.h2().tx("Participants");
      XhtmlNode t = x.table("grid");
      for (ContactDetail cd: lib.getContact()) {
        participantRow(t, "Publisher", cd, email, phone, url);
      }
      for (ContactDetail cd : lib.getAuthor()) {
        participantRow(t, "Author", cd, email, phone, url);
      }
      for (ContactDetail cd : lib.getEditor()) {
        participantRow(t, "Editor", cd, email, phone, url);
      }
      for (ContactDetail cd : lib.getReviewer()) {
        participantRow(t, "Reviewer", cd, email, phone, url);
      }
      for (ContactDetail cd : lib.getEndorser()) {
        participantRow(t, "Endorser", cd, email, phone, url);
      }
    }
    if (lib.hasRelatedArtifact()) {
      x.h2().tx("Related Artifacts");
      XhtmlNode t = x.table("grid");
      boolean label = false;
      boolean display = false;
      boolean citation = false;
      for (RelatedArtifact ra : lib.getRelatedArtifact()) {
        label = label || ra.hasLabel();
        display = display || ra.hasDisplay();
        citation = citation || ra.hasCitation();
      }
      for (RelatedArtifact ra : lib.getRelatedArtifact()) {
        renderArtifact(t, ra, lib, label, display, citation);
      }      
    }
    if (lib.hasParameter()) {
      x.h2().tx("Parameters");
      XhtmlNode t = x.table("grid");
      boolean doco = false;
      for (ParameterDefinition p : lib.getParameter()) {
        doco = doco || p.hasDocumentation();
      }
      for (ParameterDefinition p : lib.getParameter()) {
        renderParameter(t, p, doco);
      }      
    }
    if (lib.hasDataRequirement()) {
      x.h2().tx("Data Requirements");
      for (DataRequirement p : lib.getDataRequirement()) {
        renderDataRequirement(x, p);
      }      
    }
    if (lib.hasContent()) {
      x.h2().tx("Contents");          
      boolean isCql = false;
      int counter = 0;
      for (Attachment att : lib.getContent()) {
        renderAttachment(x, att, isCql, counter, lib.getId());
        isCql = isCql || (att.hasContentType() && att.getContentType().startsWith("text/cql"));
        counter++;
      }
    }
    return false;
  }

  public void describe(XhtmlNode x, Library lib) {
    x.tx(display(lib));
  }

  public String display(Library lib) {
    return lib.present();
  }

  @Override
  public String display(Resource r) throws UnsupportedEncodingException, IOException {
    return ((Library) r).present();
  }

  @Override
  public String display(ResourceWrapper r) throws UnsupportedEncodingException, IOException {
    if (r.has("title")) {
      return r.children("title").get(0).getBase().primitiveValue();
    }
    return "??";
  }

  private void renderAttachment(XhtmlNode x, Attachment att, boolean noShowData, int counter, String baseId) {
    boolean ref = !att.hasData() && att.hasUrl();
    if (ref) {
      XhtmlNode p = x.para();
      if (att.hasTitle()) {
        p.tx(att.getTitle());
        p.tx(": ");
      }
      p.code().ah(att.getUrl()).tx(att.getUrl());
      p.tx(" (");
      p.code().tx(att.getContentType());
      p.tx(lang(att));
      p.tx(")");
    } else if (!att.hasData()) {
      XhtmlNode p = x.para();
      if (att.hasTitle()) {
        p.tx(att.getTitle());
        p.tx(": ");
      }
      p.code().tx("No Content");
      p.tx(" (");
      p.code().tx(att.getContentType());
      p.tx(lang(att));
      p.tx(")");
    } else {
      String txt = getText(att);
      if (isImage(att.getContentType())) {
        XhtmlNode p = x.para();
        if (att.hasTitle()) {
          p.tx(att.getTitle());
          p.tx(": (");
          p.code().tx(att.getContentType());
          p.tx(lang(att));
          p.tx(")");
        }
        else {
          p.code().tx(att.getContentType()+lang(att));
        }
        if (att.getData().length < LibraryRenderer.DATA_IMG_SIZE_CUTOFF) {
          x.img("data: "+att.getContentType()+">;base64,"+b64(att.getData()));
        } else {
          String filename = "Library-"+baseId+(counter == 0 ? "" : "-"+Integer.toString(counter))+"."+imgExtension(att.getContentType()); 
          x.img(filename);
        }        
      } else if (txt != null && !noShowData) {
        XhtmlNode p = x.para();
        if (att.hasTitle()) {
          p.tx(att.getTitle());
          p.tx(": (");
          p.code().tx(att.getContentType());
          p.tx(lang(att));
          p.tx(")");
        }
        else {
          p.code().tx(att.getContentType()+lang(att));
        }
        String prismCode = determinePrismCode(att);
        if (prismCode != null && !tooBig(txt)) {
          x.pre().code().setAttribute("class", "language-"+prismCode).tx(txt);
        } else {
          x.pre().code().tx(txt);
        }
      } else {
        XhtmlNode p = x.para();
        if (att.hasTitle()) {
          p.tx(att.getTitle());
          p.tx(": ");
        }
        p.code().tx("Content not shown - (");
        p.code().tx(att.getContentType());
        p.tx(lang(att));
        p.tx(", size = "+Utilities.describeSize(att.getData().length)+")");
      }
    }    
  }

  private boolean tooBig(String txt) {
    return txt.length() > 16384;
  }

  private String imgExtension(String contentType) {
    if (contentType != null && contentType.startsWith("image/")) {
      if (contentType.startsWith("image/png")) {
        return "png";
      }
      if (contentType.startsWith("image/jpeg")) {
        return "jpg";
      }
    }
    return null;
  }

  private String b64(byte[] data) {
    byte[] encodeBase64 = Base64.encodeBase64(data);
    return new String(encodeBase64);
  }

  private boolean isImage(String contentType) {
    return imgExtension(contentType) != null;
  }

  private String lang(Attachment att) {
    if (att.hasLanguage()) {
      return ", language = "+describeLang(att.getLanguage());
    }
    return "";
  }

  private String getText(Attachment att) {
    try {
      try {
        String src = new String(att.getData(), "UTF-8");
        if (checkString(src)) {
          return src;
        }
      } catch (Exception e) {
        // ignore
      }
      try {
        String src = new String(att.getData(), "UTF-16");
        if (checkString(src)) {
          return src;
        }
      } catch (Exception e) {
        // ignore
      }
      try {
        String src = new String(att.getData(), "ASCII");
        if (checkString(src)) {
          return src;
        }
      } catch (Exception e) {
        // ignore
      }
      return null;      
    } catch (Exception e) {
      return null;
    }
  }

  public boolean checkString(String src) {
    for (char ch : src.toCharArray()) {
      if (ch < ' ' && ch != '\r' && ch != '\n' && ch != '\t') {
        return false;
      }
    }
    return true;
  }

  private String determinePrismCode(Attachment att) {
    if (att.hasContentType()) {
      String ct = att.getContentType();
      if (ct.contains(";")) {
        ct = ct.substring(0, ct.indexOf(";"));
      }
      switch (ct) {
      case "text/html" : return "html";
      case "text/xml" : return "xml";
      case "application/xml" : return "xml";
      case "text/markdown" : return "markdown";
      case "application/js" : return "JavaScript";
      case "application/css" : return "css";
      case "text/x-csrc" : return "c";
      case "text/x-csharp" : return "csharp";
      case "text/x-c++src" : return "cpp";
      case "application/graphql" : return "graphql";
      case "application/x-java" : return "java";
      case "application/json" : return "json";
      case "text/json" : return "json";
      case "application/liquid" : return "liquid";
      case "text/x-pascal" : return "pascal";
      case "text/x-python" : return "python";
      case "text/x-rsrc" : return "r";
      case "text/x-ruby" : return "ruby";
      case "text/x-sas" : return "sas";
      case "text/x-sql" : return "sql";
      case "application/typescript" : return "typescript";
      case "text/cql" : return "sql"; // not that bad...
      }
      if (att.getContentType().contains("json+") || att.getContentType().contains("+json")) {
        return "json";
      }
      if (att.getContentType().contains("xml+") || att.getContentType().contains("+xml")) {
        return "xml";
      }
    }
    return null;
  }
  
  
}
