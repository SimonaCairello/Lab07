package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

import it.polito.tdp.poweroutages.model.Nerc;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			//System.out.println(dao.getNercList()) ;
			//System.out.println(dao.getNerc(16));
			System.out.println(dao.getPowerOutagesList(new Nerc(5, "HI")));

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
