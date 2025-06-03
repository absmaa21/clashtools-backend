package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service for handling Category-related operations.
 * Since Category is now an enum, this service only provides methods for retrieving all categories.
 */
@Service
public class CategoryService {

    /**
     * Get all available categories
     * @return a list of all categories
     */
    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }
}
