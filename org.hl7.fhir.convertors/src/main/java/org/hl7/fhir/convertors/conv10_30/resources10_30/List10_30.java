package org.hl7.fhir.convertors.conv10_30.resources10_30;

import org.hl7.fhir.convertors.context.ConversionContext10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.Extension10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.Reference10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.complextypes10_30.CodeableConcept10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.complextypes10_30.Identifier10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.primitivetypes10_30.Boolean10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.primitivetypes10_30.DateTime10_30;
import org.hl7.fhir.convertors.conv10_30.datatypes10_30.primitivetypes10_30.String10_30;
import org.hl7.fhir.dstu2.model.List_;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.ListResource;
import org.hl7.fhir.exceptions.FHIRException;

public class List10_30 {

  public static org.hl7.fhir.dstu2.model.List_ convertList(org.hl7.fhir.dstu3.model.ListResource src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu2.model.List_ tgt = new org.hl7.fhir.dstu2.model.List_();
    ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyDomainResource(src, tgt);
    for (org.hl7.fhir.dstu3.model.Identifier t : src.getIdentifier())
      tgt.addIdentifier(Identifier10_30.convertIdentifier(t));
    if (src.hasTitleElement())
      tgt.setTitleElement(String10_30.convertString(src.getTitleElement()));
    if (src.hasCode())
      tgt.setCode(CodeableConcept10_30.convertCodeableConcept(src.getCode()));
    if (src.hasSubject())
      tgt.setSubject(Reference10_30.convertReference(src.getSubject()));
    if (src.hasSource())
      tgt.setSource(Reference10_30.convertReference(src.getSource()));
    if (src.hasEncounter())
      tgt.setEncounter(Reference10_30.convertReference(src.getEncounter()));
    if (src.hasStatus())
      tgt.setStatusElement(convertListStatus(src.getStatusElement()));
    if (src.hasDate())
      tgt.setDateElement(DateTime10_30.convertDateTime(src.getDateElement()));
    if (src.hasOrderedBy())
      tgt.setOrderedBy(CodeableConcept10_30.convertCodeableConcept(src.getOrderedBy()));
    if (src.hasMode())
      tgt.setModeElement(convertListMode(src.getModeElement()));
    for (org.hl7.fhir.dstu3.model.Annotation t : src.getNote()) tgt.setNote(t.getText());
    for (org.hl7.fhir.dstu3.model.ListResource.ListEntryComponent t : src.getEntry()) tgt.addEntry(convertListEntry(t));
    return tgt;
  }

  public static org.hl7.fhir.dstu3.model.ListResource convertList(org.hl7.fhir.dstu2.model.List_ src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu3.model.ListResource tgt = new org.hl7.fhir.dstu3.model.ListResource();
    ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyDomainResource(src, tgt);
    for (org.hl7.fhir.dstu2.model.Identifier t : src.getIdentifier())
      tgt.addIdentifier(Identifier10_30.convertIdentifier(t));
    if (src.hasTitleElement())
      tgt.setTitleElement(String10_30.convertString(src.getTitleElement()));
    if (src.hasCode())
      tgt.setCode(CodeableConcept10_30.convertCodeableConcept(src.getCode()));
    if (src.hasSubject())
      tgt.setSubject(Reference10_30.convertReference(src.getSubject()));
    if (src.hasSource())
      tgt.setSource(Reference10_30.convertReference(src.getSource()));
    if (src.hasEncounter())
      tgt.setEncounter(Reference10_30.convertReference(src.getEncounter()));
    if (src.hasStatus())
      tgt.setStatusElement(convertListStatus(src.getStatusElement()));
    if (src.hasDate())
      tgt.setDateElement(DateTime10_30.convertDateTime(src.getDateElement()));
    if (src.hasOrderedBy())
      tgt.setOrderedBy(CodeableConcept10_30.convertCodeableConcept(src.getOrderedBy()));
    if (src.hasMode())
      tgt.setModeElement(convertListMode(src.getModeElement()));
    if (src.hasNote())
      tgt.addNote(new org.hl7.fhir.dstu3.model.Annotation().setText(src.getNote()));
    for (org.hl7.fhir.dstu2.model.List_.ListEntryComponent t : src.getEntry()) tgt.addEntry(convertListEntry(t));
    return tgt;
  }

  public static org.hl7.fhir.dstu3.model.ListResource.ListEntryComponent convertListEntry(org.hl7.fhir.dstu2.model.List_.ListEntryComponent src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu3.model.ListResource.ListEntryComponent tgt = new org.hl7.fhir.dstu3.model.ListResource.ListEntryComponent();
    copyBackboneElement(src, tgt);
    if (src.hasFlag())
      tgt.setFlag(CodeableConcept10_30.convertCodeableConcept(src.getFlag()));
    if (src.hasDeletedElement())
      tgt.setDeletedElement(Boolean10_30.convertBoolean(src.getDeletedElement()));
    if (src.hasDate())
      tgt.setDateElement(DateTime10_30.convertDateTime(src.getDateElement()));
    if (src.hasItem())
      tgt.setItem(Reference10_30.convertReference(src.getItem()));
    return tgt;
  }

