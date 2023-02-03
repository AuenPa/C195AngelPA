package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBState {

    public static ObservableList<Division> getAllStates() {
        ObservableList<Division> slist = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division s = new Division(divisionId, division, countryId);
                slist.add(s);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return slist;
    }

}
