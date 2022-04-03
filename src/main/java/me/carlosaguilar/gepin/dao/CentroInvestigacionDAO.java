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
import me.carlosaguilar.gepin.model.CentroInvestigacion;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class CentroInvestigacionDAO {

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
     * @param idCentroInvestigacion 
     * @return
     * @throws HibernateException 
     */
    public CentroInvestigacion getCentroInvestigacionById(Integer idCentroInvestigacion) throws HibernateException {
        CentroInvestigacion centroInvestigacion = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from CentroInvestigacion c "
                   
                    + "where c.idCentroInvestigacion = :idCentroInvestigacion ");
            query.setParameter("idCentroInvestigacion", idCentroInvestigacion);
            centroInvestigacion = (CentroInvestigacion)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return centroInvestigacion;
    }

    /**
     * Obtiene una lista de todos los centro de investigacion registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<CentroInvestigacion> getAllCentroInvestigacion() throws HibernateException {
        List<CentroInvestigacion> centroInvestigacions = null;

        try {
            iniciaOperacion();
            centroInvestigacions = sesion.createQuery("from CentroInvestigacion").list();
        } finally {
            sesion.close();
        }

        return centroInvestigacions;
    }

    /**
     * Elimina un centro de investigacion en la BBDD
     * @param centroInvestigacion 
     * @throws HibernateException 
     */
    public void deleteCentroInvestigacion(CentroInvestigacion centroInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(centroInvestigacion);
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

    /**
     * Guarda un objeto centroInvestigacion en la BBDD
     * @param centroInvestigacion 
     * @throws HibernateException 
     */
    public void saveCentroInvestigacion(CentroInvestigacion centroInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.save(centroInvestigacion);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    /**
     * Actualiza un centro de investigacion en la BBDD
     * @param centroInvestigacion 
     * @throws HibernateException 
     */
    public void updateUser(CentroInvestigacion centroInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(centroInvestigacion);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
