import React, { useState, useEffect } from 'react';
import axios from "axios";
import { patientService } from './patientService';
import { LOCAL_HOST_PATIENT } from '../../../constants/back';
import {NOMBRE_PATIENTS_FILE, NOMBRE_PATIENTS_FILE_URGENTS, NOMBRE_PATIENTS_FILE_INTERMEDIAIRES, NOMBRE_PATIENTS_FILE_NON_URGENTS, NOMBRE_BOX_LIBRES} from '../../../constants/back';
import PatientModal from './PatientModal';

const FileAttente = () => {
    const [patients, setPatients] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [stats, setStats] = useState({total: 0, urgents: 0, intermediaires: 0, nonUrgents: 0, boxLibres: 0});
    useEffect(() => {
        chargerFileAttente();
        const interval = setInterval(chargerFileAttente, 1000);
        return () => clearInterval(interval);
    }, []);

    const chargerFileAttente = async () => {
        try {
            //const data = await patientService.getFileAttente();
            const response = await axios.get(LOCAL_HOST_PATIENT + 'file-attente-dto')
            setPatients(response.data);
        } catch (error) {
            console.error('Erreur:', error);
        }
    };

    const handlePatientAdd = async (patientData) => {
        try {
            console.log('Données reçues par FileAttente:', patientData);
            const response = await patientService.createPatient(patientData);
            console.log('Réponse du backend:', response);
            await chargerFileAttente();
        } catch (error) {
            console.error('Erreur:', error);
        }
    };

    const formatHeure = (dateString) => {
        if (!dateString) return '--:--';
        const date = new Date(dateString);
        return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit', second: "2-digit" });
    };

    const formatJour = (dateString) => {
        if (!dateString) return '--/--/--';
        const date = new Date(dateString);
        return date.toLocaleDateString();
    };

    const getNiveauUrgenceStyle = (niveau) => {
        switch(niveau) {
            case 'URGENT':
                return { backgroundColor: '#ffebee', color: '#d32f2f', fontWeight: 'bold' };
            case 'INTERMEDIAIRE':
                return { backgroundColor: '#fff3e0', color: '#f57c00', fontWeight: 'bold' };
            case 'NON_URGENT':
                return { backgroundColor: '#e8f5e8', color: '#388e3c', fontWeight: 'bold' };
            default:
                return { backgroundColor: '#f5f5f5', color: '#757575' };
        }
    };

    const setStatsData = async () => {
        const nombrePatients = await axios.get(NOMBRE_PATIENTS_FILE);
        const nombrePatientsUrgents = await axios.get(NOMBRE_PATIENTS_FILE_URGENTS);
        const nombrePatientsIntermediaires = await axios.get(NOMBRE_PATIENTS_FILE_INTERMEDIAIRES);
        const nombrePatientsNonUrgents = await axios.get(NOMBRE_PATIENTS_FILE_NON_URGENTS);
        const nombreBoxLibres = await axios.get(NOMBRE_BOX_LIBRES);


        const statsMAJ = {total: nombrePatients.data, urgents: nombrePatientsUrgents.data, intermediaires: nombrePatientsIntermediaires.data, nonUrgents: nombrePatientsNonUrgents.data, boxLibres: nombreBoxLibres.data};
        setStats(statsMAJ);



    }

    useEffect(() => {
        setStatsData();
        const interval = setInterval(setStatsData, 1000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div style={{ padding: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <h1>File d'Attente</h1>
                <h3>Statistiques</h3>
                <div style={{ display: 'flex', gap: '20px'}}>
                    <div>Box libres: <strong style={{ color: 'blue'}}>{stats.boxLibres}/3</strong></div>
                    <div>Total: <strong style={{ color: 'black'}}>{stats.total}/20</strong></div>
                    <div>Urgents: <strong style={{ color: 'red'}}>{stats.urgents}/{stats.total}</strong></div>
                    <div>Intermédiaires: <strong style={{ color: 'orange'}}>{stats.intermediaires}/{stats.total}</strong></div>
                    <div>Non urgents: <strong style={{ color: 'green'}}>{stats.nonUrgents}/{stats.total}</strong></div>



                </div>

                {stats.total > 10 && stats.total < 20 && (
                    <div>
                        <h5><span>File d'attente presque saturée. Veuillez libérer les box.</span></h5>
                    </div>
                )}

                {stats.total >= 20 && (
                    <div>
                        <h5><span>File d'attente saturée! Impossible d'ajouter des patients.</span></h5>
                    </div>
                )}

                <button 
                    onClick={() => setIsModalOpen(true)}
                    disabled={stats.total >= 20}
                    style={{
                        backgroundColor: '#007bff',
                        color: 'white',
                        padding: '12px 24px',
                        border: 'none',
                        borderRadius: '8px',
                        cursor: 'pointer',
                        fontSize: '16px',
                        fontWeight: '600'
                    }}
                >
                    Ajouter Patient
                </button>
            </div>

            <PatientModal 
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onPatientAdd={handlePatientAdd}
            />

            <table style={{
                width: '100%',
                borderCollapse: 'collapse',
                boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                backgroundColor: 'white'
            }}>
                <thead>
                    <tr style={{ backgroundColor: '#f8f9fa', borderBottom: '2px solid #dee2e6' }}>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Rang</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Nom</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Prénom</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Âge</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Score</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Niveau</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Jour d'arrivée</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Heure d'arrivée</th>
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Temps d'attente estimé (s)</th>

                    </tr>
                </thead>
                <tbody>
                    {patients.map((fileAttente, index) => {
                        const patient = fileAttente.patient;
                        return (
                            <tr key={fileAttente.idFileAttente} style={{ borderBottom: '1px solid #dee2e6' }}>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6', textAlign: 'center' }}>
                                    {index + 1}
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    <strong>{patient.nomPatient}</strong>
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    {patient.prenomPatient}
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    {patient.agePatient} ans
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6', textAlign: 'center' }}>
                                    <strong>{patient.scoreUrgence}</strong>
                                </td>
                                <td style={{ 
                                    padding: '12px', 
                                    border: '1px solid #dee2e6',
                                    textAlign: 'center',
                                    ...getNiveauUrgenceStyle(patient.niveauUrgence)
                                }}>
                                    {patient.niveauUrgence}
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    {formatJour(fileAttente.dateEntree)}
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    {formatHeure(fileAttente.dateEntree)}
                                </td>
                                <td style={{ padding: '12px', border: '1px solid #dee2e6' }}>
                                    {fileAttente.tempsAttenteEstime}
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default FileAttente;
