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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import me.carlosaguilar.gepin.model.LineaInvestigacion;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class LineaInvestigacionDAO {

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
     * Obtiene un registro de la linea de investigacion en la BBDD 
     * @param idLineaInvestigacion
     * @return
     * @throws HibernateException 
     */
    public LineaInvestigacion getLineaInvestigacionById(Integer idLineaInvestigacion) throws HibernateException {
        LineaInvestigacion lineaInvestigacion = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from LineaInvestigacion la "
                   +"left join fetch la.relGrupoAcLineaInvs rel "
            + "left join fetch rel.grupoAcademico ga "
            
                    + "where la.idLineaInvestigacion = :idLineaInvestigacion ");
            query.setParameter("idLineaInvestigacion", idLineaInvestigacion);
            lineaInvestigacion = (LineaInvestigacion)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return lineaInvestigacion;
    }

    /**
     * Obtiene una lista de todos las lineas de investigacion registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<LineaInvestigacion> getAllLineaInvestigacion() throws HibernateException {
        List<LineaInvestigacion> listLineaInvestigacion = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<LineaInvestigacion> query = builder.createQuery(LineaInvestigacion.class);
            Root<LineaInvestigacion> rootLinea = query.from(LineaInvestigacion.class);

           


//            Predicate predicate = null;
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicate = builder.and(
//                    builder.equal(relGrupo.get("grupoAcademico"), idGrupoAcademico)
//            );
//            predicates.add(predicate);
          

            query.select(rootLinea);
            Query<LineaInvestigacion> q = sesion.createQuery(query);

            listLineaInvestigacion = q.getResultList();
        } finally {
            sesion.close();
        }

        return listLineaInvestigacion;
    }

    /**
     * Elimina una linea de investigacion en la BBDD 
     * @param lineaInvestigacion
     * @throws HibernateException 
     */
    public void deleteLineaInvestigacion(LineaInvestigacion lineaInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(lineaInvestigacion);
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

    /**
     * Guarda un objeto grupoAcademico en la BBDD 
     * @param lineaInvestigacion
     * @return  
     * @throws HibernateException 
     */
    public Integer saveLineaInvestigacion(LineaInvestigacion lineaInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
           Integer idLineaInvestigacion= 0;
                   idLineaInvestigacion = (Integer) sesion.save(lineaInvestigacion);
            tx.commit();
             return idLineaInvestigacion;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
           
        }
    }

    /**
     * Actualiza una linea de investigacion en la BBDD 
     * @param lineaInvestigacion
     * @throws HibernateException 
     */
    public void updateLineaInvestigacion(LineaInvestigacion lineaInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(lineaInvestigacion);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
