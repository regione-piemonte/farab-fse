/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.business.dao.util;
/**
 * @generated
 */
public final class Constants {
	/**
	 * identificativo dell'applicativo.
	 */
	public static final String APPLICATION_CODE = "dmacontatti";

	/*PROTECTED REGION ID(R-1074371075) ENABLED START*/
	public static final Integer PAZIENTE_QUERY_LIMIT = 2;
	public static final String CODICE_APPLICAZIONE_WEBAPP_OSCURAMENTO  = "WA";
	public static final String CODICE_APPLICAZIONE_WEBAPP_NRE  = "WA_NRE";
	public static final String CODICE_APPLICAZIONE_WEBAPP  = "WEBAPP_DMA";
	public static final String CODICE_APPLICAZIONE_APISAN  = "APISAN";
	public static final String CODICE_APPLICAZIONE_BATCH = "BATCH";
	public static final String CODICE_APPLICAZIONE_BATCH_SMEDIAZIONE = "BATCH_SMEDIAZIONE";
	public static final String CODICE_APPLICAZIONE_FSE = "FSE";
	public static final String TOKEN_OPERAZIONE_FAKE = "1";
	public static final String TIPO_ESENZIONE  = "ESEN";
	public static final String RUOLO_CITTADINO = "CIT";
	/*PROTECTED REGION END*/

	public static final String IDENTIFICATIVO_ORGANIZZAZIONE_PIEMONTE = "010";


	public static final String VERTICALE_FSEDOC = "FSEDOC";

    /*
     * Elenco dei codici servizio
     */
	public static final String GENERA_OTP = "GEN_OTP";
	public static final String VERIFICA_OTP = "VER_OTP";
	public static final String GET_PREFERENZE = "GET_PREF";
	public static final String PUT_TERMS_PREFERENZE_CONTATTI = "PUT_TER_REF_CON";
	public static final String TOKEN = "TOKEN";
	/*
		Elenco codici applicativi verticali
	 */
	public static final String RITIRO_REFERTI = "RITIROREFERTI";


	//COSTANTI RELATIVE ALLA MAPPATURA ERRORI OBBLIGATORI/CONGRUENTI
	public static final String RICHIEDENTE = "Richiedente";
	public static final String APPLICAZIONE_CHIAMANTE = "ApplicazioneChiamante";
	public static final String UUID = "UUID";
	public static final String INDIRIZZO_IP = "Indirizzo IP";
	public static final String CODICE_FISCALE_RICHIEDENTE = "Codice Fiscale Richiedente";
	public static final String CODICE_FISCALE_PAZIENTE = "Codice Fiscale Paziente";
	public static final String CANALE = "Canale";
	public static final String CODICE_OTP = "Codice OTP";
	public static final String CONTATTO = "Contatto";
	public static final String LISTA_CONTATTI = "Lista Contatti";
	public static final String HASH = "HASH";


	public static final String RIC_SER = "RIC_SER";

	//Constanti rest
	public static final String POST_TOPIC = "POST_TOPIC";
	public static final String GET_SER = "GET_SER";
	public static final String GET_PREF = "GET_PREF";
	public static final String PUT_TERMS = "PUT_TERMS";
	public static final String PUT_CONTACTS = "PUT_CONTACTS";
	public static final String PUT_PREFERENCES = "PUT_PREFERENCES";


	public static final String CANALE_MAIL = "email";
	public static final String PREFISSO_PHONE = "0039";
	public static final String CONF_OTP_NOTIFICA_TAG = "OTP_NOTIFICA_TAG";
	public static final String CONF_OTP_NOTIFICA_OGGETTO_MAIL = "OTP_NOTIFICA_OGGETTO_MAIL";
	public static final String CONF_OTP_NOTIFICA_TESTO_MAIL = "OTP_NOTIFICA_TESTO_MAIL";
	public static final String CONF_OTP_NOTIFICA_TEMPLATE_MAIL = "OTP_NOTIFICA_TEMPLATE_MAIL";
	public static final String CONF_OTP_NOTIFICA_TEST_SMS = "OTP_NOTIFICA_TESTO_SMS";

	public static final String ERRORE_GENERICO_REST = "CC_ER_209";

	public static final String LANGUAGE_IT = "it_IT";

	//FormatDate
	public static final String PATTERN_DATE_yyyyMMddHHmmssSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_yyyyMMddHHmmssSSS2 = "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_yyyyMMddHHmmss2 = "yyyy/MM/dd HH:mm:ss";
	public static final String PATTERN_DATE_yyyyMMddHH = "yyyy-MM-dd";
	public static final String PATTERN_DATE_yyyyMMddHH2 = "yyyy/MM/dd";
	public static final String PATTERN_DATE_ddMMyyyyHHmmssSSS = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_ddMMyyyyHHmmssSSS2 = "dd/MM/yyyy'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_ddMMyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";
	public static final String PATTERN_DATE_ddMMyyHHmmss2 = "dd/MM/yyyy HH:mm:ss";
	public static final String PATTERN_DATE_ddMMyyyyHH = "dd-MM-yyyy";
	public static final String PATTERN_DATE_ddMMyyyyHH2 = "dd/MM/yyyy";

	public static final String VALIDITA_OTP = "VALIDITA_OTP";
	
	/*APPLICAZIONI CHIAMANTI*/
	public static final String SANSOL = "SANSOL";
    public static final String DMACMPA = "DMACMPA";
    public static final String WEBAPP_CM = "WEBAPP_CM";

	public static final int CORRETTA_LONGHEZZA_CF = 16;

	

}
