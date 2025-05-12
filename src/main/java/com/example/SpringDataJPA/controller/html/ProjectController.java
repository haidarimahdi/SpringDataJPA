package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final PersonService personService;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService, PersonService personService) {
        this.projectService = projectService;
        this.personService = personService;
    }

    /**
     * Show a list of all projects.
     */
    @GetMapping("/")
    public String showProjectIndex(Model model) {
        List<ProjectListDTO> projectsData = projectService.getProjectOverviewData();
        model.addAttribute("projectsData", projectsData);
        return "project-index";

    }

    /**
     * Show details for a specific project, including assigned persons and their timeslots for this project.
     */
    @GetMapping("/{id}")
    public String showProjectDetails(Model model, @PathVariable int id) {
        try {
            ProjectDetailDTO projectDetailDTO = projectService.getProjectDetails(id);
            model.addAttribute("projectDetails", projectDetailDTO);

            return "project-details";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id, e);
        }

    }

    /**
     * Show the form to create a new project.
     */
    @GetMapping("/new")
    public String showProjectForm(Model model) {
        model.addAttribute("projectDTO", new ProjectDTO());
        List<PersonDetailDTO> allPersons = personService.getAllPeople();
        model.addAttribute("allPersons", allPersons);
        return "project-form";
    }

    /**
     * Process the creation of a new project.
     */
    @PostMapping("/new")
    public String createProject(@ModelAttribute ProjectDTO projectDTO) {
        try {
            ProjectDetailDTO createdProject = projectService.createProject(projectDTO);
            return "redirect:/project/";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating project: " + e.getMessage());
        }
    }

    /**
     * Show the form to update an existing project.
     */
    @GetMapping("/update/{id}")
    public String showProjectUpdateForm(Model model, @PathVariable Integer id) {
        try {
            ProjectDTO projectDTO = projectService.getProjectDtoForUpdateForm(id);
            model.addAttribute("projectDTO", projectDTO);
            model.addAttribute("projectId", id);

            List<PersonDetailDTO> allPersons = personService.getAllPeople();
            model.addAttribute("allPersons", allPersons);
            Project project = null;

            return "project-update";

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found for update with id: " + id, e);
        }
    }

    /**
     * Process the update of an existing project.
     */
    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable Integer id, @ModelAttribute ProjectDTO projectDTO) {
        // projectService.updateProject handles DTO -> Entity mapping, finding, updating, and saving
        projectService.updateProject(id, projectDTO);
        return "redirect:/project/" + id;
    }

    /**
     * Process the deletion of a project.
     */
    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return "redirect:/project/";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
