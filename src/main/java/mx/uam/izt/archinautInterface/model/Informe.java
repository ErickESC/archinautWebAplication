package mx.uam.izt.archinautInterface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Informe {
	private String id;
	
	private String idCommit;
	
	private String fecha;
	
	private String Analysis;
}
