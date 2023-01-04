package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountry {

    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> clist = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Country c = new Country(countryId, country);
                clist.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
