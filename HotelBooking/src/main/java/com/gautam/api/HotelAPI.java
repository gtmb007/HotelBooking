package com.gautam.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gautam.model.Booking;
import com.gautam.model.Hotel;
import com.gautam.model.User;
import com.gautam.model.Vendor;
import com.gautam.service.AdminService;
import com.gautam.service.HotelService;
import com.gautam.service.UserService;

@RestController
@RequestMapping(value="/")
public class HotelAPI {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment environment;
	
//	@PutMapping(value="/admin/{id}/{password}")
//	public ResponseEntity<String> adminSignup(@PathVariable String id, @PathVariable String password) throws Exception {
//		try {
//			String adminId=adminService.addAdmin(id, password);
//			String message=environment.getProperty("API.ADMIN_SIGNUP_SUCCESS")+adminId;
//			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
//			return response;
//		} catch(Exception e) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
//		}
//	}
	
	@GetMapping(value="/admin/{id}/{password}")
	public ResponseEntity<String> adminLogin(@PathVariable String id, @PathVariable String password) throws Exception {
		try {
			String adminId=adminService.validateAdmin(id, password);
			String message=environment.getProperty("API.ADMIN_LOGIN_SUCCESS")+adminId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PostMapping(value="/user")
	public ResponseEntity<String> userSignup(@RequestBody User user) throws Exception {
		try {
			String userId=userService.addUser(user);
			String message=environment.getProperty("API.USER_SIGNUP_SUCCESS")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	} 
	
	@GetMapping(value="/user/{id}/{password}")
	public ResponseEntity<String> userLogin(@PathVariable String id, @PathVariable String password) throws Exception {
		try {
			String userId=userService.validateUser(id, password);
			String message=environment.getProperty("API.USER_LOGIN_SUCCESS")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) throws Exception {
		try {
			User user=userService.getUser(id);
			ResponseEntity<User> response=new ResponseEntity<User>(user, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/{id}")
	public ResponseEntity<String> addBooking(@PathVariable String id, @RequestBody Booking booking) throws Exception {
		try {
			userService.addBooking(id, booking.getBookingId());
			String message=environment.getProperty("API.BOOKING_ADDED");
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/name/{id}")
	public ResponseEntity<String> updateUserName(@PathVariable String id, @RequestBody User user) throws Exception {
		try {
			String userId=userService.updateUserName(id, user.getFirstName(), user.getLastName());
			String message=environment.getProperty("API.USER_NAME_UPDATED")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/password/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable String id, @RequestBody User user) throws Exception {
		try {
			String userId=userService.updatePassword(id, user.getPassword());
			String message=environment.getProperty("API.USER_PASSWORD_UPDATED")+userId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/user/{id}/{amount}")
	public ResponseEntity<String> rechargeWallet(@PathVariable String id, @PathVariable Double amount) throws Exception {
		try {
			userService.rechargeWallet(id, amount);
			String message=environment.getProperty("API.WALLET_RECHARGE_SUCCESS");
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PostMapping(value="/hotel")
	public ResponseEntity<String> addHotel(@RequestBody Hotel hotel) throws Exception {
		try {
			String hotelId=hotelService.addHotel(hotel);
			String message=environment.getProperty("API.HOTEL_ADDED")+hotelId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/hotel/{hotelId}")
	public ResponseEntity<Hotel> searchHotelById(@PathVariable String hotelId) throws Exception {
		try {
			Hotel hotel=hotelService.getHotel(hotelId);
			ResponseEntity<Hotel> response=new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/hotel/name/{key}")
	public ResponseEntity<Set<Hotel>> searchHotelByNameKey(@PathVariable String key) throws Exception {
		try {
			Set<Hotel> hotels=hotelService.searchHotelByNameKey(key);
			ResponseEntity<Set<Hotel>> response=new ResponseEntity<Set<Hotel>>(hotels, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/hotel/location/{key}")
	public ResponseEntity<Set<Hotel>> searchHotelByLocationKey(@PathVariable String key) throws Exception {
		try {
			Set<Hotel> hotels=hotelService.searchHotelByLocationKey(key);
			ResponseEntity<Set<Hotel>> response=new ResponseEntity<Set<Hotel>>(hotels, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PostMapping(value="/vendor")
	public ResponseEntity<String> addVendor(@RequestBody Vendor vendor) throws Exception {
		try {
			String vendorId=hotelService.addVendor(vendor);
			String message=environment.getProperty("API.VENDOR_ADDED")+vendorId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/vendor/{vendorId}")
	public ResponseEntity<Vendor> getVendor(@PathVariable String vendorId) throws Exception {
		try {
			Vendor vendor=hotelService.getVendor(vendorId);
			ResponseEntity<Vendor> response=new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PostMapping(value="/booking")
	public ResponseEntity<String> bookHotel(@RequestBody Booking booking) throws Exception {
		try {
			Integer bookingId=hotelService.bookHotel(booking.getHotelId(), booking.getVendorId(), booking.getNoOfRooms());
			String message=environment.getProperty("API.BOOKING_SUCCESS")+bookingId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@GetMapping(value="/booking/{bookingId}")
	public ResponseEntity<Booking> getBooking(@PathVariable Integer bookingId) throws Exception {
		try {
			Booking booking=hotelService.getBooking(bookingId);
			ResponseEntity<Booking> response=new ResponseEntity<Booking>(booking, HttpStatus.OK);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value="/booking/{bookingId}")
	public ResponseEntity<String> updateBooking(@PathVariable Integer bookingId, @RequestBody Booking booking) throws Exception {
		try {
			Integer bId=hotelService.updateBooking(bookingId, booking.getNoOfRooms());
			String message=environment.getProperty("API.BOOKING_UPDATED")+bId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@DeleteMapping(value="/booking/{bookingId}")
	public ResponseEntity<String> cancelBooking(@PathVariable Integer bookingId) throws Exception {
		try {
			Integer bId=hotelService.cancelBooking(bookingId);
			String message=environment.getProperty("API.BOOKING_CANCELLED")+bId;
			ResponseEntity<String> response=new ResponseEntity<String>(message, HttpStatus.CREATED);
			return response;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
}
