import React from 'react';
import { Link } from "react-router-dom";
import './styles/NavBarCSS.css';

export default function Navbar() {
    return (
        <ul className="nav justify-content-center my-3">
            <li className="nav-item">
                <Link className="nav-link" to="/ambulance">Ambulance</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/patient">Patients</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/api/patient/file-attente-dto">File d'Attente</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/api/box-medicale">Box Médicale</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/api/parcours/all">Parcours</Link>
            </li>
        </ul>
    );
};
