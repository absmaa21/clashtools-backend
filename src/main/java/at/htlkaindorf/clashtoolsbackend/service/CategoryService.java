package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling Category-related operations.
 * Since Category is now an enum, this service only provides methods for retrieving all categories.
 */
@Service
public class CategoryService {

    /**
     * Gets all available categories from the Category enum
     *
     * @return Immutable list of all categories
     */
    public List<Category> getAllCategories() {
        return List.of(Category.values());
    }
}
