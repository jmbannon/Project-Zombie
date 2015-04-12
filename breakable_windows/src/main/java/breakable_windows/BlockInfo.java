package breakable_windows;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockInfo {
	
	private Location location;
	private Material material;
	private Byte data;
	
	public BlockInfo(Location location, Material type, Byte data) {
		this.location = location;
		this.material = type;
		this.data = data;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public Byte getData() {
		return this.data;
	}
	
}
