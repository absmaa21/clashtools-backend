package at.htlkaindorf.clashtoolsbackend.repositories;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface EntityRepository extends CrudRepository<BaseEntity, Long> {
}
