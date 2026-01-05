import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css';
import {GET_PATIENTS, LOCAL_HOST_PATIENT, UPDATE_PATIENT} from "../constants/back";

export default function Patient() {
    const [patients, setPatients] = useState([]);

    const setPatientData = async () => {
        axios.get(GET_PATIENTS)
            .then((response) => {setPatients(response.data);
        })  .catch(error => {alert("Error Ocurred while loading data:" + error);
        });
    }
    const confirmRemovePatient = (id) => {
        if (window.confirm('Are you sure?')) {
            removePatient(id);
        }
    };
    const removePatient = async (id) => {
        axios.delete(LOCAL_HOST_PATIENT + id)
            .then((response) => {setPatientData();})
            .catch(error => {alert("Error Ocurred in removePatient:" + error);
        });
    }

    useEffect(() => {
        setPatientData();
    }, []);

    if (patients.length === 0)
        return (<div className="container text-center" >No patients</div>)
    return (
        <div className="container text-center">
            <h4 className=" mx-2">Patients Lists</h4>
            <div className="row">
                <table className="table table-sm table-bordered table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Prénom</th>
                        <th scope="col">Age</th>
                    </tr>
                    </thead>
                    <tbody className="table-group-divider">
                    {
                        patients.map( (patient, index) => (
                            <tr key={index}>
                                <th scope="row">{patient.idPatient}</th>
                                <td>{patient.nomPatient}</td>
                                <td>{patient.prenomPatient}</td>
                                <td>{patient.agePatient}</td>
                            </tr>

                        ))
                    }

                    </tbody>
                </table>
            </div>
        </div>
    );
};
