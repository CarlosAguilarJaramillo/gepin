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

import me.carlosaguilar.gepin.model.RelGrupoAcLineaInv;
import me.carlosaguilar.gepin.model.RelInvestigadorLineaGrupo;

import me.carlosaguilar.gepin.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CarlosAguilar
 */
public class RelInvestigadorLineaGrupoDAO {

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
     * Elimina la relacion entre investigador y linea de investigacion
     * @param relInvestigadorLineaGrupo
     * @throws HibernateException 
     */
    public void deleteAllDataRel(RelInvestigadorLineaGrupo relInvestigadorLineaGrupo) throws HibernateException {
        try {
           iniciaOperacion();
            Query query = sesion.createQuery("delete from RelInvestigadorLineaGrupo where "
                    + "investigador.idInvestigador = :idInvestigador");
            query.setParameter("idInvestigador", relInvestigadorLineaGrupo.getInvestigador().getIdInvestigador());
            
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
     * Guarda un objeto RelInvestigadorLineaGrupo en la BBDD  
     * @param relInvestigadorLineaGrupo
     * @throws HibernateException 
     */
    public void saveRelInvestigadorLinea(RelInvestigadorLineaGrupo relInvestigadorLineaGrupo) throws HibernateException {
        try {
            iniciaOperacion();
            sesion.save(relInvestigadorLineaGrupo);
            tx.commit();
            
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
            
        }
    }

   

   
}
