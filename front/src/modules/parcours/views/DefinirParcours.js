import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useParams } from "react-router-dom";
import '../../../styles/DefinirParcoursCSS.css';
import { TYPE_ACTE_MEDICAL, INSERT_PARCOURS, INSERT_SUIVRE, INSERT_ACTE_MEDICAL, SYMPTOME_PATIENT, LIBERER_BOX } from '../../../constants/back';


export default function DefinirParcours() {

    const{idPatient, idBoxMedicale} = useParams();
    const[typeActeMedical, setTypeActeMedical] = useState([]);
    const[acteParcours, setActeParcours] = useState([]);

    const setTypeActeMedicalData = async (idPatient) => {
        axios.get(TYPE_ACTE_MEDICAL + idPatient).then((response) => {
            setTypeActeMedical(response.data);
        }).catch(erreur => {
            alert("Erreur lors du chargement des acte médicaux" + erreur);
        });
    };

    const gestionCheckbox = (e) => {

        const idTypeActeMedical = parseInt(e.target.value);

        if (e.target.checked) {

            const acteMedical = typeActeMedical.find(acte => acte.idTypeActeMedical === idTypeActeMedical);
            
            setActeParcours([...acteParcours, acteMedical]);
        }
    }
    useEffect(() => {
        if (idPatient) {
            setTypeActeMedicalData(idPatient);
        }
    }, [idPatient]);

    const insererParcours = async (e) => {
        e.preventDefault();
       
        const parcours = {
            nomParcours: e.target.nomParcours.value,
            medecin: {
                idMedecin: 10,
            },
            statutGlobal: "EN_ATTENTE",
            dateCreation: Date.now(),
        }

        const reponce = await axios.post(INSERT_PARCOURS, parcours);

        const idParcoursPatient = reponce.data.idParcours;

        const suivre = {
            patient : {
                idPatient: idPatient,
            },
            parcours : {
                idParcours: idParcoursPatient,
            },
        }

        await axios.post(INSERT_SUIVRE, suivre);

        for (const acte of acteParcours) {
             
            const acteMedical = {
                typeActeMedical : acte,
                parcours:{
                    idParcours: idParcoursPatient,
                },
                ordre: acteParcours.indexOf(acte) + 1,
                statut: "EN_ATTENTE",
            }

        }


        //axios.post(LIBERER_BOX, idBoxMedicale)
        //await axios.post(`${LIBERER_BOX}/${idBoxMedicale}/${idPatient}`);
        //window.location.href = "/api/box-medicale";
        
    };
    
    return(

        <div>
            <div>
                <SymptomePatient idPatient={idPatient}/>
            </div>
            <h1>Définir le parcours du patient</h1>
            <form onSubmit={insererParcours}>
                <label>Nom parcours</label>
                <input type="text" name="nomParcours"/>

                <span>Type acte médical</span>
                {typeActeMedical && Array.isArray(typeActeMedical) && typeActeMedical.map((typeActe) => (

                    <div className="checkbox-group" key={typeActe.idTypeActeMedical}>

                        <input type="checkbox" name="typeActeMedical" value={typeActe.idTypeActeMedical} 
                        checked = {acteParcours.includes(typeActe)} 
                        onChange={(e)=> gestionCheckbox(e)}/>

                        <label>{typeActe.libelle}</label>

                    </div>
                ))}

                <button type="submit">Définir parcours</button>

            </form>

            

        </div>
            
    );
}

function SymptomePatient({idPatient}){

    const[symptome, setSymptome] = useState([]);

    const setSymptomeData = async (idPatient) => {
        axios.get(SYMPTOME_PATIENT + idPatient).then((response) => {

            setSymptome(response.data);
        }).catch(erreur => {

            alert("Erreur lors du chargement des symptomes" + erreur);
        });
    };

    useEffect(() => {
        if (idPatient) {
            setSymptomeData(idPatient);
        }
    }, [idPatient]);

    return(
        <div className='symptomes-container'>
            <h2>Symptômes du patient</h2>
            {symptome && symptome.map((symptome) => (

                <div>
                    <p key={symptome.id}>{symptome.libelle}</p>

                </div>
            ))}
        </div>
    );
}