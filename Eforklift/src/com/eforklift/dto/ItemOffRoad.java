package com.eforklift.dto;

public class ItemOffRoad {

    private String brand;
    private String partnumber;
    private String description;
    private String location;
    private String price;

    /**
     * @return the partnumber
     */
    public String getPartnumber() {
        return partnumber;
    }

    /**
     * @param partnumber the partnumber to set
     */
    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
	
}
