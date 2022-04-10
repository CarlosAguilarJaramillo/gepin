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
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.GrupoAcademicoDAO;
import me.carlosaguilar.gepin.dao.RelCentroInvGrupoAcDAO;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAc;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAcId;


import org.hibernate.HibernateException;


/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class GrupoAcademicoService implements Serializable {
    @Inject
    GrupoAcademicoDAO grupoAcademicoDAO;
    @Inject
    RelCentroInvGrupoAcDAO relCentroInvGrupoAcDAO;
    
    
    /**
     * Metodo que invoca al DAO y permite obtener la lista de todos los grupos academicos
     * @return
     * @throws SystemException 
     */
    public List<GrupoAcademico> getAllGruposAcademicos() throws SystemException {
        try {
            grupoAcademicoDAO = new GrupoAcademicoDAO();
            List<GrupoAcademico> listaGruposAcademicos = new ArrayList<>();
            listaGruposAcademicos = grupoAcademicoDAO.getAllGrupoAcademico();
            
            return listaGruposAcademicos;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los grupos académicos");
        }

    }
    
   

    
    /**
     * Metodo que invoca al DAO para eliminar un grupo academico
     * @param grupoAcademico  
     **/
    public void deleteGrupoAcademico(GrupoAcademico grupoAcademico)  {
        try {
            grupoAcademico = grupoAcademicoDAO.getGrupoAcademicoById(grupoAcademico.getIdGrupoAcademico());
            List<RelCentroInvGrupoAc> rel = new ArrayList<>(grupoAcademico.getRelCentroInvGrupoAcs());
            
            for (RelCentroInvGrupoAc ac: rel){
                relCentroInvGrupoAcDAO.deleteAllDataRel(ac);
            }
            
            grupoAcademicoDAO.deleteGrupoAcademico(grupoAcademico);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar el grupo academico");
        }
    }

    /**
     * Metodo que invoca al DAO para guardar un grupo academico  
     * @param grupoAcademico
     * @param listaCentrosInvestigacion
     */
    public void saveGrupoAcademico(GrupoAcademico grupoAcademico, List<CentroInvestigacion> listaCentrosInvestigacion) {
        try {
           
           Integer idGrupoAcademico = grupoAcademicoDAO.saveGrupoAcademico(grupoAcademico);
            
            
           
           if (grupoAcademico!= null){
               for (CentroInvestigacion centro: listaCentrosInvestigacion){
                   RelCentroInvGrupoAc  relCentroInvGrupoAc= new RelCentroInvGrupoAc();
                   RelCentroInvGrupoAcId relCentroInvGrupoAcId = new RelCentroInvGrupoAcId();
                   relCentroInvGrupoAcId.setIdCentroInvestigacion(centro.getIdCentroInvestigacion());
                   relCentroInvGrupoAcId.setIdGrupoAcademico(idGrupoAcademico);
                   relCentroInvGrupoAc.setId(relCentroInvGrupoAcId);
                   relCentroInvGrupoAcDAO.saveRelCentroInvGrupoAc(relCentroInvGrupoAc);
               }
           }

        } catch (HibernateException ex) {
           throw new BusinessException("Error al guardar el grupo académico ");

        }
    }

    /**
     * Metodo que invoca al DAO para actualizar un grupo académico
     * @param grupoAcademico 
     * @param listaCentrosInvestigacion 
     
     */
    public void updateGrupoAcademico(GrupoAcademico grupoAcademico, List<CentroInvestigacion> listaCentrosInvestigacion) {
        try {
           
            if (grupoAcademico!= null){
                
                List<RelCentroInvGrupoAc> rel = new ArrayList<>(grupoAcademico.getRelCentroInvGrupoAcs());
            
            for (RelCentroInvGrupoAc ac: rel){
                relCentroInvGrupoAcDAO.deleteAllDataRel(ac);
            }
                
               for (CentroInvestigacion centro: listaCentrosInvestigacion){
                   RelCentroInvGrupoAc  relCentroInvGrupoAc= new RelCentroInvGrupoAc();
                   RelCentroInvGrupoAcId relCentroInvGrupoAcId = new RelCentroInvGrupoAcId();
                   relCentroInvGrupoAcId.setIdCentroInvestigacion(centro.getIdCentroInvestigacion());
                   relCentroInvGrupoAcId.setIdGrupoAcademico(grupoAcademico.getIdGrupoAcademico());
                   relCentroInvGrupoAc.setId(relCentroInvGrupoAcId);
                   relCentroInvGrupoAc.setCentroInvestigacion(centro);
                   relCentroInvGrupoAc.setGrupoAcademico(grupoAcademico);
                   relCentroInvGrupoAcDAO.saveRelCentroInvGrupoAc(relCentroInvGrupoAc);
               }
           }
            
                grupoAcademicoDAO.updateGrupoAcademico(grupoAcademico);
            

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el grupo académico ");
        }
    }
    
   
    /**
     * Metodo que invoca al DAO y regresa un objeto de tipo GrupoAcademico
     * @param idGrupoAcademico 
     * @return
     * @throws SystemException 
     */
    public GrupoAcademico getGrupoAcademicoById(Integer idGrupoAcademico) throws SystemException {
        try {
            grupoAcademicoDAO = new GrupoAcademicoDAO();
            return grupoAcademicoDAO.getGrupoAcademicoById(idGrupoAcademico);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el grupo académico ");
        }
    }

}
