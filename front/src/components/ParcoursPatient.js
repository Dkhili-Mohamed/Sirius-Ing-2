import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css';
import { PARCOURS_PATIENT, CHEMIN} from "../constants/back";
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

  const [departChemin, setDepartChemin] = useState(null);

  const clickTermine = (parcours) => {
    setDepartChemin({
      idParcours: parcours.idParcours,
      ordre: parcours.ordre,
      idDepart: parcours.idEspace
    });
    
    setTimeout(() => {
      setParoursPatientData(idPatient);
    }, 100);

  };

  const commencer = (parcours) => {
    if (parcours && parcours.statut === 'EN_ATTENTE'){
      setDepartChemin({
        idParcours: parcours.idParcours, 
        ordre : 0,
        idDepart : 6
      });
    }else{
      alert("Le parcours est en cours.")
    }
    setTimeout(() => {
      setParoursPatientData(idPatient);
    }, 500);
    
  }

  if (!idPatient) 
    return (<div className="container text-center mt-5">Veuillez sélectionner un patient</div>);
  
  if (loading) 
    return (<div className="container text-center mt-5">Chargement du parcours...</div>);

  if (parcoursPatient.length === 0)
    return (<div className="container text-center mt-5">Aucun acte médical trouvé pour ce patient</div>);

  return (
    <div className="container text-center">

      <div>
        <button className="btn btn-primary"
          onClick={() => commencer(parcoursPatient[0])}
        >Commencer parcours</button>

      </div>
      <h4 className="my-4">Parcours du Patient n°{idPatient}</h4>
      <div className="table-responsive">
        <table className="table table-sm table-bordered table-hover">
          <thead className="thead-light">
            <tr>
              <th scope="col">Ordre</th>
              <th scope="col">Libellé</th>
              <th scope="col">Statut</th>
              <th scope="col">Espace n°</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            {parcoursPatient.map((parcours) => (
              <tr key={parcours.idActeMedical}>
                <td>{parcours.ordre}</td>
                <td>{parcours.libelle}</td>
                <td>
                  <span className={`badge ${parcours.statut === 'EN_ATTENTE' ? 'bg-warning' : 'bg-success'}`}>
                    {parcours.statut}
                  </span>
                </td>
                <td>{parcours.numeroEspace}</td>
                <td>
                  {parcours.statut === 'EN_COURS' && (
                    <button 
                      className="btn btn-sm btn-outline-success mx-1" 
                      onClick={() => clickTermine(parcours)}>Terminé</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      
      {departChemin && (
        <div className="mt-5 p-4 border rounded bg-light">
          <Chemin idParcours={departChemin.idParcours} 
              ordre={departChemin.ordre} 
              idDepart={departChemin.idDepart} />
          <button className="btn btn-sm btn-secondary mt-2" onClick={() => setDepartChemin(null)}>
            Fermer le chemin
          </button>
        </div>
      )}
    </div>
  );  
};

function Chemin({ idParcours, ordre, idDepart }) {
  const [chemin, setChemin] = useState([]);

  const setCheminData = async(idParcours, ordre, idDepart) => {
    // On s'assure que les trois IDs sont présents avant de lancer l'appel
    if (idDepart && idParcours) {
      
      axios.get(`${CHEMIN}/${idParcours}/${ordre}/${idDepart}`)
      .then((response) => {
        setChemin(response.data);
      })
      .catch(error => {
        console.error("Erreur lors de la récupération du chemin :", error);
      });
    }
  }

  useEffect(() => {
    if(idDepart && idParcours){
      setCheminData(idParcours, ordre, idDepart)
    }
  }, [idParcours, ordre, idDepart]);

  if(!chemin)
    return (<div className="container text-center" >Parcours Terminé.</div>)

  if (chemin.length === 0)
    return (<div className="container text-center" >Aucune salle disponible, Veillez patienter.</div>)
  
  return (
    <div>
      <h5 className="text-primary">Itinéraire conseillé</h5>
      <div className="d-flex justify-content-center align-items-center flex-wrap">
        {chemin.map((etape, index) => (
          <React.Fragment key={etape.idSalle || index}>
            <div className="p-2 m-2 border rounded shadow-sm bg-white">
              <strong>{etape.numeroEspace}</strong> <br />
              <small className="text-muted">{etape.typeEspace}</small>
            </div>
            {index < chemin.length - 1 && <span className="h6">ensuite</span>}
          </React.Fragment>
        ))}
      </div>
    </div>
  );
}





