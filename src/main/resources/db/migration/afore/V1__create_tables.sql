CREATE TABLE catalog_afore_section (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) UNIQUE NOT NULL,
	is_active bit NOT NULL
);

CREATE TABLE catalog_afore_concept (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reference VARCHAR(100) UNIQUE NOT NULL,
	description VARCHAR(100) NOT NULL,
	is_active bit NOT NULL
);

CREATE TABLE catalog_afore_concept_unified (
    id INT AUTO_INCREMENT PRIMARY KEY,
	description VARCHAR(100) NOT NULL,
	is_active bit NOT NULL
);


CREATE TABLE afore_contribution (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_inserted DATE NULL,
    id_concept INT NOT NULL,
	id_concept_unified INT NOT NULL,
    period_reference VARCHAR(50) NULL,
    quoted_days INT NULL,
    base_salary DECIMAL(10,2) NULL,
    amount DECIMAL(10,2) NULL,
    id_section INT NOT NULL,
    period DATETIME NOT NULL,
    FOREIGN KEY (id_section) REFERENCES catalog_afore_section(id),
	FOREIGN KEY (id_concept) REFERENCES catalog_afore_concept(id),
	FOREIGN KEY (id_concept_unified) REFERENCES catalog_afore_concept_unified(id)
);