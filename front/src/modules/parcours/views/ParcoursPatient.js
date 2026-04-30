import React, { useEffect, useState } from "react";
import axios from "axios";
import '../../../styles/ParcoursPatientCSS.css';
import { PARCOURS_PATIENT, CHEMIN} from "../../../constants/back";
import { useParams } from "react-router-dom";
import etage1 from "../../../assets/etage_1.png";
import etage2 from "../../../assets/etage_2.png";


export default function ParcoursPatient() {
  const { idPatient } = useParams();
  const [parcoursPatient, setParcoursPatient] = useState([]);
  const [loading, setLoading] = useState(false);

  const setParoursPatientData = async (idPatient) => {
    setLoading(true);
    axios
      .get(PARCOURS_PATIENT + idPatient)
      .then((response) => {
        setParcoursPatient(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setLoading(false);
        console.error("Error:", error);
      });
  };

  useEffect(() => {
    if (idPatient) {
      setParoursPatientData(idPatient);
    }
  }, [idPatient]);

  const [chemins, setChemins] = useState([]);

  const clickTermine = async (parcours) => {
    const reponce = await axios.post(
      `${CHEMIN}/${parcours.idParcours}/${parcours.ordre}/${parcours.idEspace}`,
    );

    setChemins(reponce.data);

    await setParoursPatientData(idPatient);
  };


  const commencer = async (parcours) => {
    if (parcours && parcours.statut === "EN_ATTENTE") {
      const reponce = await axios.post(`${CHEMIN}/${parcours.idParcours}/0/1`);

      setChemins(reponce.data);

      await setParoursPatientData(idPatient);
    } else {
      alert("Le parcours est en cours.");
    }
  };

  if (!idPatient)
    return (
      <div className="container text-center mt-5">
        Veuillez sélectionner un patient
      </div>
    );

  if (loading)
    return (
      <div className="container text-center mt-5">
        Chargement du parcours...
      </div>
    );

  if (parcoursPatient.length === 0)
    return (
      <div className="container text-center mt-5">
        Aucun acte médical trouvé pour ce patient
      </div>
    );

  return (
    <div className="container text-center">
      <div>
        <button
          className="btn btn-primary"
          onClick={() => commencer(parcoursPatient[0])}
        >
          Commencer parcours
        </button>
      </div>
      <h4 className="my-4">Parcours du Patient n°{idPatient}</h4>
      <div className="table-responsive">
        <table className="table table-sm table-bordered table-hover">
          <thead className="thead-light">
            <tr>
              <th scope="col">Ordre</th>
              <th scope="col">Libellé</th>
              <th scope="col">Statut</th>
              <th scope="col">Nom Salle</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            {parcoursPatient.map((parcours) => (
              <tr key={parcours.idActeMedical}>
                <td>{parcours.ordre}</td>
                <td>{parcours.libelle}</td>
                <td>
                  <span
                    className={`badge ${parcours.statut === "EN_ATTENTE" ? "bg-warning" : "bg-success"}`}
                  >
                    {parcours.statut}
                  </span>
                </td>
                <td>{parcours.nomSalle}</td>
                <td>
                  {parcours.statut === "EN_COURS" && (
                    <button
                      className="btn btn-sm btn-outline-success mx-1"
                      onClick={() => clickTermine(parcours)}
                    >
                      Terminé
                    </button> 
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {chemins &&  chemins.map((chemin) =>(
        
        <div className="mt-5 p-4 border rounded bg-light">
          <div>
            <div className="d-flex justify-content-center align-items-center flex-wrap">
              <svg
                id="monPlan"
                width="600"
                height="400"
                viewBox="0 0 600 400"
                style={{ display: "block" }}
              >
                <defs>
                  <marker
                    id="fleche"
                    markerWidth="4"
                    markerHeight="3"
                    refX="0"
                    refY="1.5"
                    orient="auto"
                  >
                    <polygon points="0 0, 4 1.5, 0 3" fill="green" />
                  </marker>
                </defs>

               <image
                  href={chemin.numeroEtage === "R+1" ? etage1 : etage2}
                  x="0"
                  y="0"
                  height="400"
                  width="600"
                  preserveAspectRatio="none"
                />

                <polyline
                  id="trajet"
                  points={chemin.coordonneesChemin}
                  stroke="red"
                  fill="none"
                  strokeWidth="4"
                  markerEnd="url(#fleche)"
                />

                <circle
                  cx={chemin.debut.split(" ")[0]}
                  cy={chemin.debut.split(" ")[1]}
                  r="5"
                  fill="blue"
                ></circle>
              </svg>
            </div>
          </div>
          <button
            className="btn btn-sm btn-secondary mt-2"
            onClick={() => setChemins(null)}
          >
            Fermer le chemin
          </button>
        </div>
      ))}
    </div>
  );
}

