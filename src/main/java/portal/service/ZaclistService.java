package portal.service;

import java.util.List;

import portal.entity.Zaclist;

public interface ZaclistService {
	public Zaclist saveZaclist(Zaclist zaclist);
	public Zaclist updateZaclist(Zaclist zaclist);
	public void deleteZaclist(Zaclist zaclist);
	public List<Zaclist> getAll();
}
