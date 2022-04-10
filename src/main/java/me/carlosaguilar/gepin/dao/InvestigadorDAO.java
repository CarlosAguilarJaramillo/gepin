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
import me.carlosaguilar.gepin.model.Investigador;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class InvestigadorDAO {

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
     * Obtiene un registro del investigador en la BBDD 
     * @param idInvestigador
     * @return
     * @throws HibernateException 
     */
    public Investigador getLineaInvestigacionById(Integer idInvestigador) throws HibernateException {
        Investigador investigador = null;

        try {
            iniciaOperacion();
            Query query = sesion.createQuery("from Investigador inv "
                   +"left join fetch inv.relInvestigadorLineaGrupos rel "
            + "left join fetch rel.lineaInvestigacion li "
            
                    + "where inv.idInvestigador = :idInvestigador ");
            query.setParameter("idInvestigador", idInvestigador);
            investigador = (Investigador)query.uniqueResult();
        } finally {

            sesion.close();
        }
        return investigador;
    }

    /**
     * Obtiene una lista de todos los investigadores registrados en la BBDD
     * @return
     * @throws HibernateException 
     */
    public List<Investigador> getAllInvestigador() throws HibernateException {
        List<Investigador> listInvestigador = null;

        try {
          iniciaOperacion();
            
            

            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Investigador> query = builder.createQuery(Investigador.class);
            Root<Investigador> rootInvestigador = query.from(Investigador.class);

           
          

            query.select(rootInvestigador);
            Query<Investigador> q = sesion.createQuery(query);

            listInvestigador = q.getResultList();
        } finally {
            sesion.close();
        }

        return listInvestigador;
    }

    /**
     * Elimina un investigador en la BBDD 
     * @param investigador
     * @throws HibernateException 
     */
    public void deleteInvestigador(Investigador investigador) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.delete(investigador);
            tx.commit();
        } catch (Exception he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            
            sesion.close();
        }
    }

    /**
     * Guarda un objeto investigador en la BBDD 
     * @param investigador
     * @return  
     * @throws HibernateException 
     */
    public Integer saveInvestigador(Investigador investigador) throws HibernateException {
        try {
            iniciaOperacion();
           Integer idInvestigador= 0;
                   idInvestigador = (Integer) sesion.save(investigador);
            tx.commit();
             return idInvestigador;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
           
        }
    }

    /**
     * Actualiza un investigador en la BBDD 
     * @param investigador
     * @throws HibernateException 
     */
    public void updateLineaInvestigacion(Investigador investigador) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.update(investigador);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

   
}