  public static org.hl7.fhir.dstu2.model.List_.ListEntryComponent convertListEntry(org.hl7.fhir.dstu3.model.ListResource.ListEntryComponent src) throws FHIRException {
    if (src == null || src.isEmpty())
      return null;
    org.hl7.fhir.dstu2.model.List_.ListEntryComponent tgt = new org.hl7.fhir.dstu2.model.List_.ListEntryComponent();
    copyBackboneElement(src, tgt);
    if (src.hasFlag())
      tgt.setFlag(CodeableConcept10_30.convertCodeableConcept(src.getFlag()));
    if (src.hasDeletedElement())
      tgt.setDeletedElement(Boolean10_30.convertBoolean(src.getDeletedElement()));
    if (src.hasDate())
      tgt.setDateElement(DateTime10_30.convertDateTime(src.getDateElement()));
    if (src.hasItem())
      tgt.setItem(Reference10_30.convertReference(src.getItem()));
    return tgt;
  }

  static public org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.ListResource.ListMode> convertListMode(org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.List_.ListMode> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      Enumeration<ListResource.ListMode> tgt = new Enumeration<>(new ListResource.ListModeEnumFactory());
      ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case WORKING:
                  tgt.setValue(ListResource.ListMode.WORKING);
                  break;
              case SNAPSHOT:
                  tgt.setValue(ListResource.ListMode.SNAPSHOT);
                  break;
              case CHANGES:
                  tgt.setValue(ListResource.ListMode.CHANGES);
                  break;
              default:
                  tgt.setValue(ListResource.ListMode.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.List_.ListMode> convertListMode(org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.ListResource.ListMode> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      org.hl7.fhir.dstu2.model.Enumeration<List_.ListMode> tgt = new org.hl7.fhir.dstu2.model.Enumeration<>(new List_.ListModeEnumFactory());
      ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case WORKING:
                  tgt.setValue(List_.ListMode.WORKING);
                  break;
              case SNAPSHOT:
                  tgt.setValue(List_.ListMode.SNAPSHOT);
                  break;
              case CHANGES:
                  tgt.setValue(List_.ListMode.CHANGES);
                  break;
              default:
                  tgt.setValue(List_.ListMode.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.List_.ListStatus> convertListStatus(org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.ListResource.ListStatus> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      org.hl7.fhir.dstu2.model.Enumeration<List_.ListStatus> tgt = new org.hl7.fhir.dstu2.model.Enumeration<>(new List_.ListStatusEnumFactory());
      ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case CURRENT:
                  tgt.setValue(List_.ListStatus.CURRENT);
                  break;
              case RETIRED:
                  tgt.setValue(List_.ListStatus.RETIRED);
                  break;
              case ENTEREDINERROR:
                  tgt.setValue(List_.ListStatus.ENTEREDINERROR);
                  break;
              default:
                  tgt.setValue(List_.ListStatus.NULL);
                  break;
          }
      }
      return tgt;
  }

  static public org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.ListResource.ListStatus> convertListStatus(org.hl7.fhir.dstu2.model.Enumeration<org.hl7.fhir.dstu2.model.List_.ListStatus> src) throws FHIRException {
      if (src == null || src.isEmpty())
          return null;
      Enumeration<ListResource.ListStatus> tgt = new Enumeration<>(new ListResource.ListStatusEnumFactory());
      ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyElement(src, tgt);
      if (src.getValue() == null) {
          tgt.setValue(null);
      } else {
          switch (src.getValue()) {
              case CURRENT:
                  tgt.setValue(ListResource.ListStatus.CURRENT);
                  break;
              case RETIRED:
                  tgt.setValue(ListResource.ListStatus.RETIRED);
                  break;
              case ENTEREDINERROR:
                  tgt.setValue(ListResource.ListStatus.ENTEREDINERROR);
                  break;
              default:
                  tgt.setValue(ListResource.ListStatus.NULL);
                  break;
          }
      }
      return tgt;
  }

  public static void copyBackboneElement(org.hl7.fhir.dstu2.model.BackboneElement src, org.hl7.fhir.dstu3.model.BackboneElement tgt) throws FHIRException {
    ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyBackboneElement(src,tgt);
    for (org.hl7.fhir.dstu2.model.Extension e : src.getModifierExtension()) {
      tgt.addModifierExtension(Extension10_30.convertExtension(e));
    }
  }

  public static void copyBackboneElement(org.hl7.fhir.dstu3.model.BackboneElement src, org.hl7.fhir.dstu2.model.BackboneElement tgt) throws FHIRException {
    ConversionContext10_30.INSTANCE.getVersionConvertor_10_30().copyBackboneElement(src,tgt);
    for (org.hl7.fhir.dstu3.model.Extension e : src.getModifierExtension()) {
      tgt.addModifierExtension(Extension10_30.convertExtension(e));
    }
  }
}