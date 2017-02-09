package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TrajectoryRepository extends MongoRepository<Trajectory, String> {

    Collection<Trajectory> findByUserId(@Param("id") String id);
}