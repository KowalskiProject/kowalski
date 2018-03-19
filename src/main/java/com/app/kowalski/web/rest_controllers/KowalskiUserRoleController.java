package com.app.kowalski.web.rest_controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.kowalski.dto.KowalskiUserRoleDTO;
import com.app.kowalski.services.KowalskiUserRoleService;

@RestController
@RequestMapping("/roles")
//This annotation is needed due Apache Shiro
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class KowalskiUserRoleController {

	private KowalskiUserRoleService roleService;

	@Autowired
	public KowalskiUserRoleController(KowalskiUserRoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<KowalskiUserRoleDTO>> getAllKowalskiUserRoles() {
		return new ResponseEntity<List<KowalskiUserRoleDTO>>(this.roleService.getKowalskiUserRoles(), HttpStatus.OK);
	}

}
