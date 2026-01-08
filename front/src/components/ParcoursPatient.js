import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css';
import { PARCOURS_PATIENT } from "../constants/back";
import { useParams } from 'react-router-dom';


export default function ParcoursPatient() {
  const { idPatient } = useParams();
  const [parcoursPatient, setParcoursPatient] = useState([]);
  const [loading, setLoading] = useState(false);

  const setParoursPatientData = async (idPatient) => {
    setLoading(true);
    axios.get(PARCOURS_PATIENT + idPatient)
      .then((response) => {
        setParcoursPatient(response.data);
        setLoading(false);
      })
      .catch(error => {
        setLoading(false);
        console.error("Error:", error);
      });
  }

  useEffect(() => {
    if (idPatient) {
      setParoursPatientData(idPatient);
    }
  }, [idPatient]);

  // --- Gestion des états d'affichage ---
  if (!idPatient) 
    return (<div className="container text-center mt-5">Veuillez sélectionner un patient</div>);
  
  if (loading) 
    return (<div className="container text-center mt-5">Chargement du parcours...</div>);

  if (parcoursPatient.length === 0)
    return (<div className="container text-center mt-5">Aucun acte médical trouvé pour ce patient</div>);

  // --- Rendu final du tableau ---
  return (
    <div className="container text-center">
      <h4 className="my-4">Parcours du Patient n°{idPatient}</h4>
      <div className="table-responsive">
        <table className="table table-sm table-bordered table-hover">
          <thead className="thead-light">
            <tr>
              <th scope="col">idActe</th>
              <th scope="col">idParcours</th>
              <th scope="col">idType</th>
              <th scope="col">Libellé</th>
              <th scope="col">Statut</th>
              <th scope="col">Espace n°</th>
              <th scope="col">idEspace</th>
              <th scope="col">idSalle</th>
              <th scope="col">Ordre</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            {parcoursPatient.map((parcours) => (
              <tr key={parcours.idActeMedical}>
                <td>{parcours.idActeMedical}</td> {/* Ajouté pour correspondre au header */}
                <td>{parcours.idParcours}</td>
                <td>{parcours.idTypeActeMedical}</td>
                <td>{parcours.libelle}</td>
                <td>
                  <span className={`badge ${parcours.statut === 'EN_ATTENTE' ? 'bg-warning' : 'bg-success'}`}>
                    {parcours.statut}
                  </span>
                </td>
                <td>{parcours.numeroEspace}</td>
                <td>{parcours.idEspace}</td>
                <td>{parcours.idSalle}</td>
                <td>{parcours.ordre}</td>
                <td>
                  <button className="btn btn-sm btn-outline-primary mx-1">Edit</button>
                  <button className="btn btn-sm btn-outline-danger mx-1">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};