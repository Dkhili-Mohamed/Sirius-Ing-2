import React from 'react';
import {Link} from "react-router-dom";

export default function Navbar(){
    return (
            <ul className="nav justify-content-center my-3">
                <li className="nav-item">
                    <Link className="nav-link" to="/">Home</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/sample">Sample</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/api/patient/all">Patients</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/patient/file-attente">File d'Attente</Link>
                </li>
                 <li className="nav-item">
                    <Link className="nav-link" to="/api/parcours/all">Parcours</Link>
                </li>
            </ul>
    );
};