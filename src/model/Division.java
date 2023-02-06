package model;

/**
 * Represents a division.
 */
public class Division {

    /**
     * The division ID variable of a Division object
     */
    private int divisionId;
    /**
     * The division name variable of a Division object
     */
    private String division;
    /**
     * The country ID variable of a Division object
     */
    private int countryId;

    /**
     * Constructor of a Division object.
     * @param divisionId the division ID to set
     * @param division the division to set
     * @param countryId the country ID to set
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     *
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division the division name to set
     */
    public void setDivision(String division) {
        this.division = division;
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
     * Override the toString method to return the division ID and division name
     * @return the string of division ID and division name
     */
    @Override
    public String toString() {
        return(divisionId + " " + division);
    }

}
