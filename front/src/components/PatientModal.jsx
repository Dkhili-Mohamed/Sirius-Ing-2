import React, { useState, useEffect } from 'react';
import { patientService } from '../services/patientService';

const PatientModal = ({ isOpen, onClose, onPatientAdd }) => {
    const [nom, setNom] = useState('');
    const [prenom, setPrenom] = useState('');
    const [age, setAge] = useState('');
    
    const [symptomes, setSymptomes] = useState({
        douleurThoracique: false,
        difficultesRespiratoires: false,
        perteConnaissance: false,
        confusion: false,
        saignementAbondant: false,
        vomissementsPersistants: false,
        fievreElevee: false,
        frissons: false,
        touxSevere: false,
        malaiseGeneral: false,
        fatigueExtreme: false,
        vertigesIntenses: false,
        mauxTeteSeveres: false,
        visionTroublee: false,
        difficultesParole: false,
        faiblesseBrasJambes: false,
        engourdissementFace: false
    });

    const [score, setScore] = useState(0);
    const [niveau, setNiveau] = useState('NON_URGENT');

    useEffect(() => {
        const nouveauScore = calculerScore();
        let nouveauNiveau;
        
        if (nouveauScore >= 8) {
            nouveauNiveau = 'URGENT';
        } else if (nouveauScore >= 4) {
            nouveauNiveau = 'INTERMEDIAIRE';
        } else {
            nouveauNiveau = 'NON_URGENT';
        }
        
        setScore(nouveauScore);
        setNiveau(nouveauNiveau);
    }, [symptomes, age]);

    const calculerScore = () => {
        let score = 0;
        
        if (symptomes.douleurThoracique) score += 5;
        if (symptomes.difficultesRespiratoires) score += 5;
        if (symptomes.perteConnaissance) score += 5;
        if (symptomes.confusion) score += 2;
        if (symptomes.saignementAbondant) score += 5;
        if (symptomes.vomissementsPersistants) score += 2;
        if (symptomes.fievreElevee) score += 2;
        if (symptomes.frissons) score += 1;
        if (symptomes.touxSevere) score += 2;
        if (symptomes.malaiseGeneral) score += 1;
        if (symptomes.fatigueExtreme) score += 1;
        if (symptomes.vertigesIntenses) score += 2;
        if (symptomes.mauxTeteSeveres) score += 1;
        if (symptomes.visionTroublee) score += 2;
        if (symptomes.difficultesParole) score += 3;
        if (symptomes.faiblesseBrasJambes) score += 2;
        if (symptomes.engourdissementFace) score += 3;
        
        const ageNum = parseInt(age) || 0;
        
        if (age && ageNum > 0) {
            if (ageNum == 1) score += 4;
            else if (ageNum <= 5) score += 3;
            else if (ageNum <= 12) score += 2;
            else if (ageNum <= 17) score += 1;
            else if (ageNum >= 65) score += 3;
        }
        
        return score;
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

    const handleSymptomeChange = (symptome) => {
        setSymptomes(prev => ({
            ...prev,
            [symptome]: !prev[symptome]
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        const scoreFinal = calculerScore();
        let niveauFinal;
        
        if (scoreFinal >= 8) {
            niveauFinal = 'URGENT';
        } else if (scoreFinal >= 4) {
            niveauFinal = 'INTERMEDIAIRE';
        } else {
            niveauFinal = 'NON_URGENT';
        }
        
        const patientData = {
            nomPatient: nom,
            prenomPatient: prenom,
            agePatient: parseInt(age),
            scoreUrgence: scoreFinal,
            patient_symptomes: Object.keys(symptomes).filter(key => symptomes[key]).map(s => {
                const map = {
                    douleurThoracique: 'douleur_thoracique',
                    difficultesRespiratoires: 'difficulte_respiratoire',
                    perteConnaissance: 'perte_connaissance',
                    saignementAbondant: 'hemorragie',
                    fievreElevee: 'fievre_elevee',
                    vomissementsPersistants: 'nausee',
                    frissons: 'frissons',
                    touxSevere: 'toux_severe',
                    malaiseGeneral: 'malaise_general',
                    fatigueExtreme: 'fatigue',
                    vertigesIntenses: 'vertiges_intenses',
                    mauxTeteSeveres: 'maux_tete_severes',
                    visionTroublee: 'vision_troublee',
                    difficultesParole: 'difficultes_parole',
                    faiblesseBrasJambes: 'faiblesse_bras_jambes',
                    engourdissementFace: 'engourdissement_face',
                    confusion: 'confusion'
                };
                return map[s] || s;
            })
        };
        
        console.log('Données envoyées:', patientData);
        onPatientAdd(patientData);
        
        setNom('');
        setPrenom('');
        setAge('');
        setSymptomes(Object.keys(symptomes).reduce((acc, key) => ({ ...acc, [key]: false }), {}));
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0,0,0,0.5)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1000
        }}>
            <div style={{
                backgroundColor: 'white',
                padding: '30px',
                borderRadius: '12px',
                maxWidth: '800px',
                width: '90%',
                maxHeight: '90vh',
                overflowY: 'auto',
                boxShadow: '0 4px 20px rgba(0,0,0,0.15)'
            }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                    <h2 style={{ margin: 0, color: '#1f2937' }}> Nouveau Patient</h2>
                    <button 
                        onClick={onClose}
                        style={{
                            backgroundColor: 'transparent',
                            border: 'none',
                            fontSize: '24px',
                            cursor: 'pointer',
                            color: '#6b7280'
                        }}
                    >
                        ×
                    </button>
                </div>

                <form onSubmit={handleSubmit}>
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px', marginBottom: '20px' }}>
                        <div>
                            <label style={{ display: 'block', marginBottom: '5px', fontWeight: '600', color: '#374151' }}>
                                Nom:
                            </label>
                            <input
                                type="text"
                                value={nom}
                                onChange={e => setNom(e.target.value)}
                                required
                                style={{
                                    width: '100%',
                                    padding: '10px',
                                    border: '1px solid #d1d5db',
                                    borderRadius: '6px',
                                    fontSize: '14px'
                                }}
                            />
                        </div>
                        
                        <div>
                            <label style={{ display: 'block', marginBottom: '5px', fontWeight: '600', color: '#374151' }}>
                                Prénom:
                            </label>
                            <input
                                type="text"
                                value={prenom}
                                onChange={e => setPrenom(e.target.value)}
                                required
                                style={{
                                    width: '100%',
                                    padding: '10px',
                                    border: '1px solid #d1d5db',
                                    borderRadius: '6px',
                                    fontSize: '14px'
                                }}
                            />
                        </div>
                        
                        <div>
                            <label style={{ display: 'block', marginBottom: '5px', fontWeight: '600', color: '#374151' }}>
                                Âge:
                            </label>
                            <input
                                type="number"
                                value={age}
                                onChange={e => setAge(e.target.value)}
                                required
                                min="0"
                                max="120"
                                style={{
                                    width: '100%',
                                    padding: '10px',
                                    border: '1px solid #d1d5db',
                                    borderRadius: '6px',
                                    fontSize: '14px'
                                }}
                            />
                        </div>
                    </div>

                    <div style={{ marginBottom: '20px' }}>
                        <label style={{ display: 'block', marginBottom: '10px', fontWeight: '600', color: '#374151' }}>
                             Symptômes:
                        </label>
                        <div style={{ 
                            display: 'grid', 
                            gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', 
                            gap: '10px',
                            maxHeight: '300px',
                            overflowY: 'auto',
                            border: '1px solid #e5e7eb',
                            borderRadius: '6px',
                            padding: '15px',
                            backgroundColor: '#f9fafb'
                        }}>
                            {Object.entries(symptomes).map(([key, value]) => (
                                <label key={key} style={{ 
                                    display: 'flex', 
                                    alignItems: 'center', 
                                    cursor: 'pointer',
                                    padding: '5px',
                                    borderRadius: '4px',
                                    transition: 'background-color 0.2s'
                                }}>
                                    <input
                                        type="checkbox"
                                        checked={value}
                                        onChange={() => handleSymptomeChange(key)}
                                        style={{ marginRight: '8px', transform: 'scale(1.2)' }}
                                    />
                                    <span style={{ fontSize: '14px', color: '#4b5563' }}>
                                        {key.replace(/([A-Z])/g, ' $1').trim()}
                                    </span>
                                </label>
                            ))}
                        </div>
                    </div>

                    <div style={{ 
                        display: 'flex', 
                        justifyContent: 'space-between', 
                        alignItems: 'center',
                        padding: '15px',
                        backgroundColor: '#f3f4f6',
                        borderRadius: '8px',
                        marginBottom: '20px'
                    }}>
                        <div>
                            <span style={{ fontSize: '16px', fontWeight: '600', color: '#374151' }}>
                                Score d'urgence: 
                            </span>
                            <span style={{ 
                                fontSize: '20px', 
                                fontWeight: 'bold', 
                                color: '#1f2937',
                                marginLeft: '10px'
                            }}>
                                {score}
                            </span>
                        </div>
                        <div>
                            <span style={{ fontSize: '16px', fontWeight: '600', color: '#374151' }}>
                                Niveau: 
                            </span>
                            <span style={{ 
                                padding: '6px 12px',
                                borderRadius: '6px',
                                fontSize: '14px',
                                fontWeight: 'bold',
                                marginLeft: '10px',
                                ...getNiveauUrgenceStyle(niveau)
                            }}>
                                {niveau}
                            </span>
                        </div>
                    </div>

                    <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                        <button
                            type="button"
                            onClick={onClose}
                            style={{
                                padding: '12px 24px',
                                border: '1px solid #d1d5db',
                                borderRadius: '6px',
                                backgroundColor: 'white',
                                color: '#6b7280',
                                cursor: 'pointer',
                                fontSize: '14px',
                                fontWeight: '600'
                            }}
                        >
                            Annuler
                        </button>
                        <button
                            type="submit"
                            style={{
                                padding: '12px 24px',
                                border: 'none',
                                borderRadius: '6px',
                                backgroundColor: '#10b981',
                                color: 'white',
                                cursor: 'pointer',
                                fontSize: '14px',
                                fontWeight: '600'
                            }}
                        >
                             Valider et Ajouter
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default PatientModal;
