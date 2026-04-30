import React, { useEffect, useState, useRef } from 'react';
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import redMarker from '../../../assets/red-marker.png';;


const patientIcon = L.icon({
  iconUrl: redMarker,   
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'), 
  iconSize: [50, 41],
  iconAnchor: [12, 41]
});

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

export const LOCAL_HOST_AMBULANCE_LOGIN = 'http://172.31.250.20:8081/api/ambulancelogin/';
export const LOGIN_AMBULANCE = LOCAL_HOST_AMBULANCE_LOGIN + 'login';

export const LOCAL_HOST_AMBULANCE_DATA = 'http://172.31.250.20:8081/api/ambulance/';
export const GET_AMBULANCES = LOCAL_HOST_AMBULANCE_DATA + 'available-and-sorted';
export const GET_MEILLEURE_AMBULANCE = LOCAL_HOST_AMBULANCE_DATA + 'meilleure';

export const GET_PATIENTS = 'http://172.31.250.20:8081/api/patientA/all';
export const GET_INTERVENTIONS = 'http://172.31.250.20:8081/interventions/recentes';

export default function Ambulance() {   
  const [ambulances, setAmbulances] = useState([]);
  const [idMeilleure, setIdMeilleure] = useState(null);  
  const mapRef = useRef(null);
  const [loggedIn, setLoggedIn] = useState(false);
  const [adresse, setAdresse] = useState("");
  const [mdps, setMdps] = useState("");
  const [error, setError] = useState("");
  const [patient, setPatient] = useState(null);
  const [historique, setHistorique] = useState([]);

  

  const setAmbulanceData = async () => {
    try {
      const response = await axios.get(GET_AMBULANCES);
      setAmbulances(response.data);
    } catch (error) {
      alert("Erreur lors du chargement des ambulances : " + error);
    }
  }

  const fetchMeilleureAmbulance = async () => {
    try {
      const response = await axios.get(GET_MEILLEURE_AMBULANCE);
      setIdMeilleure(response.data);
    } catch (error) {
      console.log("Erreur lors de la récupération de la meilleure ambulance ", error);
    }
  }
    
  const fetchPatient = async () => {
    try {
        const response = await axios.get(GET_PATIENTS);
        setPatient(response.data[0]); 
    } catch (error) {
      console.log("Erreur lors de la récupération du patient", error);
    }
  }

  const fetchHistorique = async () => {
    try {
      const res = await axios.get(GET_INTERVENTIONS);
      setHistorique(res.data); 
    } catch (error) {
      console.log("Erreur lors de la récupération des interventions:", error);
    }
  }

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

  const handleLogin = async () => {
    setError("");
    try {
      const response = await axios.post(LOGIN_AMBULANCE, {
        ambulanceloginadresse: adresse,
        ambulanceloginmdps: mdps
      });

      if (response.data) {
        setLoggedIn(true);
        setAmbulanceData();
        fetchMeilleureAmbulance();
        fetchPatient();
        fetchHistorique();
      } else {
        setError("Adresse ou mot de passe incorrect");
      }
    } catch (error) {
      setError("Erreur lors de la connexion : " + error.message);
    }
  }

  if (!loggedIn) {
    return (
      <div className="container text-center mt-5">
        <h4>Connexion Ambulance</h4>
        <div className="form-group my-2">
          <input 
            type="text" 
            className="form-control" 
            placeholder="Adresse" 
            value={adresse} 
            onChange={e => setAdresse(e.target.value)} 
          />
        </div>
        <div className="form-group my-2">
          <input 
            type="password" 
            className="form-control" 
            placeholder="Mot de passe" 
            value={mdps} 
            onChange={e => setMdps(e.target.value)} 
          />
        </div>
        <button className="btn btn-primary my-2" onClick={handleLogin}>Connexion</button>
        {error && <div className="text-danger mt-2">{error}</div>}
      </div>
    );
  }
  if (ambulances.length === 0)
     return (<div className="container text-center">Aucune ambulance</div>);
  const meilleureAmbulance = ambulances.find(
    a => a.idambulance === idMeilleure
  );


  return (
    <div className="container text-center">
      <h4 className="mx-2">Liste des ambulances</h4>
      {idMeilleure && meilleureAmbulance && patient &&(
        <div className="alert alert-success mt-2">
           L'ambulance n°{idMeilleure} arrivera à l'adresse {patient.adressepatientA} pour prendre en charge Mr/Mme {patient.nompatientA} dans {meilleureAmbulance.tempstrajetminutes}.
        </div>
      )}

      <div className="row">
        <table className="table table-sm table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Adresse</th>
              <th scope="col">Disponibilité</th>
              <th scope="col">Vitesse Moyenne(km/H)</th>
              
              <th scope="col">Latitude</th>
              <th scope="col">Longitude</th>
              <th scope="col">Distance(Km)</th>
              <th scope="col">Temps Trajet </th>
              
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
                
                <td>{ambulance.ambulancelatitude}</td>
                <td>{ambulance.ambulancelongitude}</td>
                <td>{ambulance.ambulancedistance}</td>
                <td>{ambulance.tempstrajetminutes}</td>
                
                <td>{ambulance.noteglobale}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="row mt-4">
        <h5>Historique des interventions</h5>
        <div style={{ maxHeight: "200px", overflowY: "scroll" }}>
          <table className="table table-sm table-bordered table-hover">
            <thead>
              <tr>
                <th>N°Intervention</th>
                <th>Nom</th>
                <th>Adresse</th>
                <th>Statut</th>
                <th>Date</th> 
              </tr>
            </thead>
            <tbody>
              {historique.map((intervention, index) => (
                <tr key={index}>
                  <td>{intervention.idintervention}</td>
                  <td>{intervention.nomintervention}</td>
                  <td>{intervention.adresseintervention}</td>
                  <td>{intervention.interventionstatut}</td>
                  <td>{intervention.dateintervention}</td> 
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    
      
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

          {patient && patient.patientAlatitude && patient.patientAlongitude && (
              <Marker
                position={[patient.patientAlatitude, patient.patientAlongitude]}
                icon={patientIcon}
              >
                <Popup>
                  Id: {patient.idpatientA}<br/>
                  Nom: {patient.nompatientA}<br/>
                  Adresse: {patient.adressepatientA}
                </Popup>
              </Marker>
          )}

          {meilleureAmbulance && patient &&
              meilleureAmbulance.ambulancelatitude && meilleureAmbulance.ambulancelongitude &&
              patient.patientAlatitude && patient.patientAlongitude && (
                <Polyline
                  positions={[
                    [meilleureAmbulance.ambulancelatitude, meilleureAmbulance.ambulancelongitude],
                    [patient.patientAlatitude, patient.patientAlongitude]
                  ]}
                  color="red"
                  weight={3}
                  dashArray="8, 6"
                />
            )}

        </MapContainer>
      </div>
    </div>
  );
};
