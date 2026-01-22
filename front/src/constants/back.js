// URLs pour Ambulance
export const LOCAL_HOST_AMBULANCE = 'http://172.31.253.74:8081/api/ambulance/';
export const GET_AMBULANCES = LOCAL_HOST_AMBULANCE + 'all';
export const UPDATE_AMBULANCE = LOCAL_HOST_AMBULANCE + 'update';

export const LOCAL_HOST = 'http://172.31.253.74:8081';

//Table Patients
//export const LOCAL_HOST = 'http://localhost:8081';
export const LOCAL_HOST_PATIENT = LOCAL_HOST + '/api/patient/';
export const GET_PATIENTS = LOCAL_HOST_PATIENT + 'all';
export const UPDATE_PATIENT = LOCAL_HOST_PATIENT + 'update';

export const PARCOURS_PATIENT = LOCAL_HOST + '/api/parcours/';
export const ALL_PATIENTS = LOCAL_HOST + '/api/parcours/all';
export const CHEMIN_SALLE = LOCAL_HOST + '/api/chemin/';
export const CHEMIN = LOCAL_HOST + '/api/parcours/chemin';
