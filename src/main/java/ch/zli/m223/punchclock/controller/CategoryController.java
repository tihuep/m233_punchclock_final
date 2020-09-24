package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.config.WebConfiguration;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Controller class for Category Entity
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private WebConfiguration webConfiguration;

    /**
     * Constructor for Category Controller, receives injected dependencies
     * @param categoryService
     * @param webConfiguration
     */
    public CategoryController(CategoryService categoryService, WebConfiguration webConfiguration){
        this.categoryService = categoryService;
        this.webConfiguration = webConfiguration;
    }

    /**
     * Post Endpoint for Category
     * @param category Data coming from Client
     * @param token JWT Token
     * @return newly created Category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@Valid @RequestBody Category category, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return categoryService.createCategory(category, applicationUser);
    }

    /**
     * Get Endpoint for Category
     * @return List of all Categories
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    /**
     * Get Endpoint for Category
     * @param id ID to select Category
     * @return Single Category with the ID provided in Path
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getSingleCategory(@PathVariable Long id){
        Category category = categoryService.getSingleCategory(id);
        return category;
    }

    /**
     * Delete Endpoint for Category
     * @param id ID to delete Category
     * @param token JWT Token
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        categoryService.deleteCategory(id, applicationUser);
    }
}
