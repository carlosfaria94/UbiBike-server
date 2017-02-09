package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface StationRepository extends MongoRepository<Station, String> {

    Collection<Station> findByName(@Param("name") String name);
    Optional<Station> findById(@Param("id") String id);
}
