package com.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String backgroundImageUrl = "https://fthmb.tqn.com/vMHG2Hi44XBqddh93WTo3nkWESU=/5000x3000/filters:fill(auto,1)/low-poly-background-672623312-5a5a8563e258f800370a105a.jpg";

    private String currentCity;
    private String currentCountry;

    private String bornCity;
    private String bornCountry;

    private String dob;
    private String gender;
    private String maritalStatus;

    private String schoolName;

    private String jobTitle;

    private String companyName;
    private String companyUrl;

    private String phoneNumber;

    @OneToOne
	private User owner;
}
