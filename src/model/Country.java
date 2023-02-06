package model;

/**
 * Represents a country.
 */
public class Country {

    /**
     * The country ID variable of a Country object
     */
    private int countryId;
    /**
     * The country name variable of a Country object
     */
    private String country;

    /**
     * Constructor of a Country object.
     * @param countryId the country ID to set
     * @param country the country name to set
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Override the toString method to display the country ID and name
     * @return string of country ID and country name
     */
    @Override
    public String toString(){
        return(countryId + " " + country);
    }
}
