package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.ProjectDTO;
import com.example.SpringDataJPA.dto.ProjectDetailDTO;
import com.example.SpringDataJPA.dto.ProjectListDTO;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;


    /**
     * Show list of all projects.
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
        ProjectDetailDTO projectDetailDTO = projectService.getProjectDetails(id);
        if (projectDetailDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id);
        }
        model.addAttribute("projectDetails", projectDetailDTO);

        return "project-details";
    }

    /**
     * Show the form to create a new project.
     */
    @GetMapping("/new")
    public String showProjectForm(Model model) {
        model.addAttribute("projectDTO", new ProjectDTO()); // DTO for form binding
        model.addAttribute("allPersons", personRepository.findAll()); // For assigning persons
        return "project-form";
    }

    /**
     * Process the creation of a new project.
     */
    @PostMapping("/new")
    public String createProject(@ModelAttribute ProjectDTO projectDTO) {
        Project createdProject = projectService.createProject(projectDTO);
        return "redirect:/project/" + createdProject.getId(); // Redirect to details page
    }

    /**
     * Show the form to update an existing project.
     */
    @GetMapping("/update/{id}")
    public String showProjectUpdateForm(Model model, @PathVariable Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ProjectDTO projectDTO = projectService.mapEntityToDto(project);

            model.addAttribute("projectDTO", projectDTO);
            model.addAttribute("projectId", id);
            model.addAttribute("allPersons", personRepository.findAll());
            // Pass currently assigned person IDs to pre-select in the form
            model.addAttribute("assignedPersonIds", project.getPersons().stream().map(Person::getId).collect(Collectors.toSet()));

            return "project-update";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found for update with id: " + id);
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
