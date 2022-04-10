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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAc;
import me.carlosaguilar.gepin.model.RelGrupoAcLineaInv;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class GrupoAcademicoDAO {

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
     * Obtiene un registro los grupos registrados en la BBDD
     * @param idGrupoAcademico 
     * @return
     * @throws HibernateException 
     */
    public GrupoAcademico getGrupoAcademicoById(Integer idGrupoAcademico) throws HibernateException {
        GrupoAcademico grupoAcademico = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from GrupoAcademico ga "
                   +"left join fetch ga.relCentroInvGrupoAcs rel "
            + "left join fetch rel.centroInvestigacion ci "
            
                    + "where ga.idGrupoAcademico = :idGrupoAcademico ");
            query.setParameter("idGrupoAcademico", idGrupoAcademico);
            grupoAcademico = (GrupoAcademico)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return grupoAcademico;
    }

    /**
     * Obtiene una lista de todos los grupos academicos registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<GrupoAcademico> getAllGrupoAcademico() throws HibernateException {
        List<GrupoAcademico> grupoAcademicos = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<GrupoAcademico> query = builder.createQuery(GrupoAcademico.class);
            Root<GrupoAcademico> rootGrupo = query.from(GrupoAcademico.class);
            
            
            // Join<GrupoAcademico, RelGrupoAcLineaInv> relGrupoAcLineaInv = (Join) rootGrupo.fetch("relGrupoAcLineaInvs", JoinType.LEFT);
           


//            Predicate predicate = null;
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicate = builder.and(
//                    builder.equal(relGrupo.get("grupoAcademico"), idGrupoAcademico)
//            );
//            predicates.add(predicate);
          

            query.select(rootGrupo);
            Query<GrupoAcademico> q = sesion.createQuery(query);

            grupoAcademicos = q.getResultList();
        } finally {
            sesion.close();
        }

        return grupoAcademicos;
    }

    /**
     * Elimina un grupo academico en la BBDD
     * @param grupoAcademico 
     * @throws HibernateException 
     */
    public void deleteGrupoAcademico(GrupoAcademico grupoAcademico) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(grupoAcademico);
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
     * @param grupoAcademico 
     * @return  
     * @throws HibernateException 
     */
    public Integer saveGrupoAcademico(GrupoAcademico grupoAcademico) throws HibernateException {
        try {
            iniciaOperacion();
           Integer idGrupoAcademico= 0;
                   idGrupoAcademico = (Integer) sesion.save(grupoAcademico);
            tx.commit();
             return idGrupoAcademico;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
           
        }
    }

    /**
     * Actualiza un grupoAcademico en la BBDD
     * @param grupoAcademico 
     * @throws HibernateException 
     */
    public void updateGrupoAcademico(GrupoAcademico grupoAcademico) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(grupoAcademico);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
