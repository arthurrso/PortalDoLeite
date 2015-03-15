package controllers;

import java.util.List;
import static play.data.Form.form;

import models.Usuario;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import dao.GenericDAO;
import dao.GenericDAOImpl;

public class LoginController extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();

	private static Form<Usuario> usuarioForm = form(Usuario.class)
			.bindFromRequest();

	@Transactional
	public static Result login() {

		Form<Usuario> form = usuarioForm.bindFromRequest();

		String email = form.field("email").value();
		String senha = form.field("senha").value();

		if (isDadosInvalidosLogin(email, senha)) {
			return ok(views.html.login.render(usuarioForm));
		} else {
			Usuario user = (Usuario) getDAO().findByAttributeName("Usuario",
					"email", email).get(0);
			session().clear();
			session("user", user.getEmail());
			return redirect(routes.Application.index());
		}
	}

	public static Result showLogin() {
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
		return ok(views.html.login.render(usuarioForm));
	}

	private static boolean isDadosInvalidosLogin(String email, String senha) {
		return usuarioForm.hasErrors() || !validarDadosLogin(email, senha);
	}

	private static boolean validarDadosLogin(String email, String senha) {
		List<Usuario> u = getDAO().findByAttributeName("Usuario", "email",
				email);
		if (u == null || u.isEmpty()) {
			return false;
		}
		if (!u.get(0).getSenha().equals(senha)) {
			return false;
		}
		return true;
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
}
