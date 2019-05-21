package portal.service;

import java.util.List;

import portal.entity.Zac;

public interface ZacService {
	public Zac saveZac(Zac zac);
	public Zac updateZac(Zac zac);
	public void deleteZac(Zac zac);
	public List<Zac> getAll();
}
