CREATE TABLE IF NOT EXISTS `Portfolio` (	
	pfl_id int(20) NOT NULL AUTO_INCREMENT, /* internal identifier for a portfolio */
	pfl_csg_id int(11) DEFAULT NULL, /* = Portfolio-Owner-ID e.g. PBC, P2R */
	pfl_wrk_id int(20) DEFAULT NULL, /* constant value for backend-processes */
	pfl_cust_identifier varchar(20) NOT NULL, /* = DUNS-Number ror some other ID (might be Handelsregisternummer)*/
	pfl_ext_identifier varchar(20) NOT NULL, /* = Business-Partner-ID (BP_ID) */
	pfl_dtt_id int(11) NOT NULL, /* = Profile-ID e.g. S,M,L */
	pfl_country_iso2 char(2) NOT NULL,
	pfl_strt_dt datetime NOT NULL, /* beginning of activation */
	pfl_end_dt datetime DEFAULT NULL, /* beginning of activation. in case its not null, it disables a portfolio */
	PRIMARY KEY (pfl_id),
	UNIQUE KEY portfolio_pk (pfl_id)
);

DELETE FROM Portfolio;

CREATE TABLE IF NOT EXISTS `workflow_log` (
	wrk_id INT NOT NULL,
	wrk_clt_id INT NOT NULL,
	wrk_csg_id INT NOT NULL,
	wrk_type_cd VARCHAR(100) NOT NULL,
	wrk_def_type_cd INT NOT NULL,
	wrk_current_step INT NOT NULL,
	wrk_current_status INT NOT NULL,
	wrk_pdi_job_id INT NULL,
	wrk_strt_dt DATETIME NOT NULL,
	wrk_end_dt DATETIME NULL,
	wrk_success_quote DECIMAL(6,3) NULL,
	wrk_activating_time DATETIME NULL,
	PRIMARY KEY (wrk_id)
);

DELETE FROM workflow_log;


	

