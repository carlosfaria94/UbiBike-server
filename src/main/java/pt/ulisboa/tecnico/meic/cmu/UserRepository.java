package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Following the Repository Pattern to perform various operations involving User objects.
 * It gets these operations by extending MongoRepository, which in turn extends the
 * PagingAndSortingRepositry interface defined in Spring Data Commons.
 */
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(@Param("id") String id);
    Optional<User> findByEmail(@Param("email") String email);
}
