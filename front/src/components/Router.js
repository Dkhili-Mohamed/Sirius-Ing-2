import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./App";
import Sample from "./Sample";
import Navbar from "./Navbar";
import NotFound from "./NotFound";
import Patient from "./Patient";
import ParcoursPatient from "./ParcoursPatient";
import Parcours from "./Parcours";
import FileAttente from "./FileAttente";
import PatientSymptomes from "./PatientSymptomes";
import PatientModal from "./PatientModal";

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/" element={<App />}/>
                    <Route path="/sample" element={<Sample />}/>
                    <Route path="/api/patient/all" element={<Patient />}/>
                    <Route path="*" element={<NotFound />}/>
                    <Route path="/api/parcours/:idPatient" element={<ParcoursPatient />}/>
                    <Route path="/api/patient-symptomes/patient/:id" element={<PatientSymptomes />}/>
                    <Route path="/patient/file-attente" element={<FileAttente />}/>
                    <Route path="/file-attente" element={<FileAttente />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};