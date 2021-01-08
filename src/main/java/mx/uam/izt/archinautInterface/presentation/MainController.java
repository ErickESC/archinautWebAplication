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
	
	@GetMapping("/graficasSCC")
	public String graficasSCC() {
		
		return "graficasSCC";
	}
	
	@GetMapping("/graficasDpnds")
	public String graficasDpnds() {
		
		return "graficasDpnds";
	}

}
