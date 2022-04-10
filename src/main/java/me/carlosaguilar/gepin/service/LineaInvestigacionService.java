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

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.LineaInvestigacionDAO;
import me.carlosaguilar.gepin.dao.RelGrupoAcLineaInvDAO;
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.RelGrupoAcLineaInv;
import me.carlosaguilar.gepin.model.RelGrupoAcLineaInvId;


import org.hibernate.HibernateException;


/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class LineaInvestigacionService implements Serializable {
    @Inject
    LineaInvestigacionDAO lineaInvestigacionDAO;
    @Inject
    RelGrupoAcLineaInvDAO relGrupoAcLineaInvDAO;
    
    
    /**
     * Metodo que invoca al DAO y permite obtener la lista de todas las lineas de invetigacion
     * @return
     * @throws SystemException 
     */
    public List<LineaInvestigacion> getAllLineaInvestigacion() throws SystemException {
        try {
            
            List<LineaInvestigacion> listaLineaInvestigacion = new ArrayList<>();
            listaLineaInvestigacion = lineaInvestigacionDAO.getAllLineaInvestigacion();
            
            return listaLineaInvestigacion;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener as líneas de investigación");
        }

    }
    
   

    
    /**
     * Metodo que invoca al DAO para eliminar una linea de investigacion  
     * @param lineaInvestigacion
     **/
    public void deleteLineaInvestigacion(LineaInvestigacion lineaInvestigacion)  {
        try {
            lineaInvestigacion = lineaInvestigacionDAO.getLineaInvestigacionById(lineaInvestigacion.getIdLineaInvestigacion());
            List<RelGrupoAcLineaInv> rel = new ArrayList<>(lineaInvestigacion.getRelGrupoAcLineaInvs());
            
            for (RelGrupoAcLineaInv ac: rel){
                relGrupoAcLineaInvDAO.deleteAllDataRel(ac);
            }
            
            lineaInvestigacionDAO.deleteLineaInvestigacion(lineaInvestigacion);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar la línea de investigación");
        }
    }

    /**
     * Metodo que invoca al DAO para guardar una linea de investigacion 
     * @param lineaInvestigacion
     * @param listaGrupoAcademicos
     */
    public void saveLineaInvestigacion(LineaInvestigacion lineaInvestigacion, List<GrupoAcademico> listaGrupoAcademicos) {
        try {
           
           Integer idLineaInvestigacion = lineaInvestigacionDAO.saveLineaInvestigacion(lineaInvestigacion);
            
            
           
           if (lineaInvestigacion!= null){
               for (GrupoAcademico grupo: listaGrupoAcademicos){
                   RelGrupoAcLineaInv  relGrupoAcLineaInv= new RelGrupoAcLineaInv();
                   RelGrupoAcLineaInvId relGrupoAcLineaInvId = new RelGrupoAcLineaInvId();
                   relGrupoAcLineaInvId.setIdGrupoAcademico(grupo.getIdGrupoAcademico());
                   relGrupoAcLineaInvId.setIdLineaInvestigacion(lineaInvestigacion.getIdLineaInvestigacion());
                   relGrupoAcLineaInv.setId(relGrupoAcLineaInvId);
                   relGrupoAcLineaInvDAO.saveRelCentroInvGrupoAc(relGrupoAcLineaInv);
               }
           }

        } catch (HibernateException ex) {
           throw new BusinessException("Error al guardar la línea de investigación ");

        }
    }

    /**
     * Metodo que invoca al DAO para actualizar una linea de investigacion
     *
     * @param lineaInvestigacion
     * @param listaGrupoAcademicos
     */
    public void updateLineaInvestigacion(LineaInvestigacion lineaInvestigacion, List<GrupoAcademico> listaGrupoAcademicos) {
        try {
           
            if (lineaInvestigacion!= null){
                
                List<RelGrupoAcLineaInv> rel = new ArrayList<>(lineaInvestigacion.getRelGrupoAcLineaInvs());
            
            for (RelGrupoAcLineaInv ac: rel){
                relGrupoAcLineaInvDAO.deleteAllDataRel(ac);
            }
                
               for (GrupoAcademico grupo: listaGrupoAcademicos){
                   RelGrupoAcLineaInv  relGrupoAcLineaInv= new RelGrupoAcLineaInv();
                   RelGrupoAcLineaInvId relGrupoAcLineaInvId = new RelGrupoAcLineaInvId();
                   relGrupoAcLineaInvId.setIdGrupoAcademico(grupo.getIdGrupoAcademico());
                   relGrupoAcLineaInvId.setIdLineaInvestigacion(lineaInvestigacion.getIdLineaInvestigacion());
                   relGrupoAcLineaInv.setId(relGrupoAcLineaInvId);
                   relGrupoAcLineaInv.setGrupoAcademico(grupo);
                   relGrupoAcLineaInv.setLineaInvestigacion(lineaInvestigacion);
                   relGrupoAcLineaInvDAO.saveRelCentroInvGrupoAc(relGrupoAcLineaInv);
               }
           }
            
                lineaInvestigacionDAO.updateLineaInvestigacion(lineaInvestigacion);

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar la línea de investigación ");
        }
    }
    
   
    /**
     * Metodo que invoca al DAO y regresa un objeto de tipo LineaInvestigacion 
     * @param idLineaInvestigacion
     * @return
     * @throws SystemException 
     */
    public LineaInvestigacion getLineaInvestogacionById(Integer idLineaInvestigacion) throws SystemException {
        try {
            
            return lineaInvestigacionDAO.getLineaInvestigacionById(idLineaInvestigacion);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener la línea de investigación ");
        }
    }

}
