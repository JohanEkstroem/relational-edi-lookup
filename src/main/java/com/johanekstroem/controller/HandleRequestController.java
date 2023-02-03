package com.johanekstroem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanekstroem.model.PeppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.model.ResponseDTO.OrganizationDTO;
import com.johanekstroem.service.ExternalSources;
import com.johanekstroem.service.serviceSources.PeppolDirectoryService;
import com.johanekstroem.service.serviceSources.ViaductService;

import io.javalin.http.Context;

public class HandleRequestController {
   

    public static void searchQuery(Context ctx) {
        boolean isFullSearch = ctx.endpointHandlerPath().equals("/specific");
        List<OrganizationDTO> listOfOrganizationDTOs = new ArrayList<>();

        var futurePeppolDirectory = PeppolDirectoryService.peppolDirectoryLookup(ctx.queryString());
        var futureMockViaduct = ViaductService.mockViaductRequest(ctx.queryString());
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futurePeppolDirectory, futureMockViaduct)
                .thenAcceptAsync((Void) -> {
                    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        PeppolDirectoryPOJO resultFromPeppolDirectory = om.readValue(futurePeppolDirectory.get().body(), PeppolDirectoryPOJO.class);
                        PeppolDirectoryPOJO resultFromMockViaduct = om.readValue(futureMockViaduct.get().body(), PeppolDirectoryPOJO.class);
                        if (isFullSearch) {
                            PeppolDirectoryService.fullFetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                            ViaductService.fullFetchFromViaduct(listOfOrganizationDTOs, resultFromMockViaduct, ExternalSources.Viaduct);
                        } else {
                            PeppolDirectoryService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                            ViaductService.fetchFromViaduct(listOfOrganizationDTOs, resultFromMockViaduct, ExternalSources.Viaduct);
                        }

                        ctx.json(listOfOrganizationDTOs);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        ctx.result("Something went wrong");
                    }
                });
        ctx.future(() -> combinedFuture);
    }

}
