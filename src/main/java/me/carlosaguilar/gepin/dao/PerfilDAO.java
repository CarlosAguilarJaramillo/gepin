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
import me.carlosaguilar.gepin.model.Perfil;
import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author CarlosAguilar
 */
public class PerfilDAO {

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
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos: " + he.toString(), he);
    }

    /**
     * Obtiene una lista de todos los perfiles guardados en la base de datos
     * @return
     * @throws HibernateException 
     */
    public List<Perfil> getAllPerfil() throws HibernateException {
        List<Perfil> listaPerfil = null;

        try {
            iniciaOperacion();
            listaPerfil = sesion.createQuery("from Perfil").list();
        } finally {
            sesion.close();
        }

        return listaPerfil;
    }

    /**
     * Obtiene un perfil por su ientificador desde la BBDD
     * @param idPerfil
     * @return
     * @throws HibernateException 
     */
    public Perfil getPerfilById(Integer idPerfil) throws HibernateException {
        Perfil perfil = null;

        try {
            iniciaOperacion();
            perfil = (Perfil) sesion.get(Perfil.class, idPerfil);
        } finally {
            sesion.close();
        }
        return perfil;
    }

}
