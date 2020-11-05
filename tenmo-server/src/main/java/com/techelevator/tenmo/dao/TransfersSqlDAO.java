package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.tenmo.model.Transfers;

@Component
public class TransfersSqlDAO implements TransfersDAO {
	
	 private JdbcTemplate jdbcTemplate;
	  public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	  }
	@Override
	public List<Transfers> allTransfers() {
		  
		    List<Transfers> transfers = new ArrayList<>();
		    String sql = "SELECT * FROM transfers";
		    SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		    while(results.next()) {
		      Transfers theTransfer = mapRowToTransfers(results);
		      transfers.add(theTransfer);
		    }
		    return transfers;
		  }
	@Override
	public Transfers detailsTransfersBasedOnId(Long id) {
		  
		    Transfers transfers = null;
		    String sql = "SELECT * FROM transfers WHERE transfer_id = ? ";
		    SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		    while(results.next()) {
		      transfers = mapRowToTransfers(results);
		       
		    }
		    return transfers;
		  }
	
	@Override
	public List<Transfers> listAllPendingTransfers() {
		 List<Transfers> transfers = new ArrayList<>();
		    String sql = "SELECT * FROM transfers WHERE transfer_status_id = 1";
		    SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		    while(results.next()) {
		      Transfers pendingTransfer = mapRowToTransfers(results);
		      transfers.add(pendingTransfer);
		    }
		    return transfers;
	}
	private Transfers mapRowToTransfers(SqlRowSet results) {
		Transfers theTransfers = new Transfers();
		theTransfers.setId(results.getLong("transfer_id"));
		theTransfers.setTypeId(results.getInt("transfer_type_id"));
		theTransfers.setStatusId(results.getInt("transfer_status_id"));
		theTransfers.setAccountFrom(results.getInt("account_from"));
		theTransfers.setAccountTo(results.getInt("account_to"));
		theTransfers.setAmount(results.getBigDecimal("amount"));
		return theTransfers;
		}
}