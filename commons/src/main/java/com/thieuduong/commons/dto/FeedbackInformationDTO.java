package com.thieuduong.commons.dto;

public class FeedbackInformationDTO {

	private Double rating;

	private int commentsCount;

	private int ratingsCount;

	private int rating5Count;

	private int rating4Count;

	private int rating3Count;

	private int rating2Count;

	private int rating1Count;

	public FeedbackInformationDTO(Double rating, int commentsCount, int ratingsCount, int rating5Count,
			int rating4Count, int rating3Count, int rating2Count, int rating1Count) {
		super();
		this.rating = rating;
		this.commentsCount = commentsCount;
		this.ratingsCount = ratingsCount;
		this.rating5Count = rating5Count;
		this.rating4Count = rating4Count;
		this.rating3Count = rating3Count;
		this.rating2Count = rating2Count;
		this.rating1Count = rating1Count;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public int getRatingsCount() {
		return ratingsCount;
	}

	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	public int getRating5Count() {
		return rating5Count;
	}

	public void setRating5Count(int rating5Count) {
		this.rating5Count = rating5Count;
	}

	public int getRating4Count() {
		return rating4Count;
	}

	public void setRating4Count(int rating4Count) {
		this.rating4Count = rating4Count;
	}

	public int getRating3Count() {
		return rating3Count;
	}

	public void setRating3Count(int rating3Count) {
		this.rating3Count = rating3Count;
	}

	public int getRating2Count() {
		return rating2Count;
	}

	public void setRating2Count(int rating2Count) {
		this.rating2Count = rating2Count;
	}

	public int getRating1Count() {
		return rating1Count;
	}

	public void setRating1Count(int rating1Count) {
		this.rating1Count = rating1Count;
	}
}
