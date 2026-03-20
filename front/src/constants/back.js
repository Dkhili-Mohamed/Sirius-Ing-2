// URLs pour Ambulance
export const LOCAL_HOST_AMBULANCE = 'http://172.31.250.222:8081/api/ambulance/';
export const GET_AMBULANCES = LOCAL_HOST_AMBULANCE + 'all';
export const UPDATE_AMBULANCE = LOCAL_HOST_AMBULANCE + 'update';

//export const LOCAL_HOST = 'http://172.31.249.73:8081';

//Table Patients +
export const LOCAL_HOST = 'http://localhost:8081';
export const LOCAL_HOST_PATIENT = LOCAL_HOST + '/api/patient/';
export const GET_PATIENTS = LOCAL_HOST_PATIENT + 'all';
export const UPDATE_PATIENT = LOCAL_HOST_PATIENT + 'update';
export const GET_FILE_ATTENTE = LOCAL_HOST_PATIENT + 'file-attente';
export const FILE_ATTENTE_ROUTE = '/api/patient/file-attente';
export const PARCOURS_PATIENT = LOCAL_HOST + '/api/parcours/';
export const ALL_PATIENTS = LOCAL_HOST + '/api/parcours/all';
export const CHEMIN_SALLE = LOCAL_HOST + '/api/chemin/';
export const CHEMIN = LOCAL_HOST + '/api/parcours/chemin';
export const TYPE_ACTE_MEDICAL = LOCAL_HOST + '/api/type-acte-medical/';
export const INSERT_ACTE_MEDICAL = LOCAL_HOST + '/api/acte-medical/insert';
export const INSERT_PARCOURS = LOCAL_HOST + '/api/parcours/insert';
export const INSERT_SUIVRE = LOCAL_HOST + '/api/suivre/insert';
export const SYMPTOME_PATIENT = LOCAL_HOST + '/api/symptome/';
export const BOX_MEDICALE = LOCAL_HOST + '/api/box-medicale';




