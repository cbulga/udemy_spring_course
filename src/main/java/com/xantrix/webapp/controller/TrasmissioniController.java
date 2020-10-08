package com.xantrix.webapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.xantrix.webapp.domain.Terminalino;
import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.service.TerminalinoService;
import com.xantrix.webapp.service.TrasmissioniService;
import com.xantrix.webapp.utils.TerminalinoResult;
import com.xantrix.webapp.views.TerminalinoPdfView;

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
	public RedirectView manageInsTerminalino(@Valid @ModelAttribute("newTerminalino") Terminalino terminalino, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redir) {
		MultipartFile dataFile = terminalino.getDataFile();
		String fileName = terminalino.getDataFile().getOriginalFilename();
		TerminalinoResult result = null;

		if (!bindingResult.hasErrors()) {
			boolean isFileOk = dataFile != null && !dataFile.isEmpty();
			model.addAttribute("isFileOk", isFileOk);

			if (isFileOk) {
				result = terminalinoService.processTerminalino(terminalino);
				log.error(result.toString());
				model.addAttribute("terminalinoResult", result);
			}
		}

		RedirectView redirectView = new RedirectView("/trasmissioni/cerca/" + fileName.substring(0, 5) + "/download",true);
	    redir.addFlashAttribute("result", result);
	    return redirectView;
	}

	// http://localhost:8080/alphashop/trasmissioni/cerca/42995
	@GetMapping(value = "/cerca/{filter}")
	public String findTrasmissioniByIdFilter(@PathVariable("filter") String idTerminale, Model model) {
		List<Trasmissioni> recordset = trasmissioniService.findTrasmissioniByIdTerminale(idTerminale);
		int trasmissioniCount = 0;

		if (recordset != null)
			trasmissioniCount = recordset.size();

		model.addAttribute("trasmissioniCount", trasmissioniCount);
		model.addAttribute("Titolo", String.format("Ricerca Trasmissioni per ID Terminale %s", idTerminale));
		model.addAttribute("Titolo2", "Risultati Ricerca " + idTerminale);
		model.addAttribute("Trasmissioni", recordset);
		model.addAttribute("idTerminale", idTerminale);

		return "trasmissioni";
	}

	// http://localhost:8080/alphashop/trasmissioni/cerca/42995/download
	@GetMapping(value = "/cerca/{filter}/download")
	public ModelAndView findTrasmissioniByIdFilterPdf(@PathVariable("filter") String idTerminale, Model model) {
		List<Trasmissioni> recordset = trasmissioniService.findTrasmissioniByIdTerminale(idTerminale);

		ModelAndView mav = new ModelAndView();
		mav.addObject("Trasmissioni", recordset);
		mav.addObject("result", model.asMap().get("result"));
		mav.setView(new TerminalinoPdfView(idTerminale + "_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".pdf"));

		return mav;
	}
}
