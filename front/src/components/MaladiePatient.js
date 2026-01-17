import React, { useEffect, useState } from 'react';
import axios from "axios";
import { useParams } from 'react-router-dom';
import { LOCAL_HOST } from "../constants/back";

export default function MaladiePatient() {
    const { idPatient } = useParams();
    const [maladies, setMaladies] = useState([]);

    const fetchMaladies = async () => {
        try {
            const response = await axios.get(`${LOCAL_HOST}/api/maladie-patient/patient/${idPatient}`);
            setMaladies(Array.isArray(response.data) ? response.data : [response.data]);
        } catch (error) {
            console.error("Erreur API:", error);
            setMaladies([]);
        }
    };

    useEffect(() => {
        fetchMaladies();
    }, [idPatient]);

    if (maladies.length === 0) {
        return <div className="container text-center">Aucune maladie enregistrée</div>;
    }

    return (
        <div className="container">
            <h4 className="my-4">Maladies du patient</h4>
            <table className="table table-sm table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Niveau de gravité</th>
                        <th>Date du diagnostic</th>
                    </tr>
                </thead>
                <tbody>
                    {maladies.map((maladie) => (
                        <tr key={maladie.idMaladiePatient}>
                            <td>{maladie.maladie.idMaladie}</td>
                            <td>{maladie.maladie.nomMaladie}</td>
                            <td>{maladie.niveauCCMU}</td>
                            <td>{new Date(maladie.dateDiagnostic).toLocaleDateString()}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
