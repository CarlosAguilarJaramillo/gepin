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
package me.carlosaguilar.gepin.dao;

import java.util.List;
import me.carlosaguilar.gepin.model.Usuario;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class UsuarioDAO {

    private Session sesion;
    private Transaction tx;

    /**
     * Inicia una sesión y una transacción en la base de datos
     *
     * @throws HibernateException
     */
    private void iniciaOperacion() throws HibernateException {
        
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    /**
     * Permite controlar las excepciones con la base de datos
     *
     * @param he
     * @throws HibernateException
     */
    private void manejaExcepcion(Exception he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos: " + he.toString(), he);
    }

    /**
     * Obtiene un registro de usario realizando la busqueda por email
     * @param email
     * @return
     * @throws HibernateException 
     */
    public Usuario getUserByEmail(String email) throws HibernateException {
        Usuario usuario = new Usuario();
        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from Usuario c "
                    + "left join fetch c.perfil cp "
                    + "left join fetch c.centroInvestigacion ci "
                    + "where c.idUsuario = :email ");
            query.setParameter("email", email);
            usuario = (Usuario) query.uniqueResult();
        }catch(Exception ex){
             manejaExcepcion(ex);
            throw ex;
        } 
        finally {
              sesion.close();
             
           
        }
        return usuario;
    }

    /**
     * Obtiene una lista de todos los usuarios registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<Usuario> getAllUsers() throws HibernateException {
        List<Usuario> listacatusuario = null;

        try {
            iniciaOperacion();
            listacatusuario = sesion.createQuery("from Usuario c left join fetch c.perfil").list();
        } finally {
            sesion.close();
        }

        return listacatusuario;
    }

    /**
     * Elimina un usuario en la BBDD
     * @param usuario
     * @throws HibernateException 
     */
    public void deleteUser(Usuario usuario) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(usuario);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    /**
     * Guarda un objeto usuario en la BBDD
     * @param usuario
     * @throws HibernateException 
     */
    public void saveUser(Usuario usuario) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.save(usuario);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    /**
     * Actualiza un usuario en la BBDD
     * @param usuario
     * @throws HibernateException 
     */
    public void updateUser(Usuario usuario) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(usuario);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
