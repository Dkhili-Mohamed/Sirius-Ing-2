import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css'; 
import { GET_AMBULANCES, LOCAL_HOST_AMBULANCE } from "../constants/back";

export default function AmbulanceList() {   // <-- nom modifié
  const [ambulances, setAmbulances] = useState([]);

  const setAmbulanceData = async () => {
    axios.get(GET_AMBULANCES)
      .then((response) => setAmbulances(response.data))
      .catch(error => alert("Error while loading ambulances: " + error));
  }

  const confirmRemoveAmbulance = (id) => {
    if (window.confirm('Are you sure?')) {
      removeAmbulance(id);
    }
  }

  const removeAmbulance = async (id) => {
    axios.delete(LOCAL_HOST_AMBULANCE + id)
      .then(() => setAmbulanceData())
      .catch(error => alert("Error in removeAmbulance: " + error));
  }

  useEffect(() => {
    setAmbulanceData();
  }, []);

  if (ambulances.length === 0) return (<div className="container text-center">No ambulances</div>);

  return (
    <div className="container text-center">
      <h4 className="mx-2">Ambulances List</h4>
      <div className="row">
        <table className="table table-sm table-bordered table-hover">
          <thead>
            <tr>
              <th>Id</th>
              <th>Adresse</th>
              <th>Disponibilité</th>
              <th>Vitesse Moyenne</th>
              <th>Equipement</th>
              <th>Expérience</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody className="table-group-divider">
            {ambulances.map((a) => (
              <tr key={a.idambulance}>   {/* <-- clé modifiée */}
                <th>{a.idambulance}</th>
                <td>{a.adresseambulance}</td>
                <td>{a.disponibiliteambulance ? "Oui" : "Non"}</td>
                <td>{a.vitessemoyambulance}</td>
                <td>{a.equipementambulance}</td>
                <td>{a.experienceambulance}</td>
                <td>
                  <button
                    className="btn btn-outline-danger"
                    onClick={() => confirmRemoveAmbulance(a.idambulance)}
                  >Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
