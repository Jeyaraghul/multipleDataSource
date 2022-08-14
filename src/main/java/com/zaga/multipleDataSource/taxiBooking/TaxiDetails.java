package com.zaga.multipleDataSource.taxiBooking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "taxi")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaxiDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carType;
    private String carName;
}
