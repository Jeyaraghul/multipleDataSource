package com.zaga.multipleDataSource.hotelBooking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<HotelDetails, Long> {

}
