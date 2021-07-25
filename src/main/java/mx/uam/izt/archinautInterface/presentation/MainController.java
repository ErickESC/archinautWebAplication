package mx.uam.izt.archinautInterface.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controlador web de la capa de presentacion
 */


@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/graficas")
	public String graficas() {
		
		return "graficas";
	}

}
