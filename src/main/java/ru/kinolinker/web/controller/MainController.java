package ru.kinolinker.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ru.kinolinker.web.service.PersonService;

@Controller
public class MainController {

	@Autowired
	PersonService personService;

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = { "/", "persons" }, method = RequestMethod.GET)
	public ModelAndView home(ModelAndView model, HttpServletRequest request) {

		String name = request.getParameter("name");

		model.addObject("name", name);
		model.setViewName("persons");

		return model;

	}

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public ModelAndView getMoviesPage(ModelAndView model, HttpServletRequest request) {

		model.setViewName("movies");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/get_image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@RequestParam("path") String path) throws IOException {
		InputStream in = null;
		File f = new File(path);

		in = new FileInputStream(f);

		return IOUtils.toByteArray(in);
	}

}
