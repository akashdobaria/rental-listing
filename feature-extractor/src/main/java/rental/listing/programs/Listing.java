package rental.listing.programs;

import java.util.List;
import java.util.Map;

public class Listing {
	
	/*
	 * 
	 * bathrooms	bedrooms	building_id	created	description	display_address	features	interest_level	latitude	listing_id	longitude	manager_id	photos	price	street_address

	 */
	private String noOfBedrooms; 
	private String noOfBathrooms;
	private String buildingId;
	private String created;
	private String description;
	private String displayAddress;
	private String interestLevel;
	private String latitude;
	private String longitude;
	private String listingId;
	private String managerId;
	private String photos;
	private Integer noOfPhotos;
	private String price;
	private String streetAddress;
	private List<String> features;
	private Map<String,Integer> expandedFeatures;
	
	public Listing(){}
	
	
	
	public String getNoOfBedrooms() {
		return noOfBedrooms;
	}



	public void setNoOfBedrooms(String noOfBedrooms) {
		this.noOfBedrooms = noOfBedrooms;
	}



	public String getNoOfBathrooms() {
		return noOfBathrooms;
	}



	public void setNoOfBathrooms(String noOfBathrooms) {
		this.noOfBathrooms = noOfBathrooms;
	}



	public String getBuildingId() {
		return buildingId;
	}



	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}



	public String getCreated() {
		return created;
	}



	public void setCreated(String created) {
		this.created = created;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getDisplayAddress() {
		return displayAddress;
	}



	public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}



	public String getInterestLevel() {
		return interestLevel;
	}



	public void setInterestLevel(String interestLevel) {
		this.interestLevel = interestLevel;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getListingId() {
		return listingId;
	}



	public void setListingId(String listingId) {
		this.listingId = listingId;
	}



	public String getManagerId() {
		return managerId;
	}



	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}



	public String getPhotos() {
		return photos;
	}



	public void setPhotos(String photos) {
		this.photos = photos;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getStreetAddress() {
		return streetAddress;
	}



	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}



	public List<String> getFeatures() {
		return features;
	}



	public void setFeatures(List<String> features) {
		this.features = features;
	}



	public Map<String, Integer> getExpandedFeatures() {
		return expandedFeatures;
	}



	public void setExpandedFeatures(Map<String, Integer> expandedFeatures) {
		this.expandedFeatures = expandedFeatures;
	}



	public Integer getNoOfPhotos() {
		return noOfPhotos;
	}



	public void setNoOfPhotos(Integer noOfPhotos) {
		this.noOfPhotos = noOfPhotos;
	}



	@Override
	public String toString(){
		return GsonUtil.getGson().toJson(this);
	}
}