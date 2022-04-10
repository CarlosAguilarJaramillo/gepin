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
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
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
public class RelGrupoAcLineaInvDAO {

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
     * Elimina la relacion entre linea de investigacion y grupo academico 
     * @param relGrupoAcLineaInv
     * @throws HibernateException 
     */
    public void deleteAllDataRel(RelGrupoAcLineaInv relGrupoAcLineaInv) throws HibernateException {
        try {
           iniciaOperacion();
            Query query = sesion.createQuery("delete from RelGrupoAcLineaInv where "
                    + "lineaInvestigacion.idLineaInvestigacion = :idLineaInvestigacion");
            query.setParameter("idLineaInvestigacion", relGrupoAcLineaInv.getLineaInvestigacion().getIdLineaInvestigacion());
            
            query.executeUpdate();
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

    /**
     * Guarda un objeto RelGrupoAcLineaInv en la BBDD  
     * @param relGrupoAcLineaInv
     * @throws HibernateException 
     */
    public void saveRelCentroInvGrupoAc(RelGrupoAcLineaInv relGrupoAcLineaInv) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.save(relGrupoAcLineaInv);
            tx.commit();
            
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
            
        }
    }

    
     /**
     * Obtiene una lista de todos las relaciones entre centro de investigacion y grupo academico por el id del centro de investigacion en la BBDD
     * @param idGrupoAcademico
     * @return
     * @throws HibernateException 
     */
    public List<RelGrupoAcLineaInv> getAllRelRelGrupoAcLineaInvByIdGrupoAcademico(Integer idGrupoAcademico) throws HibernateException {
        List<RelGrupoAcLineaInv> listRelGrupoAcLineaInv = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<RelGrupoAcLineaInv> query = builder.createQuery(RelGrupoAcLineaInv.class);
            Root<RelGrupoAcLineaInv> rootGrupo = query.from(RelGrupoAcLineaInv.class);
            
            
            Join<RelGrupoAcLineaInv, GrupoAcademico> relGrupoAcademico = (Join) rootGrupo.fetch("grupoAcademico", JoinType.LEFT);
            Join<RelGrupoAcLineaInv, LineaInvestigacion> reLineaInvestigacio = (Join) rootGrupo.fetch("lineaInvestigacion", JoinType.LEFT);
           


            Predicate predicate = null;

            List<Predicate> predicates = new ArrayList<>();

            predicate = builder.and(
                    builder.equal(relGrupoAcademico.get("idGrupoAcademico"), idGrupoAcademico)
            );
            predicates.add(predicate);
          

            query.distinct(true).select(rootGrupo).where(predicates.toArray(new Predicate[predicates.size()]));
            Query<RelGrupoAcLineaInv> q = sesion.createQuery(query);

            listRelGrupoAcLineaInv = q.getResultList();
        } finally {
            sesion.close();
        }

        return listRelGrupoAcLineaInv;
    }
   

   
}
