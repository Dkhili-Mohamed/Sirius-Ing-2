import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";
import Sample from "./Sample";
import Ambulance from "./Ambulance";
import Navbar from "./Navbar";
import NotFound from "./NotFound";
import Patient from "./Patient";
import ParcoursPatient from "./ParcoursPatient";
import Parcours from "./Parcours";

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/" element={<App />} />
                    <Route path="/sample" element={<Sample />} />
                    <Route path="/ambulance" element={<Ambulance />} />  {/* <-- route mise à jour */}
                    <Route path="*" element={<NotFound />} />
                    <Route path="/patient" element={<Patient />}/>
                    <Route path="/api/parcours/:idPatient" element={<ParcoursPatient />}/>
                    <Route path="/api/parcours/all" element={<Parcours />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};
