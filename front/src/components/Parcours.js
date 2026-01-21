import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Table.css';
import { ALL_PATIENTS } from "../constants/back";   

export default function Parcours() {
    const [patientStatutParours, setPatientStatutParours] = useState([]);
    const [loading, setLoading] = useState(false);    
    const setParcoursPatientsData = async () => {   
        setLoading(true);
        axios.get(ALL_PATIENTS).then((response) => {
            setPatientStatutParours(response.data);
            setLoading(false);
        }).catch(error => {
        setLoading(false);  

            alert("Error Ocurred while loading data:" + error);
        });
    }


    useEffect(() => {
        setParcoursPatientsData();
    }, []);
    if (loading)
    return (<div className="container text-center mt-5">Chargement des parcours...</div>);
    if (patientStatutParours.length === 0)
        return (<div className="container text-center mt-5">No parcours patients</div>)
    return (
        <div className="container text-center">
        <h4 className=" mx-2">Liste des Parcours Patients</h4>
        <div className="row">
            <table className="table table-sm table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">Nom</th>
                <th scope="col">Prénom</th>
                <th scope="col">Nom Parcours</th>
                <th scope="col">Statut Parcours</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody> 
            {patientStatutParours.map((patientStatutParcours) => (
                <tr key={patientStatutParcours.idPatient}>   
                <td>{patientStatutParcours.nomPatient}</td>
                <td>{patientStatutParcours.prenomPatient}</td>
                <td>{patientStatutParcours.nomParcours}</td>
                <td>{patientStatutParcours.statutParcours}</td>
                <td>
                    
                    <button className="btn btn-primary btn-sm mx-1" 
                        onClick={() => window.location.href = `/api/parcours/${patientStatutParcours.idPatient}`}
                    >
                    Voir le parcours
                    </button>
                    
                </td>
                </tr>
            ))}
            </tbody>
            </table>
        </div>


        </div>
    );
};


