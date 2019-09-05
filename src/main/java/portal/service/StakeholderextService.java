package portal.service;

import java.util.List;

import portal.entity.Stakeholderext;

public interface StakeholderextService {
	public Stakeholderext save(Stakeholderext stakeholderext);
	public Stakeholderext update(Stakeholderext stakeholderext);
	public void delete(Stakeholderext stakeholderext);
	public List<Stakeholderext> getAll();
}
