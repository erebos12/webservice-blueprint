CREATE TABLE IF NOT EXISTS `Portfolio` (	
	pfl_id int(20) NOT NULL AUTO_INCREMENT,
	pfl_csg_id int(11) DEFAULT NULL,
	pfl_wrk_id int(20) DEFAULT NULL,
	pfl_cust_identifier varchar(20) NOT NULL,
	pfl_ext_identifier varchar(20) NOT NULL,
	pfl_dtt_id int(11) NOT NULL,
	pfl_country_iso2 char(2) NOT NULL,
	pfl_strt_dt datetime NOT NULL,
	pfl_end_dt datetime DEFAULT NULL,
	PRIMARY KEY (pfl_id),
	UNIQUE KEY portfolio_pk (pfl_id)
);

DELETE FROM `Portfolio`;

CREATE TABLE IF NOT EXISTS `Workflow` (	
	WRK_ID INT NOT NULL,
	WRK_CLT_ID INT NOT NULL,
	WRK_CDP_ID VARCHAR(50),
	WRK_TYPE_CD VARCHAR(1),
	WRK_TODO_ID VARCHAR(20),
	WRK_START_DATE DATETIME NOT NULL,
	WRK_END_DATE DATETIME,
	PRIMARY KEY (WRK_ID)
);
DELETE FROM `Workflow`;



	

