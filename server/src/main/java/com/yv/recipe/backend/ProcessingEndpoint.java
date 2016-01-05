
package com.yv.recipe.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import requests.ProcessRequest;

/**
 * An endpoint to send images to the server in order to analyze them
 */
@Api(name = "process", version="v1", namespace = @ApiNamespace(ownerDomain = "backend.recipe.yv.com", ownerName = "backend.recipe.yv.com", packagePath = ""))
public class ProcessingEndpoint {
    private static final Logger log = Logger.getLogger(ProcessingEndpoint.class.getName());

    /**
     * Api Keys can be obtained from the google cloud console
     */

    @ApiMethod(name = "process.image", httpMethod = ApiMethod.HttpMethod.POST)
    public ProcessRequest processImage(ProcessRequest req) {
        ProcessRequest response = new ProcessRequest();
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("1");
        response.setId(responseBuilder.toString());
        return response;
    }

    @ApiMethod(name = "greet", httpMethod = ApiMethod.HttpMethod.GET)
    public ProcessRequest greetUser() {
        ProcessRequest response = new ProcessRequest();
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("greeting user");
        response.setId(responseBuilder.toString());
        return response;
    }
}
