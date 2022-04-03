/*
 * Copyright 2019 JoinFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.carlosaguilar.gepin.service;

import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.constantes.Constantes;

import me.carlosaguilar.gepin.dao.UsuarioDAO;
import me.carlosaguilar.gepin.model.Usuario;
import me.carlosaguilar.gepin.util.Utils;
import org.hibernate.HibernateException;

/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class UsuarioService implements Serializable {

    UsuarioDAO usuarioDAO;

    /**
     * Metdo que invoca al DAO para obtener el los datos del usuario que inicia
     * sesión
     *
     * @param usuario
     * @return
     */
    public Usuario obtenUsuariologin(Usuario usuario) {
        usuarioDAO = new UsuarioDAO();
        Usuario usuariotemp = new Usuario();
        usuariotemp = usuarioDAO.getUserByEmail(usuario.getIdUsuario());
        if (usuariotemp != null) {
            if (usuariotemp.getIdEstatus() == (Constantes.ESTATUS_USUARIO_INACTIVO)) {
                throw new BusinessException("El usuario " + usuariotemp.getIdUsuario() + " \"se encuentra inhabilitado\"");
            }

            if ((usuario.getPassword().equals(Utils.desencriptarPassword(usuariotemp.getPassword()))) && (usuariotemp.getIdEstatus() == Constantes.ESTATUS_USUARIO_ACTIVO)) {

                return usuariotemp;

            } else {

                throw new BusinessException("EL usuario o contraseña son incorrectos");

            }

        } else {
            throw new BusinessException("EL usuario o contraseña son incorrectos");
        }

    }

    /**
     * Metodo que invoca al DAO y permite obtener la lista de todos los
     * usuarios.
     *
     * @return
     * @throws SystemException
     */
    public List<Usuario> getAllUsers() throws SystemException {
        try {
            usuarioDAO = new UsuarioDAO();
            return usuarioDAO.getAllUsers();
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los usuarios");
        }

    }

    /**
     * Metodo que invoca al DAO para eliminar un usuario
     *
     * @param usuario
     *
     */
    public void deleteUser(Usuario usuario) {
        try {
            usuarioDAO = new UsuarioDAO();
            usuarioDAO.deleteUser(usuario);
        } catch (HibernateException ex) {
            throw new BusinessException("Error al eliminar el usuario ");
        }
    }

    /**
     * Metodo que invoca al DAO para guardar un usuario
     *
     * @param catusuario
     */
    public void saveUser(Usuario catusuario) {
        try {
            usuarioDAO = new UsuarioDAO();
//            catusuario.setUsrPassword(this.encriptarPassword(catusuario.getPassword()));
            usuarioDAO.saveUser(catusuario);

        } catch (HibernateException ex) {
            if (ex.getLocalizedMessage().contains("duplicate key")) {
                throw new BusinessException("El email <code>" + catusuario.getIdUsuario() + "</code> ya está registrado.", FacesMessage.SEVERITY_ERROR);
            } else {
                throw new BusinessException(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }

        }
    }

    /**
     * Metodo que invoca al DAO para actualizar un usuario
     *
     * @param usuario
     * @param changePassword
     */
    public void updateUser(Usuario usuario, Boolean changePassword) {
        try {
            usuarioDAO = new UsuarioDAO();
            if (changePassword) {
////                usuario.setPassword(this.encriptarPassword(usuario.getPassword()));
                usuarioDAO.updateUser(usuario);
            } else {
                usuarioDAO.updateUser(usuario);
            }

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el usuario ");
        }
    }

    /**
     * Metodo que invoca al DAO y regresa un objeto de tipo Catusuario
     *
     * @param email
     * @return
     * @throws SystemException
     */
    public Usuario getUserByEmail(String email) throws SystemException {
        try {
            usuarioDAO = new UsuarioDAO();
            return usuarioDAO.getUserByEmail(email);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el usuario ");
        }
    }

    /**
     * Metodo que permite generar los selectItems para el estatus del usuario
     *
     * @return
     */
    public List<SelectItem> getItemStatusForUser() {

        List<SelectItem> itemsEstatus = new ArrayList<>();
        SelectItem item = new SelectItem(Constantes.ESTATUS_USUARIO_ACTIVO, "Activo");
        itemsEstatus.add(item);
        item = new SelectItem(Constantes.ESTATUS_USUARIO_INACTIVO, "Inactivo");
        itemsEstatus.add(item);

        return itemsEstatus;
    }

}
