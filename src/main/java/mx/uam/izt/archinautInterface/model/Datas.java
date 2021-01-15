package mx.uam.izt.archinautInterface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Datas {
	private String idCommit;
	
	private String info;
}
