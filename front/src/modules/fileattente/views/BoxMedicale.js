import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../../../styles/Table.css';
import {BOX_MEDICALE, CHEMIN, LIBERER_BOX} from "../../../constants/back";

export default function BoxMedicale() {
    const [boxMedicales, setBoxMedicales] = useState([]);

    const setBoxMedicaleData = async () => {
        axios.get(BOX_MEDICALE)
            .then((response) => {setBoxMedicales(response.data);
            })  .catch(error => {alert("Error Ocurred while loading data:" + error);
        });
    }

    const formatHeure = (dateString) => {
        if (!dateString) return '--:--';
        const date = new Date(dateString);
        return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit', second: "2-digit" });
    };

    useEffect(() => {
        setBoxMedicaleData();
        const interval = setInterval(setBoxMedicaleData, 1000)
        return() => clearInterval(interval)
    }, []);

    const liberer_box = async (boxMedicale) => {
        if (boxMedicale.statutBox === "OCCUPEE") {
            await axios.post(`${LIBERER_BOX}/${boxMedicale.idBoxMedicale}/${boxMedicale.patient?.idPatient}`);

            window.location.href = `/api/type-acte-medical/${boxMedicale.patient?.idPatient}`
        

        } else {
            alert("Pas de patient dans la box");
        }

    };

    if (boxMedicales.length === 0)
        return (<div className="container text-center" >Pas de box medicale</div>)
    return (
        <div className="container text-center">
            <h4 className=" mx-2">Box Medicales</h4>
            <div className="row">
                <table className="table table-sm table-bordered table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Box</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Prénom</th>
                        <th scope="col">Heure d'entrée</th>
                        <th scope="col">Temps estimé</th>
                        <th scope="col">Temps restant (secondes)</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody className="table-group-divider">
                    {
                        boxMedicales.map( (boxMedicale) => (
                            <tr key={boxMedicale.idBoxMedicale}>
                                <td>{boxMedicale.nomBox}</td>
                                <td>{boxMedicale.patient?.nomPatient || '--'}</td>
                                <td>{boxMedicale.patient?.prenomPatient || '--'}</td>
                                <td>{formatHeure(boxMedicale.heureEntree) || '--'}</td>
                                <td>{boxMedicale.tempsEstime || '--'}</td>
                                <td>{boxMedicale.tempsRestant || '--'}</td>

                                <td>
                                    <button className="btn btn-primary btn-sm mx-1"
                                            onClick={() => {
                                                
                                           liberer_box(boxMedicale)}
                                    }>
                                        Définir parcours
                                    </button>
                                </td>





                            </tr>



                        ))
                    }


                    </tbody>
                </table>
            </div>
        </div>
    );
};
