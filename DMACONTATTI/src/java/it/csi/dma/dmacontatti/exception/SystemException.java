/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma.dmacontatti.exception;

/**
 * Eccezione scatenata da problemi di sistema
 */
public class SystemException extends Exception {

	private static final long serialVersionUID = -8088994138430216391L;

	protected Throwable throwable;

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable throwable) {
		super(message);
		this.throwable = throwable;
	}

	public Throwable getCause() {
		return throwable;
	}
}
