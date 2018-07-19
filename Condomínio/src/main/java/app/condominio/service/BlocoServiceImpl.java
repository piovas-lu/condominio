package app.condominio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.BlocoDao;
import app.condominio.domain.Bloco;
import app.condominio.domain.Condominio;

@Service
@Transactional
public class BlocoServiceImpl implements BlocoService {

	@Autowired
	private BlocoDao blocoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Bloco bloco) {
		bloco.setCondominio(usuarioService.lerLogado().getCondominio());
		blocoDao.save(bloco);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bloco ler(Long id) {
		return blocoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Bloco> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<Bloco>();
		}
		return condominio.getBlocos();
	}

	@Override
	public void editar(Bloco bloco) {
		blocoDao.save(bloco);
	}

	@Override
	public void excluir(Bloco bloco) {
		blocoDao.delete(bloco);
	}

	@Override
	public boolean haCondominio() {
		return usuarioService.lerLogado().getCondominio() != null;
	}
}
