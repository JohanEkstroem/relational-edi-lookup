package com.johanekstroem.service;

import java.util.List;

import com.johanekstroem.model.ResponseDTO.KeyValuePairSources;
import com.johanekstroem.model.ResponseDTO.OrganizationDTO;

public class DTOService {
    
    public static void addIdentifier(OrganizationDTO organization, KeyValuePairSources newIdentifier) {
        String newIdentifierKey = newIdentifier.getKey().trim().toLowerCase();
        String newIdentifierValue = newIdentifier.getValue().trim().toLowerCase();
        if (newIdentifierKey.equals("iso6523-actorid-upis")) {
            newIdentifier.setKey(IdentifiersENUM.PEPPOLID.toString());
        }

        // If there's no match -> add newIdentifier
        if (organization.getCompanyIdentifier().stream().noneMatch(
                x -> x.getKey().trim().toLowerCase().equals(newIdentifierKey)
                        && x.getValue().trim().toLowerCase().equals(newIdentifierValue))) {
            // organization.getCompanyIdentifier().add(newIdentifier);
            organization.addCompanyIdentifier(newIdentifier);
        } else {
            // Ifrån organization.getCompanyIdentifier(),addressera raden som matchar
            // newIdentifier.getKey(), newIdentifier.getValue() och lägg till en ny source i
            // listan.
            for (KeyValuePairSources element : organization.getCompanyIdentifier()) {
                String elementKey = element.getKey().trim().toLowerCase();
                String elementValue = element.getValue().trim().toLowerCase();

                if (elementKey.equals(newIdentifierKey) && elementValue.equals(newIdentifierValue)) {
                    // Check if list of sources already have externalSources -> Add source to
                    // companyIdentifierList
                    if (!element.getListOfSources().contains(newIdentifier.getListOfSources().get(0))) {
                        // element.getListOfSources().add(newIdentifier.getListOfSources().get(0));
                        element.addListOfSources(newIdentifier.getListOfSources().get(0));
                    }
                }
            }
        }
    }

    public static void findOrganizationAndUpdateListOfOrganizations(List<OrganizationDTO> listOfOrganizations,
            OrganizationDTO organizationProspect) {

        var identifiersToCheck = organizationProspect.getCompanyIdentifier();

        // Hämta ut alla organisationer i listan listOfOrganizations som har någon match
        // i "companyIdentifiers" någon av identifierarna i identifiersToCheck (som i
        // sin
        // tur kommer från det nya, hämtade, datat från API:et)
        OrganizationDTO foundOrganization = null;

        try {
            foundOrganization = listOfOrganizations.stream()
                    .filter(x -> x.getCompanyIdentifier().stream().anyMatch(z -> identifiersToCheck.stream()
                            .anyMatch(y -> z.getKey().equals(y.getKey()) && z.getValue().equals(y.getValue()))))
                    .findFirst().get();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (foundOrganization == null) {
            // det finns ingen organisation i listan som passar med den nyhämtade datat i
            // "matchen"
            listOfOrganizations.add(organizationProspect);
        } else {
            // det finns organisation i listan som passar med den nyhämtade datat i
            // "matchen", då fyller vi på med identifierare som inte redan finns i
            // organisationen från listan
            fillOrganizationWithNewIds(organizationProspect, foundOrganization);
        }

    }

    public static void fillOrganizationWithNewIds(OrganizationDTO organizationDTOFromMatch,
            OrganizationDTO existingOrganizationDTO) {

        for (KeyValuePairSources key : organizationDTOFromMatch.getCompanyIdentifier()) {

            addIdentifier(existingOrganizationDTO, key);
        }

    }

    public static String getIdentifier(String identifierString) {
        identifierString = identifierString.trim().replace(" ", "");
        for (IdentifiersENUM identifier : IdentifiersENUM.values()) {
            String identifierValue = identifier.toString();

            if (identifierString.equals(identifierValue)) {
                return identifierValue;
            }
        }
        return null;
    }

}
