
-- drop all tables
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- give access to postgres
GRANT ALL ON SCHEMA public TO test;
GRANT ALL ON SCHEMA public TO public;

-- enum
CREATE TYPE type_espace_enum AS ENUM ('SALLE', 'COULOIR', 'ESCALIER', 'ASCENSEUR');
CREATE TYPE etat_salle_enum AS ENUM ('DISPONIBLE', 'INDISPONIBLE', 'COMPLETE');
CREATE TYPE statut_acte_medical_enum AS ENUM ('EN_COURS', 'EN_ATTENTE', 'TERMINE');

-- create table

CREATE TABLE batiment (
    id_batiment SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE etage (
    id_etage SERIAL PRIMARY KEY,
    numero_etage VARCHAR(10) NOT NULL,
    id_batiment INTEGER NOT NULL,
    CONSTRAINT fk_etage_batiment FOREIGN KEY (id_batiment) REFERENCES batiment(id_batiment)
);

CREATE TABLE espace (
    id_espace SERIAL PRIMARY KEY,
    numero_espace VARCHAR(20) NOT NULL,
    type_espace type_espace_enum NOT NULL,
    id_etage INTEGER NOT NULL,
    CONSTRAINT fk_espace_etage FOREIGN KEY (id_etage) REFERENCES etage(id_etage)
);

CREATE TABLE connexion (
    id_connexion SERIAL PRIMARY KEY,
    id_espace_1 INTEGER NOT NULL,
    id_espace_2 INTEGER NOT NULL,
    CONSTRAINT fk_connexion_1 FOREIGN KEY (id_espace_1) REFERENCES espace(id_espace),
    CONSTRAINT fk_connexion_2 FOREIGN KEY (id_espace_2) REFERENCES espace(id_espace),
    CONSTRAINT uq_connexion UNIQUE (id_espace_1, id_espace_2)
);


CREATE TABLE salle (
    id_salle SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    capacite INTEGER CHECK (capacite > 0),
    place_disponible INTEGER CHECK (place_disponible <= capacite AND place_disponible >= 0),
    etat_salle etat_salle_enum NOT NULL,
    id_espace INTEGER NOT NULL,
    CONSTRAINT fk_salle_espace FOREIGN KEY (id_espace) REFERENCES espace(id_espace)
);

CREATE TABLE type_acte_medical (
    id_type_acte_medical SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    duree INTERVAL NOT NULL
);


CREATE TABLE type_acte_prerequis (
    id_prerequis SERIAL PRIMARY KEY,
    id_type_acte INTEGER NOT NULL,
    id_type_acte_prerequis INTEGER NOT NULL,
    CONSTRAINT uq_acte_prerequis UNIQUE (id_type_acte, id_type_acte_prerequis),
    CONSTRAINT fk_acte FOREIGN KEY (id_type_acte) REFERENCES type_acte_medical(id_type_acte_medical),
    CONSTRAINT fk_prerequis FOREIGN KEY (id_type_acte_prerequis) REFERENCES type_acte_medical(id_type_acte_medical)
);


CREATE TABLE salle_type_acte_medical (
    id_salle_type_acte SERIAL PRIMARY KEY,
    id_salle INTEGER NOT NULL,
    id_type_acte_medical INTEGER NOT NULL,
    CONSTRAINT uq_salle_type UNIQUE (id_salle, id_type_acte_medical),
    CONSTRAINT fk_sta_salle FOREIGN KEY (id_salle) REFERENCES salle(id_salle),
    CONSTRAINT fk_sta_type FOREIGN KEY (id_type_acte_medical) REFERENCES type_acte_medical(id_type_acte_medical)
);

CREATE TABLE medecin (
    id_medecin SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    specialite VARCHAR(100) NOT NULL
);


CREATE TABLE patient (
    id_patient SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL
);

CREATE TABLE parcours (
    id_parcours SERIAL PRIMARY KEY,
    nom_parcours VARCHAR(100) NOT NULL,
    statut_global statut_acte_medical_enum,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_medecin INTEGER NOT NULL,
    CONSTRAINT fk_parcours_medecin FOREIGN KEY (id_medecin) REFERENCES medecin(id_medecin)
);

CREATE TABLE patientA (
    idpatientA SERIAL PRIMARY KEY,
    nompatientA VARCHAR(255) NOT NULL,
    adressepatientA VARCHAR(255),
    numeropatientA VARCHAR(50),
    patientAlatitude DOUBLE PRECISION,        
    patientAlongitude DOUBLE PRECISION
);
CREATE TABLE ambulance (
    idambulance SERIAL PRIMARY KEY,        
    adresseambulance VARCHAR(255),         
    disponibiliteambulance BOOLEAN DEFAULT true,  
    vitessemoyambulance DOUBLE PRECISION,    
    equipementambulance DOUBLE PRECISION,    
    experienceambulance DOUBLE PRECISION,     
    ambulancelatitude DOUBLE PRECISION,        
    ambulancelongitude DOUBLE PRECISION
);



CREATE TABLE suivre (
    id_suivre SERIAL PRIMARY KEY,
    id_parcours INTEGER NOT NULL,
    id_patient INTEGER NOT NULL,
    CONSTRAINT uq_parcours_patient UNIQUE (id_parcours, id_patient),
    CONSTRAINT fk_suivre_parcours FOREIGN KEY (id_parcours) REFERENCES parcours(id_parcours) ON DELETE CASCADE,
    CONSTRAINT fk_suivre_patient FOREIGN KEY (id_patient) REFERENCES patient(id_patient) ON DELETE CASCADE
);

CREATE TABLE acte_medical (
    id_acte_medical SERIAL PRIMARY KEY,
    ordre INTEGER NOT NULL,
    statut statut_acte_medical_enum NOT NULL,
    id_type_acte_medical INTEGER NOT NULL,
    id_parcours INTEGER NOT NULL,
    id_salle INTEGER,
    CONSTRAINT fk_acte_type FOREIGN KEY (id_type_acte_medical) REFERENCES type_acte_medical(id_type_acte_medical),
    CONSTRAINT fk_acte_parcours FOREIGN KEY (id_parcours) REFERENCES parcours(id_parcours),
    CONSTRAINT fk_acte_salle FOREIGN KEY (id_salle) REFERENCES salle(id_salle)
);