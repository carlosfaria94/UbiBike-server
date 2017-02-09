package pt.ulisboa.tecnico.meic.cmu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.meic.cmu.Security.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static pt.ulisboa.tecnico.meic.cmu.Security.Utils.checkHash;
import static pt.ulisboa.tecnico.meic.cmu.Security.Utils.checkSenderTimestamp;

@RestController
@RequestMapping("/users")
class UserRestController {
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addUser(@RequestBody User input) {
        // Create a new user and save in DB (MongoDB)
        User newUser = userRepository.save(new User(
                input.firstName,
                input.lastName,
                input.email,
                input.password,
                input.secretKey
        ));
        return new ResponseEntity<>(newUser, null, HttpStatus.CREATED); // Return user created and HTTP CREATED code
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<User> readUser() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    User readUserId(@PathVariable String userId) {
        this.validateUser(userId); // Validate the userId URL path if correspond to an user in DB
        return this.userRepository.findOne(userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@RequestBody User input) {
        Optional<User> user = userRepository.findByEmail(input.email);
        if (user.isPresent()) {
            if (user.get().password.equals(input.password)) {
                // User exist and password input its correct
                return new ResponseEntity<>(user.get(), null, HttpStatus.OK);
            } else {
                // User exist, but password is incorrect. Return NOT ACCEPTABLE header code
                return new ResponseEntity<>(null, null, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            // User doesn't exist. Return NOT FOUND header code
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{userId}/validatePointsReceived", method = RequestMethod.POST)
    ResponseEntity<?> validatePointsReceived(@PathVariable String userId, @RequestBody Validate input) {
        this.validateUser(userId); // Validate the userId URL path if correspond to an user in DB
        Validate request = new Validate( // Create a Validate object as request from this endpoint
                input.hmac,
                input.sender,
                input.receiverFirstName,
                input.timestamp,
                input.points
        );

        request.buildTuple(); // Build the tuple with the inputs

        Optional<User> sender = userRepository.findById(request.getSender()); // Async return of the sender user object
        Optional<User> receiver = userRepository.findById(userId); // Async return of the receiver user object

        if (sender.isPresent() && receiver.isPresent()) { // Check if sender and receiver exist in DB
            if (checkHash(request, sender.get().getSecretKey())) { // Compare the request HMAC with computed by the server
                // Compare the timestamp received by the request with the timestamp stored for each user
                if (!checkSenderTimestamp(request, sender.get().getTimestamp())) { // If timestamp exist the request is not valid
                    // Valid request

                    int timestamp = request.getTimestamp();
                    sender.get().addTimestamp(timestamp); // Set the user timestamp

                    int senderPoints = sender.get().getPoints();
                    int receiverPoints = receiver.get().getPoints();
                    int requestPoints = request.getPoints();

                    int totalSenderPoints = senderPoints - requestPoints;
                    int totalReceiverPoints = receiverPoints + requestPoints;

                    sender.get().setPoints(totalSenderPoints); // Update the sender points
                    receiver.get().setPoints(totalReceiverPoints); // Update the receiver points

                    // Update the sender points transactions to ensure consistency
                    Transaction newTransaction = new Transaction(
                            receiver.get().getId(),
                            requestPoints
                    );
                    ArrayList<Transaction> transactions = sender.get().getPointsTransactions();
                    transactions.add(newTransaction);
                    sender.get().setPointsTransactions(transactions);

                    // Save in DB the changes in each user (sender and receiver)
                    this.userRepository.save(sender.get());
                    this.userRepository.save(receiver.get());
                    return new ResponseEntity<>(null, null, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(null, null, HttpStatus.NOT_ACCEPTABLE); // Validation process failed. Reject.
    }

    @Autowired
    UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void validateUser(String userId) {
        this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId));
    }
}
