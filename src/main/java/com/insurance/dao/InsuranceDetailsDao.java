package com.insurance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.insurance.dbconnection.ConnectionFactory;
import com.insurance.model.InsuranceDetailsResponse;

public class InsuranceDetailsDao {
    Connection connection = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;
    
    public InsuranceDetailsDao() {

    }

    private Connection getConnection() throws SQLException {
        Connection conn;
        conn = ConnectionFactory.getInstance().getConnection();
        return conn;
    }

    public Set findAll() {
        try {
           System.out.println("InsuranceDetailsDao -> findAll() -> Begins");
            String queryString = "SELECT * FROM insurance_details";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            rs = ptmt.executeQuery();
            Set insuranceHolders = new HashSet();
            while(rs.next())
            {
                InsuranceDetailsResponse insuranceHolder = extractUserFromResultSet(rs);
                insuranceHolders.add(insuranceHolder);
            }
            System.out.println("No of Insurance holders found "+insuranceHolders.size());
            return insuranceHolders;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    
    private InsuranceDetailsResponse extractUserFromResultSet(ResultSet rs) throws SQLException {
        InsuranceDetailsResponse insuranceHolder = new InsuranceDetailsResponse();
        insuranceHolder.setPolicyId( rs.getInt("policy_id") );
        insuranceHolder.setPolicyHolder( rs.getString("insurance_holder_name") );
        return insuranceHolder;
    }
}
