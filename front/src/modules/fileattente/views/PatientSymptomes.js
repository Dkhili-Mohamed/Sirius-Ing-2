import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../../../styles/Table.css';
import {LOCAL_HOST_PATIENT, SYMPTOME_PATIENT} from "../../../constants/back";
import { useParams } from 'react-router-dom';

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
                <div key={symptome.id}>
                    <p>{symptome.libelle}</p>
                </div>
            ))}
        </div>
    );
}

export default function PatientSymptomes() {
    const { id } = useParams();
    const [patient, setPatient] = useState(null);

    const fetchPatient = async () => {
        try {
            const patientResponse = await axios.get(`${LOCAL_HOST_PATIENT}${id}`);
            setPatient(patientResponse.data);
        } catch (error) {
            alert("Erreur lors du chargement du patient: " + error);
        }
    };

    useEffect(() => {
        if (id) {
            fetchPatient();
        }
    }, [id]);

    if (!patient)
        return (<div className="container text-center mt-5">Patient non trouvé</div>);

    return (
        <div className="container text-center">
            <h4 className="mx-2">Patient : {patient.nomPatient} {patient.prenomPatient}</h4>
            <p><strong>Âge:</strong> {patient.agePatient} ans</p>

            <SymptomePatient idPatient={id} />

            <div className="row mt-4">
                <div className="col-md-12">
                    <button
                        className="btn btn-secondary"
                        onClick={() => window.history.back()}
                    >
                        ← Retour à la liste des patients
                    </button>
                </div>
            </div>
        </div>
    );
}