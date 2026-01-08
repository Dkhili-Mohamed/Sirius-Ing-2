import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";
import Sample from "./Sample";
import Ambulance from "./Ambulance";   // <-- import avec nouveau nom
import Navbar from "./Navbar";
import NotFound from "./NotFound";

export default function Router() {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/" element={<App />} />
                    <Route path="/sample" element={<Sample />} />
                    <Route path="/ambulance" element={<Ambulance />} />  {/* <-- route mise à jour */}
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </div>
        </BrowserRouter>
    );
};
