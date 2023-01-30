package com.johanekstroem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanekstroem.model.peppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.model.responseDTO.OrganizationDTO;
import com.johanekstroem.service.DTOService;
import com.johanekstroem.service.ExternalSources;
import com.johanekstroem.service.PeppolDirectoryService;
import com.johanekstroem.service.ViaductService;

import io.javalin.http.Context;

public class HandleRequestController {
   

    public static void searchQuery(Context ctx) {
        boolean isFullSearch = ctx.endpointHandlerPath().equals("/specific");

        List<OrganizationDTO> listOfOrganizationDTOs = new ArrayList<>();
        var futurePeppolDirectory = PeppolDirectoryService.peppolDirectoryLookup(ctx.queryString());
        var futureMockViaduct = ViaductService.mockViaductRequest(ctx.queryString());
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futurePeppolDirectory, futureMockViaduct)
                .thenAcceptAsync((Void) -> {
                    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            false);
                    try {
                        PeppolDirectoryPOJO resultFromPeppolDirectory = om.readValue(futurePeppolDirectory.get().body(), PeppolDirectoryPOJO.class);
                        PeppolDirectoryPOJO resultFromMockViaduct = om.readValue(futureMockViaduct.get().body(), PeppolDirectoryPOJO.class);

                        if (isFullSearch) {
                            DTOService.fullFetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                        } else {
                            DTOService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                            DTOService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromMockViaduct, ExternalSources.Viaduct);
                        }

                        ctx.json(listOfOrganizationDTOs);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        ctx.result("Something went wrong");
                    }
                });
        ctx.future(() -> combinedFuture);
    }

}
