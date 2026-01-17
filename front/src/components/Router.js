import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./App";
import Sample from "./Sample";
import Navbar from "./Navbar";
import NotFound from "./NotFound";
import Patient from "./Patient";
import ParcoursPatient from "./ParcoursPatient";
import Parcours from "./Parcours";
import DPI from "./DPI";
import MaladiePatient from "./MaladiePatient";

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/" element={<App />}/>
                    <Route path="/sample" element={<Sample />}/>
                    <Route path="/api/patient/all" element={<Patient />}/>
                    <Route path="/api/dpi/patient/:idPatient" element={<DPI />}/>
                    <Route path="*" element={<NotFound />}/>
                    <Route path="/api/parcours/:idPatient" element={<ParcoursPatient />}/>
                    <Route path="/api/parcours/all" element={<Parcours />}/>
                    <Route path="/api/maladie-patient/patient/:idPatient" element={<MaladiePatient />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};