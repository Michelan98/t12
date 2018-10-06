package ca.mcgill.ecse321.webservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.webservice.model.Registration;
import ca.mcgill.ecse321.webservice.service.RegistrationService;
import ca.mcgill.ecse321.webservice.model.User;
import ca.mcgill.ecse321.webservice.model.Trip;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	//may have to delete these and replace with service classes
	@Autowired
	private ca.mcgill.ecse321.webservice.repository.UserRepository userRepository;
	@Autowired
	private ca.mcgill.ecse321.webservice.repository.TripRepository tripRepository;
	
	@RequestMapping(value="/registrations", method = RequestMethod.GET)
	public ResponseEntity<?> getAllRegistrations() {
		Iterable<Registration> registrationList = registrationService.getAllRegistrations();
		return new ResponseEntity<>(registrationList, HttpStatus.OK);
	}
	
	// can do the following method by importing user repository or service and then geting the user and then getting its registrations
	
	@RequestMapping(value="/registrationsByUser/{userID}", method = RequestMethod.GET)   
	public ResponseEntity<?> getRegistrationsByUserID(@PathVariable long userID) {
		Optional<User> userO = userRepository.findById(userID);
		User user = userO.get();
		
		
		Iterable<Registration> registrationList = user.getRegistrations();
		return new ResponseEntity<>(registrationList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/registrationsByTrip/{tripID}", method = RequestMethod.GET)   
	public ResponseEntity<?> getRegistrationsByTripID(@PathVariable long tripID) {
		Optional<Trip> tripO = tripRepository.findById(tripID);
		Trip trip = tripO.get();
		
		
		Iterable<Registration> registrationList = trip.getRegistrations();
		return new ResponseEntity<>(registrationList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/registrations/{registrationID}", method = RequestMethod.PUT)   
	public ResponseEntity<?> updateRegistration(@PathVariable long registrationID, @RequestBody Registration registration) {
		
		Registration newRegistration = registrationService.updateRegistration(registrationID, registration);
		
		
		return new ResponseEntity<>(newRegistration, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/users/{userID}/trips/{tripID}/registrations", method = RequestMethod.POST)
	public ResponseEntity<?> addRegistration(@PathVariable long userID,@PathVariable long tripID) {
		System.out.println("the");
		System.out.println(userID);
		if (userRepository==null) {
			System.out.println("repository null");
		}
		Optional<User> user= userRepository.findById(userID);
		if (user.get()==null) {
			return new ResponseEntity<>("user of that id does not exist", HttpStatus.BAD_REQUEST);
		}
		Optional<Trip> trip = tripRepository.findById(tripID);
		if (trip.get()==null) {
			return new ResponseEntity<>("trip of that id does not exist", HttpStatus.BAD_REQUEST);
		}
		
		Registration newRegistration= registrationService.addRegistration(userID, tripID);
		return new ResponseEntity<>(newRegistration, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/registrations/{registrationID}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRegistration(@PathVariable long registrationID) {
		
		Registration deletedRegistration= registrationService.deleteRegistration(registrationID);
				
		
		return new ResponseEntity<>(deletedRegistration,HttpStatus.OK);
	}
	
	@RequestMapping(value="/registrations/{registraionID}", method = RequestMethod.GET)
	public ResponseEntity<?> getRegistrationByID(@PathVariable long registrationID) {
		Optional <Registration> registration= registrationService.getRegistrationByID(registrationID);
		Registration r = registration.get();
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	
	
	
	
}

