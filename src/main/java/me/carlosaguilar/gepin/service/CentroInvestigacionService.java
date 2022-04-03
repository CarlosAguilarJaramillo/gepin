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
package me.carlosaguilar.gepin.service;

import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;

import java.util.List;
import javax.ejb.Stateless;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.CentroInvestigacionDAO;
import me.carlosaguilar.gepin.model.CentroInvestigacion;

import org.hibernate.HibernateException;


/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class CentroInvestigacionService implements Serializable {

    CentroInvestigacionDAO centroInvestigacionDAO;
    
    /**
     * Metodo que invoca al DAO y permite obtener la lista de todos los centro de investigcón.
     * @return
     * @throws SystemException 
     */
    public List<CentroInvestigacion> getAllCentrosInvestigacion() throws SystemException {
        try {
            centroInvestigacionDAO = new CentroInvestigacionDAO();
            return centroInvestigacionDAO.getAllCentroInvestigacion();
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los centro de investigación");
        }

    }

    
    /**
     * Metodo que invoca al DAO para eliminar un centro de investigación
     * @param centroInvestigacion 
     **/
    public void deleteCentroInvestigacion(CentroInvestigacion centroInvestigacion)  {
        try {
            centroInvestigacionDAO = new CentroInvestigacionDAO();
            centroInvestigacionDAO.deleteCentroInvestigacion(centroInvestigacion);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar el centro de investigación");
        }
    }

    /**
     * Metodo que invoca al DAO para guardar un centro de investigacion
     * @param centroInvestigacion  
     */
    public void saveCentroInvestigacion(CentroInvestigacion centroInvestigacion) {
        try {
            centroInvestigacionDAO = new CentroInvestigacionDAO();

            centroInvestigacionDAO.saveCentroInvestigacion(centroInvestigacion);

        } catch (HibernateException ex) {
           throw new BusinessException("Error al guardar el centro de investigacion ");

        }
    }

    /**
     * Metodo que invoca al DAO para actualizar un centro de investigacion
     * @param centroInvestigacion 
     
     */
    public void updateCentroInvestigacion(CentroInvestigacion centroInvestigacion) {
        try {
            centroInvestigacionDAO = new CentroInvestigacionDAO();
          
                centroInvestigacionDAO.updateUser(centroInvestigacion);
            

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el centro de investigación ");
        }
    }
    
   
    /**
     * Metodo que invoca al DAO y regresa un objeto de tipo CentroInvestigacion
     * @param idCentroInvestigacion 
     * @return
     * @throws SystemException 
     */
    public CentroInvestigacion getCentroInvestigacionById(Integer idCentroInvestigacion) throws SystemException {
        try {
            centroInvestigacionDAO = new CentroInvestigacionDAO();
            return centroInvestigacionDAO.getCentroInvestigacionById(idCentroInvestigacion);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el centro de investigación ");
        }
    }

}
