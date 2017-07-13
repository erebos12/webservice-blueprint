package com.bisnode.bhc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Portfolio")
public class Portfolio {

	@Id
	@Column(name = "PFL_CLT_ID")
    public int PFL_CLT_ID;
	
	@Column(name = "PFL_CDP_ID")
	public int PFL_CDP_ID;
	
	@Column(name = "PFL_WRK_ID")
	public int PFL_WRK_ID;
		
	@Column(name = "PFL_CUST_ID")
	public String PFL_CUST_ID;
	
	@Column(name = "PFL_DTT_ID")
	public int PFL_DTT_ID;
	
	@Column(name = "PFL_COUNTRY_ISO3")
	public String PFL_COUNTRY_ISO3;
	
	@Column(name = "PFL_CURRENCY_ISO3")
	public String PFL_CURRENCY_ISO3;
	
	@Column(name = "PFL_LEDGER_KEY")
	public String PFL_LEDGER_KEY;
	
	@Column(name = "PFL_START_DATE")
	public Date PFL_START_DATE;
	
	@Column(name = "PFL_END_DATE")
	public Date PFL_END_DATE;

	@Override
	public String toString() {
		return "Portfolio [PFL_CLT_ID=" + PFL_CLT_ID + ", PFL_CDP_ID=" + PFL_CDP_ID + ", PFL_WRK_ID=" + PFL_WRK_ID
				+ ", PFL_CUST_ID=" + PFL_CUST_ID + ", PFL_DTT_ID=" + PFL_DTT_ID + ", PFL_COUNTRY_ISO3="
				+ PFL_COUNTRY_ISO3 + ", PFL_CURRENCY_ISO3=" + PFL_CURRENCY_ISO3 + ", PFL_LEDGER_KEY=" + PFL_LEDGER_KEY
				+ ", PFL_START_DATE=" + PFL_START_DATE + ", PFL_END_DATE=" + PFL_END_DATE + "]";
	}
}
