package portal.service;

import java.util.List;


import portal.entity.Zacmap;

public interface ZacmapService {
	public Zacmap saveZacmap(Zacmap zacmap);
	public Zacmap updateZacmap(Zacmap zacmap);
	public void deleteZacmap(Zacmap zacmap);
	public List<Zacmap> getAll();
	public void deleteAll(List<Zacmap> zacmap);
}
