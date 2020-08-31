package com.xantrix.webapp.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class about a file getting from a "terminalino".
 *  
 * @author cristian
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Terminalino implements Serializable {

	private static final long serialVersionUID = -2466392059785563352L;
	private MultipartFile dataFile;
}
