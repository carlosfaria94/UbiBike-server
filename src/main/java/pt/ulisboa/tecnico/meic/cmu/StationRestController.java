package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/stations")
public class StationRestController {

    private final StationRepository stationRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addStation(@RequestBody Station input) {
        Station res = stationRepository.save(new Station(
                input.name,
                input.location,
                input.bikesAvailable,
                input.latitude,
                input.longitude
        ));
        HttpHeaders httpHeaders;
        httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(res.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Station> readAllStations() {
        return this.stationRepository.findAll();
    }

    @RequestMapping(value = "/{stationId}", method = RequestMethod.GET)
    Station readStationId(@PathVariable String stationId) {
        this.validateStation(stationId);
        return this.stationRepository.findOne(stationId);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    Collection<Station> findByName(@RequestParam("name") String name) {
        return this.stationRepository.findByName(name);
    }

    @RequestMapping(value = "/{stationId}/bookingBike", method = RequestMethod.GET)
    ResponseEntity<?> bookingBike(@PathVariable String stationId) {
        this.validateStation(stationId);
        return this.stationRepository
                .findById(stationId)
                .map(station -> {
                    if (station.getBikesAvailable() <= 0) {
                        return new ResponseEntity<>(null, null, HttpStatus.NOT_ACCEPTABLE);
                    } else {
                        station.decrementBikesAvailable();
                        this.stationRepository.save(station);
                        return new ResponseEntity<>(null, null, HttpStatus.OK);
                    }
                }).get();
    }

    @RequestMapping(value = "/{stationId}/bikeReturned", method = RequestMethod.GET)
    ResponseEntity<?> bikeReturned(@PathVariable String stationId) {
        this.validateStation(stationId);
        return this.stationRepository
                .findById(stationId)
                .map(station -> {
                    station.incrementBikesAvailable();
                    this.stationRepository.save(station);
                    return new ResponseEntity<>(null, null, HttpStatus.OK);
                }).get();
    }

    @Autowired
    StationRestController(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    private void validateStation(String userId) {
        this.stationRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId));
    }
}
