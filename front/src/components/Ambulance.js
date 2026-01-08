import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css'; 
import { GET_AMBULANCES, LOCAL_HOST_AMBULANCE } from "../constants/back";

export default function Ambulance() {   
  const [ambulances, setAmbulances] = useState([]);

  const setAmbulanceData = async () => {
    axios.get(GET_AMBULANCES)
      .then((response) => setAmbulances(response.data))
      .catch(error => alert("Error while loading ambulances: " + error));
  }

  

  const removeAmbulance = async (id) => {
    axios.delete(LOCAL_HOST_AMBULANCE + id)
      .then(() => setAmbulanceData())
      .catch(error => alert("Error in removeAmbulance: " + error));
  }

  useEffect(() => {
    setAmbulanceData();
  }, []);

  if (ambulances.length === 0)
     return (<div className="container text-center">No ambulances</div>)

  return (
    <div className="container text-center">
      <h4 className="mx-2">Ambulances List</h4>
      <div className="row">
        <table className="table table-sm table-bordered table-hover">
          <thead>
          <tr>
              <th scope="col">Id</th>
              <th scope="col">Adresse</th>
              <th scope="col">Disponibilité</th>
              <th scope="col">Vitesse Moyenne</th>
              <th scope="col">Equipement</th>
              <th scope="col">Expérience</th>
              <th scope="col">Actions</th>
           </tr>
           </thead>
           <tbody className="table-group-divider">
            {
                ambulances.map((ambulance, index) => (
                    <tr key={index}>   
                        <th scope="row">{ambulance.idambulance}</th>
                        <td>{ambulance.adresseambulance}</td>
                        <td>{ambulance.disponibiliteambulance ? "Oui" : "Non"}</td>
                        <td>{ambulance.vitessemoyambulance}</td>
                        <td>{ambulance.equipementambulance}</td>
                        <td>{ambulance.experienceambulance}</td>

                    </tr>
                ))
                
            }
          </tbody>
        </table>
      </div>
    </div>
  );
};
