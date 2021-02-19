/* ---------------------------------------------------------------
 --        Script HSQLDB.  
 ---------------------------------------------------------------*/


/***************************************************************
   Table: step
 ***************************************************************/
CREATE TABLE IF NOT EXISTS step(
	step_code  VARCHAR (10) NOT NULL,
	name       VARCHAR (100) ,
	year       SMALLINT  
	,CONSTRAINT step_PK PRIMARY KEY (step_code)
);

/***************************************************************
   Table: formation
 ***************************************************************/
CREATE TABLE IF NOT EXISTS formation(
	formation_code  VARCHAR (10) NOT NULL,
	name            VARCHAR (150) NOT NULL,
	description     VARCHAR (2048) NOT NULL,
	type            VARCHAR (100) NOT NULL,
	url             VARCHAR (2083) NOT NULL
	,CONSTRAINT formation_PK PRIMARY KEY (formation_code)
);

/***************************************************************
   Table: administrative_registration
 ***************************************************************/
CREATE TABLE IF NOT EXISTS administrative_registration(
	step_code     VARCHAR (10) NOT NULL,
	student_code  VARCHAR (10) NOT NULL,
	year          SMALLINT  NOT NULL
	,CONSTRAINT administrative_registration_PK PRIMARY KEY (step_code,student_code,year)
);

/***************************************************************
   Table: path
 ***************************************************************/
CREATE TABLE IF NOT EXISTS path(
	path_code               VARCHAR (10) NOT NULL,
	avg_student_count_per_year  DOUBLE  NOT NULL
	,CONSTRAINT path_PK PRIMARY KEY (path_code)
);

/***************************************************************
   Table: step_formation
 ***************************************************************/
CREATE TABLE IF NOT EXISTS step_formation(
	step_code       VARCHAR (10) NOT NULL,
	formation_code  VARCHAR (10) NOT NULL
	,CONSTRAINT step_formation_PK PRIMARY KEY (step_code,formation_code)
);

/***************************************************************
   Table: path_step
 ***************************************************************/
CREATE TABLE IF NOT EXISTS path_step(
	step_code      VARCHAR (10) NOT NULL,
	path_code      VARCHAR (10) NOT NULL,
	step_position  SMALLINT  NOT NULL
	,CONSTRAINT path_step_PK PRIMARY KEY (step_code,path_code)
);





ALTER TABLE administrative_registration
	ADD CONSTRAINT administrative_registration_step0_FK
	FOREIGN KEY (step_code)
	REFERENCES step(step_code);

ALTER TABLE step_formation
	ADD CONSTRAINT step_formation_step0_FK
	FOREIGN KEY (step_code)
	REFERENCES step(step_code);

ALTER TABLE step_formation
	ADD CONSTRAINT step_formation_formation1_FK
	FOREIGN KEY (formation_code)
	REFERENCES formation(formation_code);

ALTER TABLE path_step
	ADD CONSTRAINT path_step_step0_FK
	FOREIGN KEY (step_code)
	REFERENCES step(step_code);

ALTER TABLE path_step
	ADD CONSTRAINT path_step_path1_FK
	FOREIGN KEY (path_code)
	REFERENCES path(path_code);

	
-------------------------------------------------------------
-- View: pre_built_path
-------------------------------------------------------------

CREATE VIEW IF NOT EXISTS pre_built_path AS
SELECT step_sequence,
    COUNT(student_code) / (2021 - min(end_year))
    AS avg_student_count_per_year
FROM (
    SELECT student_code,
        GROUP_CONCAT(
            DISTINCT step_code
            ORDER BY year ASC separator ','
        ) AS step_sequence,
        MAX(year) AS end_year
    FROM administrative_registration
    GROUP BY student_code
) AS T
GROUP BY step_sequence;