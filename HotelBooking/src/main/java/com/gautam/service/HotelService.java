package com.gautam.service;

import java.util.Set;

import com.gautam.model.Booking;
import com.gautam.model.Hotel;
import com.gautam.model.Vendor;

public interface HotelService {
	
	public String addHotel(Hotel hotel) throws Exception;
	
	public Hotel getHotel(String hotelId) throws Exception;
	
	public Set<Hotel> searchHotelByNameKey(String key) throws Exception;
	
	public Set<Hotel> searchHotelByLocationKey(String key) throws Exception;
	
	public String addVendor(Vendor vendor) throws Exception;
	
	public Vendor getVendor(String vendorId) throws Exception;
	
	public Booking getBooking(Integer bookingId) throws Exception;
	
	public Boolean validateBooking(String hotelId, String vendorId, Integer noOfRooms) throws Exception;
	
	public Integer bookHotel(String hotelId, String vendorId, Integer noOfRooms) throws Exception;
	
	public Integer updateBooking(Integer bookingId, Integer noOfRooms) throws Exception;
	
	public Integer cancelBooking(Integer bookingId) throws Exception;
	
}
