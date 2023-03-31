package com.example.project.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({ "cloth_donated, rating" })

public class Rating{
    
    @JsonProperty("cloth_donated")
    private int clothDonated;

	@JsonProperty("rating")
	private float rating;

	public Rating() {
		super();
	}

    public Rating(int clothDonated, float rating) {
        super();
        this.clothDonated = clothDonated;
        this.rating = rating;
    }

    @JsonGetter("cloth_donated")
    public int getClothDonated() {
        return clothDonated;
    }

    @JsonSetter("cloth_donated")
    public void setClothDonated(int clothDonated) {
        this.clothDonated = clothDonated;
    }

    @JsonGetter("rating")
	public float getRating() {
        return rating;
    }

	@JsonSetter("rating")
    public void setRating(float rating) {
        this.rating = rating;
    }
}
