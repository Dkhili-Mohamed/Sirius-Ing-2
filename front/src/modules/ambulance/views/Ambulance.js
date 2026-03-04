import React, { useEffect, useState, useRef } from 'react';
import axios from "axios";

import { GET_AMBULANCES, LOCAL_HOST_AMBULANCE } from "../../../constants/back";
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

export default function Ambulance() {   
  const [ambulances, setAmbulances] = useState([]);
  const [idMeilleure, setIdMeilleure] = useState(null);  // ✅ nouvel état
  const mapRef = useRef(null);

  const GET_MEILLEURE_AMBULANCE = LOCAL_HOST_AMBULANCE + "meilleure"; // endpoint pour la meilleure ambulance

  // Récupérer toutes les ambulances
  const setAmbulanceData = async () => {
    try {
      const response = await axios.get(GET_AMBULANCES);
      setAmbulances(response.data);
    } catch (error) {
      alert("Erreur lors du chargement des ambulances : " + error);
    }
  }

  // Récupérer la meilleure ambulance
  const fetchMeilleureAmbulance = async () => {
    try {
      const response = await axios.get(GET_MEILLEURE_AMBULANCE);
      setIdMeilleure(response.data);
    } catch (error) {
      console.log("Erreur lors de la récupération de la meilleure ambulance ", error);
    }
  }

  // Charger les données au démarrage
  useEffect(() => {
    setAmbulanceData();
    fetchMeilleureAmbulance();
  }, []);

  // Ajuster automatiquement la carte 
  useEffect(() => {
    if (ambulances.length && mapRef.current) {
      const bounds = ambulances
        .filter(a => a.ambulancelatitude && a.ambulancelongitude)
        .map(a => [a.ambulancelatitude, a.ambulancelongitude]);

      if (bounds.length) {
        mapRef.current.fitBounds(bounds, { padding: [50, 50] });
      }
    }
  }, [ambulances]);

  if (ambulances.length === 0)
     return (<div className="container text-center">Aucune ambulance</div>);

  return (
    <div className="container text-center">
      <h4 className="mx-2">Liste des ambulances</h4>

      {/* Affichage de la meilleure ambulance */}
      {idMeilleure && (
        <div className="alert alert-success mt-2">
           L'ambulance la plus adaptée est l'ambulance {idMeilleure}
        </div>
      )}

      {/* Tableau des ambulances */}
      <div className="row">
        <table className="table table-sm table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Adresse</th>
              <th scope="col">Disponibilité</th>
              <th scope="col">Vitesse Moyenne(km/H)</th>
              <th scope="col">Equipement</th>
              <th scope="col">Expérience</th>
              <th scope="col">Latitude</th>
              <th scope="col">Longitude</th>
              <th scope="col">Distance(Km)</th>
              <th scope="col">Temps Trajet </th>
              <th scope="col">Score Trajet</th>
              <th scope="col">Score Global</th>
            </tr>
          </thead>
          <tbody className="table-group-divider">
            {ambulances.map((ambulance, index) => (
              <tr key={index}>   
                <th scope="row">{ambulance.idambulance}</th>
                <td>{ambulance.adresseambulance}</td>
                <td>{ambulance.disponibiliteambulance ? "Oui" : "Non"}</td>
                <td>{ambulance.vitessemoyambulance}</td>
                <td>{ambulance.equipementambulance}</td>
                <td>{ambulance.experienceambulance}</td>
                <td>{ambulance.ambulancelatitude}</td>
                <td>{ambulance.ambulancelongitude}</td>
                <td>{ambulance.ambulancedistance}</td>
                <td>{ambulance.tempstrajetminutes}</td>
                <td>{ambulance.notetrajet}</td>
                <td>{ambulance.noteglobale}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Carte Leaflet sous le tableau */}
      <div className="row mt-4">
        <MapContainer
          ref={mapRef}
          center={[48.8566, 2.3522]} 
          zoom={5}
          style={{ height: "400px", width: "100%" }}
        >
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributeurs'
          />

          {/* Markers des ambulances */}
          {ambulances
            .filter(a => a.ambulancelatitude && a.ambulancelongitude)
            .map((ambulance, index) => (
              <Marker
                key={index}
                position={[ambulance.ambulancelatitude, ambulance.ambulancelongitude]}
              >
                <Popup>
                  Id: {ambulance.idambulance}<br/>
                  Adresse: {ambulance.adresseambulance}<br/>
                  Disponibilité: {ambulance.disponibiliteambulance ? "Oui" : "Non"}
                </Popup>
              </Marker>
          ))}

        </MapContainer>
      </div>
    </div>
  );
};