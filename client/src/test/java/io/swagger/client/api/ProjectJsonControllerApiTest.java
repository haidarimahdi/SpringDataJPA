/*
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.model.ProjectDTO;
import io.swagger.client.model.ProjectDetailDTO;
import io.swagger.client.model.ProjectListDTO;
import org.junit.Test;
import org.junit.Ignore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for ProjectJsonControllerApi
 */
@Ignore
public class ProjectJsonControllerApiTest {

    private final ProjectJsonControllerApi api = new ProjectJsonControllerApi();

    /**
     * 
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void createProjectJSONTest() throws Exception {
        ProjectDTO body = null;
        ProjectDetailDTO response = api.createProjectJSON(body);

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void deleteProjectJSONTest() throws Exception {
        Integer id = null;
        api.deleteProjectJSON(id);

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getAllProjectsJSONTest() throws Exception {
        List<ProjectListDTO> response = api.getAllProjectsJSON();

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void getProjectDetailJSONTest() throws Exception {
        Integer id = null;
        ProjectDetailDTO response = api.getProjectDetailJSON(id);

        // TODO: test validations
    }
    /**
     * 
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateProjectJSONTest() throws Exception {
        ProjectDTO body = null;
        Integer id = null;
        ProjectDetailDTO response = api.updateProjectJSON(body, id);

        // TODO: test validations
    }
}
