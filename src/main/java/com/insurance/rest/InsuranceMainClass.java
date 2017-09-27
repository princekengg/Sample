package com.insurance.rest;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.dao.InsuranceDetailsDao;

@Path("/info")
public class InsuranceMainClass {
  
  @GET
  @Path("/getInsuranceDetails")
  public String getInsuranceholdersList() {
   ObjectMapper mapper = new ObjectMapper();
  
    String json = null;

    // Get time from DB server
    try {
     
        InsuranceDetailsDao insuranceDetailsDao = new InsuranceDetailsDao();
        Set insuranceDetails = insuranceDetailsDao.findAll();
        json = mapper.writeValueAsString(insuranceDetails);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return json;
  }
}