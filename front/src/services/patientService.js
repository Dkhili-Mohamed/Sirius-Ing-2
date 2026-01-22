import axios from "axios";
import { LOCAL_HOST_PATIENT, GET_PATIENTS, GET_FILE_ATTENTE } from '../constants/back';

export const patientService = {
    getAllPatients: async () => {
        const response = await axios.get(GET_PATIENTS);
        return response.data;
    },

    createPatient: async (patientData) => {
        const response = await axios.post(LOCAL_HOST_PATIENT, patientData);
        return response.data;
    },

    getFileAttente: async () => {
        const response = await axios.get(GET_FILE_ATTENTE);
        return response.data;
    }
};