package com.app.kowalski.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.user.exception.KowalskiUserNotFoundException;
import com.app.kowalski.util.HateoasLinksBuilder;

@RestController
@RequestMapping("/users")
public class KowalskiUserController {

	@Autowired
	HttpServletRequest request;
	private KowalskiUserService kowalskiUserService;

	@Autowired
	KowalskiUserController(KowalskiUserService kowalskiUserService) {
		this.kowalskiUserService = kowalskiUserService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserDTO>> getAllKowalskiUsers() {
		List<KowalskiUserDTO> kowalskiUsersDTO = this.kowalskiUserService.getKowalskiUsers();
		for (KowalskiUserDTO kowalskiUserDTO : kowalskiUsersDTO) {
			HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		}
		return new ResponseEntity<List<KowalskiUserDTO>>(kowalskiUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<KowalskiUserDTO> addKowalskiUser(@RequestBody KowalskiUserDTO kowalskiUserDTO) {
		KowalskiUserDTO kowalskiUsersDTO = this.kowalskiUserService.addKowaslkiUser(kowalskiUserDTO);
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUsersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<KowalskiUserDTO> getKowalskiUser(@RequestParam int userId) {
		KowalskiUserDTO kowalskiUserDTO;
		try {
			kowalskiUserDTO = this.kowalskiUserService.getKowalskiUser(userId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<KowalskiUserDTO> editKowalskiUser(@RequestParam int userId,
			@RequestBody KowalskiUserDTO kowalskiUserDTO) {
		kowalskiUserDTO.setkUserId(userId);
		try {
			kowalskiUserDTO = this.kowalskiUserService.editKowaslkiUser(kowalskiUserDTO);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		HateoasLinksBuilder.createHateoasForKowalskiUser(kowalskiUserDTO);
		return new ResponseEntity<KowalskiUserDTO>(kowalskiUserDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<KowalskiUserDTO> deleteKowalskiUser(@RequestParam int userId) {
		try {
			boolean ret = this.kowalskiUserService.deleteKowalskiUser(userId);
		} catch (KowalskiUserNotFoundException e) {
			return new ResponseEntity<KowalskiUserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<KowalskiUserDTO>(HttpStatus.OK);
	}
}
