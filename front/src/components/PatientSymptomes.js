import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Table.css';
import { LOCAL_HOST_PATIENT } from "../constants/back";
import { useParams } from 'react-router-dom';

export default function PatientSymptomes() {
    const { id } = useParams();
    const [patient, setPatient] = useState(null);
    const [symptomes, setSymptomes] = useState([]);
    const [loading, setLoading] = useState(false);

    const fetchPatientSymptomes = async () => {
        setLoading(true);
        try {
            const patientResponse = await axios.get(`${LOCAL_HOST_PATIENT}${id}`);
            setPatient(patientResponse.data);
            
            setSymptomes(patientResponse.data.symptomes || []);
            setLoading(false);
        } catch (error) {
            setLoading(false);
            alert("Erreur lors du chargement des symptômes: " + error);
        }
    };

    useEffect(() => {
        if (id) {
            fetchPatientSymptomes();
        }
    }, [id]);

    if (loading)
        return (<div className="container text-center mt-5">Chargement des symptômes...</div>);

    if (!patient)
        return (<div className="container text-center mt-5">Patient non trouvé</div>);

    return (
        <div className="container text-center">
            <h4 className="mx-2">Symptômes du Patient</h4>
            
            <div className="row mb-4">
                <div className="col-md-12">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">
                                {patient.nomPatient} {patient.prenomPatient}
                            </h5>
                            <p className="card-text">
                                <strong>Âge:</strong> {patient.agePatient} ans<br/>
                                <strong>Score d'urgence:</strong> {patient.scoreUrgence}<br/>
                                <strong>Niveau d'urgence:</strong> 
                                <span className={`badge ${patient.niveauUrgence === 'URGENT' ? 'bg-danger' : patient.niveauUrgence === 'INTERMEDIAIRE' ? 'bg-warning' : 'bg-success'}`}>
                                    {patient.niveauUrgence}
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <div className="row">
                <div className="col-md-12">
                    <table className="table table-sm table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Symptôme</th>
                                <th scope="col">Points</th>
                            </tr>
                        </thead>
                        <tbody className="table-group-divider">
                            {symptomes.length === 0 ? (
                                <tr>
                                    <td colSpan="3" className="text-center">
                                        Aucun symptôme enregistré
                                    </td>
                                </tr>
                            ) : (
                                symptomes.map((symptome, index) => {
                                    const getPointsSymptome = (sym) => {
                                        switch (sym.toLowerCase()) {
                                            case "fievre_elevee":
                                                return 3;
                                            case "douleur_intense":
                                                return 4;
                                            case "douleur_thoracique":
                                            case "difficulte_respiratoire":
                                                return 5;
                                            case "perte_connaissance":
                                                return 5;
                                            case "hemorragie":
                                                return 5;
                                            case "douleur_moderee":
                                                return 2;
                                            case "nausee":
                                                return 1;
                                            case "fatigue":
                                                return 1;
                                            case "confusion":
                                                return 2;
                                            default:
                                                return 0;
                                        }
                                    };

                                    const points = getPointsSymptome(symptome);
                                    const symptomeAffiche = symptome.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());

                                    return (
                                        <tr key={index}>
                                            <th scope="row">{index + 1}</th>
                                            <td>{symptomeAffiche}</td>
                                            <td>
                                                <span className={`badge ${points >= 5 ? 'bg-danger' : points >= 3 ? 'bg-warning' : 'bg-info'}`}>
                                                    {points} pts
                                                </span>
                                            </td>
                                        </tr>
                                    );
                                })
                            )}
                        </tbody>
                    </table>
                </div>
            </div>

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
