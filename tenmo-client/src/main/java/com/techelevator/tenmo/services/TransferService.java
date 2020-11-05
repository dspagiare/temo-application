package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.view.ConsoleService;

public class TransferService {
	
	
	private static final String API_BASE_URL = "http://localhost:8080/";
	private static RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console;
    
	public void viewTransferHistory(AuthenticatedUser currentUser) {
		Transfers[] allTransfers = null;
		try {
		  	allTransfers = restTemplate.exchange(API_BASE_URL + "transfers",HttpMethod.GET, makeAuthEntity(currentUser), Transfers[].class ).getBody();
		  } catch (RestClientResponseException ex) {
		   System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
		  } catch (ResourceAccessException ex) {
		   System.out.println(ex.getMessage());
		  }
			
			System.out.println(allTransfers);
		  }
	
	public void viewPendingRequests(AuthenticatedUser currentUser) {
		Transfers[] pendingRequests = null;
		
		try {
		  pendingRequests = restTemplate.exchange(API_BASE_URL + "pending_requests",HttpMethod.GET, makeAuthEntity(currentUser), Transfers[].class ).getBody();
	  	} catch (RestClientResponseException ex) {
		   System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
		   System.out.println(ex.getMessage());
		}
			
		  System.out.println(pendingRequests);
		}
	
	private void viewTransferDetailsById(long id, AuthenticatedUser currentUser) {
		Transfers myTransfers = null;
		try {
		  	myTransfers = restTemplate.exchange(API_BASE_URL + "transfers" + id, HttpMethod.GET, makeAuthEntity(currentUser), Transfers.class ).getBody();
		  } catch (RestClientResponseException ex) {
		   System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
		  } catch (ResourceAccessException ex) {
		   System.out.println(ex.getMessage());
		  }
			
			System.out.println(myTransfers);
		}
	
	private HttpEntity makeAuthEntity(AuthenticatedUser currentUser) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
