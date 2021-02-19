-------------------------------------------------------------
--        Script MySQL.
-------------------------------------------------------------


-------------------------------------------------------------
-- Table: step
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS step(
        step_code Varchar (10) NOT NULL ,
        name      Varchar (100) ,
        year      Smallint
	,CONSTRAINT step_PK PRIMARY KEY (step_code)
)ENGINE=InnoDB;


-------------------------------------------------------------
-- Table: formation
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS formation(
        formation_code Varchar (10) NOT NULL ,
        name           Varchar (150) NOT NULL ,
        description    Varchar (2048) NOT NULL ,
        type           Varchar (100) NOT NULL ,
        url            Varchar (2083) NOT NULL
	,CONSTRAINT formation_PK PRIMARY KEY (formation_code)
)ENGINE=InnoDB;

-------------------------------------------------------------
-- Table: administrative_registration
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS administrative_registration(
        step_code    Varchar (10) NOT NULL ,
        student_code Varchar (10) NOT NULL ,
        year         Smallint NOT NULL
	,CONSTRAINT administrative_registration_PK PRIMARY KEY (step_code,student_code,year)

	,CONSTRAINT administrative_registration_step_FK FOREIGN KEY (step_code) REFERENCES step(step_code)
)ENGINE=InnoDB;


-------------------------------------------------------------
-- Table: path
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS path(
        path_code              Varchar (36) NOT NULL ,
        avg_student_count_per_year Double NOT NULL
	,CONSTRAINT path_PK PRIMARY KEY (path_code)
)ENGINE=InnoDB;


-------------------------------------------------------------
-- Table: step_formation
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS step_formation(
        step_code      Varchar (10) NOT NULL ,
        formation_code Varchar (10) NOT NULL
	,CONSTRAINT step_formation_PK PRIMARY KEY (step_code,formation_code)

	,CONSTRAINT step_formation_step_FK FOREIGN KEY (step_code) REFERENCES step(step_code)
	,CONSTRAINT step_formation_formation0_FK FOREIGN KEY (formation_code) REFERENCES formation(formation_code)
)ENGINE=InnoDB;


-------------------------------------------------------------
-- Table: path_step
-------------------------------------------------------------

CREATE TABLE IF NOT EXISTS path_step(
        step_code     Varchar (10) NOT NULL ,
        path_code     Varchar (36) NOT NULL ,
        step_position Smallint NOT NULL
	,CONSTRAINT path_step_PK PRIMARY KEY (step_code,path_code)

	,CONSTRAINT path_step_step_FK FOREIGN KEY (step_code) REFERENCES step(step_code)
	,CONSTRAINT path_step_path0_FK FOREIGN KEY (path_code) REFERENCES path(path_code)
)ENGINE=InnoDB;

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