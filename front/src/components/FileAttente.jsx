import React, { useState, useEffect } from 'react';
import axios from "axios";
import { patientService } from '../services/patientService';
import { LOCAL_HOST_PATIENT } from '../constants/back';
import PatientModal from './PatientModal';

const FileAttente = () => {
    const [patients, setPatients] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        chargerFileAttente();
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
        return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
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

    return (
        <div style={{ padding: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <h2>File d'Attente</h2>
                <button 
                    onClick={() => setIsModalOpen(true)}
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
                        <th style={{ padding: '12px', textAlign: 'left', border: '1px solid #dee2e6' }}>Heure d'arrivée</th>
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
                                    {formatHeure(patient.dateArrivee)}
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
