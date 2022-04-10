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
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;
import me.carlosaguilar.gepin.model.Tesista;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class TesistaDAO {

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

    
    public Tesista getTesistaById(Integer idTesista) throws HibernateException {
        Tesista tesista = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from Tesista te "
                   + "left join fetch te.investigador inv "
                    + "left join fetch te.proyectosInvestigacion pr "
            
                    + "where te.idTesista = :idTesista ");
            query.setParameter("idTesista", idTesista);
            tesista = (Tesista) query.uniqueResult();
        } finally {

            sesion.close();
        }
        return tesista;
    }

   
    public List<Tesista> getAllTesistas() throws HibernateException {
        List<Tesista> listTesistas = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Tesista> query = builder.createQuery(Tesista.class);
            Root<Tesista> rootTesista = query.from(Tesista.class);

           


//            Predicate predicate = null;
//
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicate = builder.and(
//                    builder.equal(relGrupo.get("grupoAcademico"), idGrupoAcademico)
//            );
//            predicates.add(predicate);
          

            query.select(rootTesista);
            Query<Tesista> q = sesion.createQuery(query);

            listTesistas = q.getResultList();
        } finally {
            sesion.close();
        }

        return listTesistas;
    }

    
    public void deleteTesista(Tesista tesista) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(tesista);
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

   
    public Integer saveTesista(Tesista tesista) throws HibernateException {
        try {
            iniciaOperacion();
           Integer idTesista= 0;
                   idTesista = (Integer) sesion.save(tesista);
            tx.commit();
             return idTesista;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
           
        }
    }

    
    public void updateTesista(Tesista tesista) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(tesista);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
