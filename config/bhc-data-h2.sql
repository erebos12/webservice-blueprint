CREATE TABLE IF NOT EXISTS `Portfolio` (	
	pfl_id int(20) NOT NULL AUTO_INCREMENT, /* internal identifier for a portfolio */
	pfl_csg_id int(11) DEFAULT NULL, /* = Portfolio-Owner-ID e.g. PBC, P2R */
	pfl_wrk_id int(20) DEFAULT NULL, /* constant value for backend-processes */
	pfl_cust_identifier varchar(20) NOT NULL, /* = DUNS-Number */
	pfl_ext_identifier varchar(20) NOT NULL, /* = Business-Partner-ID (BP_ID) */
	pfl_dtt_id int(11) NOT NULL, /* = Profile-ID e.g. S,M,L */
	pfl_country_iso2 char(2) NOT NULL,
	pfl_strt_dt datetime NOT NULL, /* beginning of activation */
	pfl_end_dt datetime DEFAULT NULL, /* beginning of activation. in case its not null, it disables a portfolio */
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



	

