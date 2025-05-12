package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.ProjectDTO;
import com.example.SpringDataJPA.dto.ProjectDetailDTO;
import com.example.SpringDataJPA.dto.ProjectListDTO;
import com.example.SpringDataJPA.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing Project entities in JSON format.
 * This class provides endpoints for creating, updating, deleting,
 * and retrieving project details in JSON format.
 */
@RestController
@RequestMapping("/project")
public class ProjectJSONController {

    private final ProjectService projectService;

    @Autowired
    public ProjectJSONController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Get a list of all projects (basic info).
     */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<ProjectListDTO>> getAllProjectsJSON() {
        List<ProjectListDTO> projectsData = projectService.getProjectOverviewData();
        return ResponseEntity.ok(projectsData);
    }

    /**
     * Get the details of a project by ID in JSON format.
     *
     * @param id the ID of the project
     * @return ResponseEntity containing the ProjectDetailDTO if found, or 404 Not Found status
     */
    @GetMapping(value = "{id}.json", produces = "application/json")
    public ResponseEntity<ProjectDetailDTO> getProjectDetailJSON(@PathVariable("id") Integer id) {
        try {
            ProjectDetailDTO dto = projectService.getProjectDetails(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Create a new project from the provided ProjectDTO.
     *
     * @param projectDTO the DTO containing the details of the project to create
     * @return ResponseEntity containing the created ProjectDetailDTO and the location URI
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProjectDetailDTO> createProjectJSON(@RequestBody ProjectDTO projectDTO) {
        try {
            ProjectDetailDTO createdProjectDto = projectService.createProject(projectDTO);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}.json")
                    .buildAndExpand(createdProjectDto.getId()).toUri();

            return ResponseEntity.created(location).body(createdProjectDto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating project: " + e.getMessage(), e);
        }
    }

    /**
     * Update an existing project by ID with the provided ProjectDTO.
     *
     * @param id the ID of the project to update
     * @param projectDTO the DTO containing the updated details of the project
     * @return ResponseEntity containing the updated ProjectDetailDTO if found, or 404 Not Found status
     */
    @PostMapping(value = "{id}.json", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProjectDetailDTO> updateProjectJSON(@PathVariable("id") Integer id,
                                                              @RequestBody ProjectDTO projectDTO) {
        try {
            ProjectDetailDTO updatedProjectDto = projectService.updateProject(id, projectDTO);
            return ResponseEntity.ok(updatedProjectDto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error updating project: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a project by ID.
     *
     * @param id the ID of the project to delete
     * @return ResponseEntity with no content if successful, or 404 Not Found status if the project does not exist
     */
    @DeleteMapping(value = "{id}.json")
    public ResponseEntity<Void> deleteProjectJSON(@PathVariable("id") Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

