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
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class ProyectosInvestigacionDAO {

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
     * @param idProyectoInvestigacion
     * @return
     * @throws HibernateException 
     */
    public ProyectosInvestigacion getProyectoInvestigacionById(Integer idProyectoInvestigacion) throws HibernateException {
        ProyectosInvestigacion proyectosInvestigacion = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from ProyectosInvestigacion pi "
                   +"left join fetch pi.relProyectoInvestigadors rel "
            + "left join fetch rel.investigador inv "
            
                    + "where pi.idProyectoInvestigacion = :idProyectoInvestigacion ");
            query.setParameter("idProyectoInvestigacion", idProyectoInvestigacion);
            proyectosInvestigacion = (ProyectosInvestigacion)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return proyectosInvestigacion;
    }
    
    public ProyectosInvestigacion getProyectoInvestigacionByIdSinRelacionInv(Integer idProyectoInvestigacion) throws HibernateException {
        ProyectosInvestigacion proyectosInvestigacion = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from ProyectosInvestigacion pi "
                   
            
                    + "where pi.idProyectoInvestigacion = :idProyectoInvestigacion ");
            query.setParameter("idProyectoInvestigacion", idProyectoInvestigacion);
            proyectosInvestigacion = (ProyectosInvestigacion)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return proyectosInvestigacion;
    }

    /**
     * Obtiene una lista de todos las lineas de investigacion registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<ProyectosInvestigacion> getAllProyectosInvestigacion() throws HibernateException {
        List<ProyectosInvestigacion> listProyectos = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<ProyectosInvestigacion> query = builder.createQuery(ProyectosInvestigacion.class);
            Root<ProyectosInvestigacion> rootProyectos = query.from(ProyectosInvestigacion.class);

           


//            Predicate predicate = null;
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicate = builder.and(
//                    builder.equal(relGrupo.get("grupoAcademico"), idGrupoAcademico)
//            );
//            predicates.add(predicate);
          

            query.select(rootProyectos);
            Query<ProyectosInvestigacion> q = sesion.createQuery(query);

            listProyectos = q.getResultList();
        } finally {
            sesion.close();
        }

        return listProyectos;
    }

    
    public void deleteProyectoInvestigacion(ProyectosInvestigacion proyectosInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(proyectosInvestigacion);
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

   
    public Integer saveProyectoInvestigacion(ProyectosInvestigacion proyectosInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
           Integer idProyecto= 0;
                   idProyecto = (Integer) sesion.save(proyectosInvestigacion);
            tx.commit();
             return idProyecto;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
           
        }
    }

    
    public void updateproyectosInvestigacion(ProyectosInvestigacion proyectosInvestigacion) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(proyectosInvestigacion);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
