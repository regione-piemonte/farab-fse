/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.farmab.integration.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ClientPasswordCallback implements CallbackHandler {

	String userFarmacie;
	String passFarmacie;
	
	
	public String getUserFarmacie() {
		return userFarmacie;
	}


	public void setUserFarmacie(String userFarmacie) {
		this.userFarmacie = userFarmacie;
	}


	public String getPassFarmacie() {
		return passFarmacie;
	}


	public void setPassFarmacie(String passFarmacie) {
		this.passFarmacie = passFarmacie;
	}


	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		
		for (int i = 0; i < callbacks.length; i++)
		{
			WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];

 if(pc.getIdentifier().equals(userFarmacie)) {
				pc.setPassword(passFarmacie);
			}
		}
	}

}
