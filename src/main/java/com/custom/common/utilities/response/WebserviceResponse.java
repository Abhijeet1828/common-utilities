package com.custom.common.utilities.response;

import java.io.Serializable;

import com.custom.common.utilities.httpclient.HttpUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used to store the Http response from Remote API calls.
 * 
 * @implSpec This class is used in {@link HttpUtils} class.
 * 
 * @author Abhijeet
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebserviceResponse implements Serializable {

	private static final long serialVersionUID = -1032378580972155901L;

	private String response;

	private int httpStatus;

}
