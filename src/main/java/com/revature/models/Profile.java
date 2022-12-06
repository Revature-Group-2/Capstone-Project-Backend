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
    private String backgroundImageUrl = "https://th.bing.com/th/id/R.77d86c07a77caa50a1007988a9c5df5d?rik=FccQzPNYUxhKaw&pid=ImgRaw&r=0";

    private String currentCity;
    private String currentCountry;

    private String bornCity;
    private String bornCountry;

    private String dob;
    private String gender;
    private String martialStatus;

    private String schoolName;

    private String jobTitle;

    private String companyName;
    private String companyUrl;

    @OneToOne
	private User owner;
}
