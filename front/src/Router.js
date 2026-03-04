import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Ambulance from "./modules/ambulance/views/Ambulance";
import Navbar from "./Navbar";
import Patient from "./modules/fileattente/views/Patient";
import ParcoursPatient from "./modules/parcours/views/ParcoursPatient";
import Parcours from "./modules/parcours/views/Parcours";
import FileAttente from "./modules/fileattente/components/FileAttente";
import PatientSymptomes from "./modules/fileattente/views/PatientSymptomes";
import DefinirParcours from "./modules/parcours/views/DefinirParcours";

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/ambulance" element={<Ambulance />} />  {/* <-- route mise à jour */}
                    <Route path="/patient" element={<Patient />}/>
                    <Route path="/api/patient/all" element={<Patient />}/>
                    <Route path="/api/parcours/:idPatient" element={<ParcoursPatient />}/>
                    <Route path="/api/patient-symptomes/patient/:id" element={<PatientSymptomes />}/>
                    <Route path="/api/patient/file-attente-dto" element={<FileAttente />}/>
                    <Route path="/api/parcours/all" element={<Parcours />}/>
                    <Route path="/api/type-acte-medical/:idPatient" element={<DefinirParcours />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};
