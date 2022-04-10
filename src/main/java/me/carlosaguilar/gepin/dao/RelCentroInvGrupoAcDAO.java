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

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class RelCentroInvGrupoAcDAO {

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
     * Elimina un grupo academico en la BBDD 
     * @param relCentroInvGrupoAc
     * @throws HibernateException 
     */
    public void deleteAllDataRel(RelCentroInvGrupoAc relCentroInvGrupoAc) throws HibernateException {
        try {
           iniciaOperacion();
            Query query = sesion.createQuery("delete from RelCentroInvGrupoAc where "
                    + "grupoAcademico.idGrupoAcademico = :idGrupoAcademico");
            query.setParameter("idGrupoAcademico", relCentroInvGrupoAc.getGrupoAcademico().getIdGrupoAcademico());
            
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
     * Guarda un objeto grupoAcademico en la BBDD  
     * @param relCentroInvGrupoAc
     * @throws HibernateException 
     */
    public void saveRelCentroInvGrupoAc(RelCentroInvGrupoAc relCentroInvGrupoAc) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.save(relCentroInvGrupoAc);
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
     * @return
     * @throws HibernateException 
     */
    public List<RelCentroInvGrupoAc> getAllRelCentroInvGrupoAcByIdCentroInvestigacion(Integer idCentroInvestigacion) throws HibernateException {
        List<RelCentroInvGrupoAc> listRelCentroInvGrupoAc = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<RelCentroInvGrupoAc> query = builder.createQuery(RelCentroInvGrupoAc.class);
            Root<RelCentroInvGrupoAc> rootLinea = query.from(RelCentroInvGrupoAc.class);
            
            Join<RelCentroInvGrupoAc, CentroInvestigacion> relCentroInvestigacion = (Join) rootLinea.fetch("centroInvestigacion", JoinType.LEFT);
            Join<RelCentroInvGrupoAc, GrupoAcademico> relGrupoAcademico = (Join) rootLinea.fetch("grupoAcademico", JoinType.LEFT);

           


            Predicate predicate = null;

            List<Predicate> predicates = new ArrayList<>();

            predicate = builder.and(
                    builder.equal(relCentroInvestigacion.get("idCentroInvestigacion"), idCentroInvestigacion)
            );
            predicates.add(predicate);
          

            query.distinct(true).select(rootLinea).where(predicates.toArray(new Predicate[predicates.size()]));
            Query<RelCentroInvGrupoAc> q = sesion.createQuery(query);

            listRelCentroInvGrupoAc = q.getResultList();
        } finally {
            sesion.close();
        }

        return listRelCentroInvGrupoAc;
    }
   

   
}
