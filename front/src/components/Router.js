import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navbar from "./Navbar";
import ParcoursPatient from "./ParcoursPatient";
import Parcours from "./Parcours";

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/api/parcours/:idPatient" element={<ParcoursPatient />}/>
                    <Route path="/api/parcours/all" element={<Parcours />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};