package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.Entity;
import org.springframework.data.repository.CrudRepository;

public interface EntityRepository extends CrudRepository<Entity, Long> {
}
