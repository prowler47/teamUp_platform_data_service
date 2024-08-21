package ua.dargunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dargunovskiy.entity.Founder;
import ua.dargunovskiy.entity.Project;
import ua.dargunovskiy.service.FounderService;
import ua.dargunovskiy.service.ProjectService;

import java.util.UUID;

@RestController
@RequestMapping("/founder")
public class FounderController {

    @Autowired
    private FounderService founderService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/addFounder")
    public void addFounder(@RequestBody Founder founder) {
            founderService.addFounder(founder);
    }

    @PostMapping("/addFounderWithUserId/{userId}")
    public void addFounder(@RequestBody Founder founder, @PathVariable("userId") UUID userId) {
        founderService.addFounder(founder, userId);
    }

    @PostMapping("/addProjectAsFounder/{founderId}")
    public void addProjectAsFounder(@RequestBody Project project, @PathVariable("founderId") UUID founderId) {
        founderService.addProjectAsFounder(project, founderId);
    }

    @DeleteMapping("/deleteFounder/{founderId}")
    public void deleteFounder(@PathVariable("founderId") UUID founderId) {
        founderService.deleteFounder(founderId);
    }
}
