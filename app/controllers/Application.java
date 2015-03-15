package controllers;

import java.util.List;

import models.Usuario;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import dao.GenericDAO;
import dao.GenericDAOImpl;

public class Application extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	
	@Transactional
	public static Result index() {
		if (session().get("user") == null) {
			return redirect(routes.LoginController.showLoginPage());
		}
		return ok(views.html.index.render(getUsuarioLogado()));
	}
    
	@Transactional
	protected static void salvaObjeto(Object obj) {
		dao.persist(obj);
		dao.merge(obj);
		dao.flush();
	}

	protected static GenericDAO getDAO() {
		return dao;
	}
	
	protected static Usuario getUsuarioLogado() {
		Usuario user = new Usuario();
		if (listaUsuariosLogados().size() > 0) {
			if (listaUsuariosLogados().get(0) != null) {
				user = (Usuario) dao.findByAttributeName("Usuario", "email",
						session().get("user")).get(0);
			}
		}
		return user;
	}
	
	private static List<Usuario> listaUsuariosLogados() {
		return dao.findByAttributeName("Usuario", "email", session().get("user"));
	}

}