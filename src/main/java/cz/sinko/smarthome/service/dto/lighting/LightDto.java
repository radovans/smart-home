package cz.sinko.smarthome.service.dto.lighting;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author radovan.sinko@direct.cz
 */
@Data
@AllArgsConstructor
public class LightDto {

	@ApiModelProperty(value = "Details the state of the light, see the state table below for more details.")
	private LightStateDto lightStateDto;

	@ApiModelProperty(value = "A fixed name describing the type of light e.g. “Extended color light”.")
	private String type;

	@ApiModelProperty(value = "A unique, editable name given to the light.")
	private String name;

	@ApiModelProperty(value = "The hardware model of the light.")
	private String modelId;

	@ApiModelProperty(value = "Unique id of the device. The MAC address of the device with a unique endpoint id in the form: AA:BB:CC:DD:EE:FF:00:11-XX")
	private String uniqueId;

	@ApiModelProperty(value = "The manufacturer name.")
	private String manufacturerName;

	@ApiModelProperty(value = "Product name of devices")
	private String productName;

	@ApiModelProperty(value = "Unique ID of the luminaire the light is a part of in the format: AA:BB:CC:DD-XX-YY.  AA:BB:, ... represents the hex of the luminaireid, XX the lightsource position (incremental but may contain gaps) and YY the lightpoint position (index of light in luminaire group).  A gap in the lightpoint position indicates an incomplete luminaire (light search required to discover missing light points in this case).")
	private String luminaireUniqueId;

	@ApiModelProperty(value = "Current light supports streaming features")
	private Object streaming;

	@ApiModelProperty(value = "Indicates if a lamp can be used for entertainment streaming as renderer")
	private boolean renderer;

	@ApiModelProperty(value = "Indicates if a lamp can be used for entertainment streaming as a proxy node.")
	private boolean proxy;

	@ApiModelProperty("An identifier for the software version running on the light.")
	private String swVersion;

}
