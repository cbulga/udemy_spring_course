package com.xantrix.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.service.TerminalinoService;
import com.xantrix.webapp.service.TrasmissioniService;
import com.xantrix.webapp.utils.TerminalinoResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller useful to import data from a file about a "terminalino".
 * 
 * @author cristian
 */
@Controller
@RequestMapping("/trasmissioni")
@Slf4j
public class TrasmissioniController {

	@Autowired
	private TrasmissioniService trasmissioniService;
	@Autowired
	private TerminalinoService terminalinoService;

	@GetMapping(value = "/aggiungi")
	public String insTerminalino(Model model) {
		Terminalino terminalino = new Terminalino();
		model.addAttribute("Titolo", "Inserimento Nuovo Terminalino");
		model.addAttribute("newTerminalino", terminalino);
		return "insTerminalino";
	}

	@PostMapping(value = "/aggiungi")
	public String manageInsTerminalino(@Valid @ModelAttribute("newTerminalino") Terminalino terminalino, BindingResult bindingResult, Model model, HttpServletRequest request) {
		MultipartFile dataFile = terminalino.getDataFile();

		if (!bindingResult.hasErrors()) {
			boolean isFileOk = dataFile != null && !dataFile.isEmpty();
			model.addAttribute("isFileOk", isFileOk);

			if (isFileOk) {
				TerminalinoResult result = terminalinoService.processTerminalino(terminalino);
				log.error(result.toString());
				model.addAttribute("terminalinoResult", result);
			}
		}

		return "insTerminalino";
	}
}
