import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Sample.css';
import {GET_DPI, LOCAL_HOST_DPI, UPDATE_DPI, LOCAL_HOST} from "../constants/back";
import { useParams } from 'react-router-dom';

export default function DPI() {
    const { idPatient } = useParams();
    const [DPIs, setDPIs] = useState([]);

    const setDPIData = async () => {
        const url = idPatient 
            ? `${LOCAL_HOST_DPI}${idPatient}` 
            : LOCAL_HOST + '/api/dpi/all';

        axios.get(url)
            .then((response) => {
                const data = Array.isArray(response.data) ? response.data : [response.data];
                setDPIs(data);
            })
            .catch(error => {
                console.error("Erreur API:", error);
                setDPIs([]);
            });
    }
    const confirmRemoveDPI = (id) => {
        if (window.confirm('Are you sure?')) {
            removeDPI(id);
        }
    };
    const removeDPI = async (id) => {
        axios.delete(LOCAL_HOST_DPI + id)
            .then((response) => {setDPIData();})
            .catch(error => {alert("Error Ocurred in removeDPI:" + error);
            });
    }

    useEffect(() => {
        setDPIData();
    }, [idPatient]);

    if (DPIs.length === 0)
        return (<div className="container text-center" >DPI vide</div>)
    return (
        <div className="container text-center">
            <h4 className=" mx-2">DPIs Lists</h4>
            <div className="row">
                <table className="table table-sm table-bordered table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Antecedent</th>
                        <th scope="col">Traitement</th>

                    </tr>
                    </thead>
                    <tbody className="table-group-divider">
                    {
                        DPIs.map( (DPI, index) => (
                            <tr key={index}>
                                <th scope="row">{DPI.idDPI}</th>
                                <td>{DPI.antecedent}</td>
                                <td>{DPI.traitement}</td>
                            </tr>


                        ))
                    }

                    </tbody>
                </table>
            </div>
        </div>
    );
};
