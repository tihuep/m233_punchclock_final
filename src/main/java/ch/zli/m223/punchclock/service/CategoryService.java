package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.error_handling.ForbiddenException;
import ch.zli.m223.punchclock.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category, ApplicationUser user){
        if (!user.getRole().getAdmin())
            throw new ForbiddenException("Only admins allowed");
        return categoryRepository.saveAndFlush(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getSingleCategory(Long id){
        return categoryRepository.findById(id).get();
    }

    public void deleteCategory(Long id, ApplicationUser user){
        if (!user.getRole().getAdmin())
            throw new ForbiddenException("Only admins allowed");
        categoryRepository.deleteById(id);
    }

}
