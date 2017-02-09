package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/trajectories")
class TrajectoryRestController {

    private final TrajectoryRepository trajectoryRepository;
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addTrajectory(@PathVariable String userId, @RequestBody Trajectory input) {
        this.validateUser(userId);
        return this.userRepository
                .findById(userId)
                .map(user -> {
                    Trajectory newTrajectory = new Trajectory(
                            user.getId(),
                            input.distTravelled,
                            input.pointsEarned,
                            input.travelTime
                    );

                    Trajectory res = trajectoryRepository.save(newTrajectory); // Save new trajectory
                    user.setPoints(res.getPointsEarned()); // Update user points
                    userRepository.save(user); // Update user

                    HttpHeaders httpHeaders;
                    httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(res.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Trajectory> readTrajectory(@PathVariable String userId) {
        this.validateUser(userId);
        return this.trajectoryRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/{trajectoryId}", method = RequestMethod.GET)
    Trajectory readTrajectory(@PathVariable String userId, @PathVariable String trajectoryId) {
        this.validateUser(userId);
        return this.trajectoryRepository.findOne(trajectoryId);
    }

    @Autowired
    TrajectoryRestController(TrajectoryRepository trajectoryRepository, UserRepository userRepository) {
        this.trajectoryRepository = trajectoryRepository;
        this.userRepository = userRepository;
    }

    private void validateUser(String userId) {
        this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId));
    }
}
