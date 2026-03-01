import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Ambulance from "./Ambulance";
import Navbar from "./Navbar";
import Patient from "./Patient";
import ParcoursPatient from "./ParcoursPatient";
import Parcours from "./Parcours";
import FileAttente from "./FileAttente";
import PatientSymptomes from "./PatientSymptomes";
import BoxMedicale from "./BoxMedicale";

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
                    <Route path="/api/box-medicale" element={<BoxMedicale />}/>
                    <Route path="/api/parcours/all" element={<Parcours />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};
