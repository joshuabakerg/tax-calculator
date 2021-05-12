/*
 * Copyright Notice
 * ================
 * This file contains proprietary information of Discovery Health.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2021
 */

package za.co.joshuabakerg.taxcalculatorbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import za.co.joshuabakerg.taxcalculatorbackend.exceptions.ApiDocNotFoundException;

/**
 * @author Joshua Baker on 2021/05/12
 */
@RestController
public class ApiDocsController {

    private static final ObjectMapper YAML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    /**
     * A rest controller to serve the api docs
     *
     * @return The custom api-docs.
     */
    @GetMapping(value = {"/{version}/api-docs"}, produces = {"application/json", "application/json+hal"})
    @ResponseBody
    public LinkedHashMap<?, ?> getDocumentation(@PathVariable final String version) throws IOException {
        final ClassPathResource resource = new ClassPathResource(String.format("docs/%s/api.yml", version));
        if (!resource.exists()) {
            throw new ApiDocNotFoundException(version);
        }
        try (InputStream stream = resource.getInputStream()) {
            return YAML_OBJECT_MAPPER.readValue(stream, LinkedHashMap.class);
        }
    }

}
