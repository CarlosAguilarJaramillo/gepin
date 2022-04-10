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
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.InvestigadorDAO;
import me.carlosaguilar.gepin.dao.LineaInvestigacionDAO;
import me.carlosaguilar.gepin.dao.ProyectosInvestigacionDAO;

import me.carlosaguilar.gepin.dao.RelProyectosInvestigadorDAO;
import me.carlosaguilar.gepin.model.Investigador;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;
import me.carlosaguilar.gepin.model.RelProyectoInvestigador;
import me.carlosaguilar.gepin.model.RelProyectoInvestigadorId;


import org.hibernate.HibernateException;


/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class ProyectosInvestigacionService implements Serializable {
    @Inject
    ProyectosInvestigacionDAO proyectosInvestigacionDAO;
    @Inject
    RelProyectosInvestigadorDAO relProyectosInvestigadorDAO;
    @Inject
    LineaInvestigacionDAO lineaInvestigacionDAO;
   
    
    public List<ProyectosInvestigacion> getAllProyectosInvestigacion() throws SystemException {
        try {
            
            List<ProyectosInvestigacion> listaProyectosInvestigacion = new ArrayList<>();
            listaProyectosInvestigacion = proyectosInvestigacionDAO.getAllProyectosInvestigacion();
            
            return listaProyectosInvestigacion;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los proyectos");
        }

    }
    
   

    public void deleteProyectosInvestigacion(ProyectosInvestigacion proyectosInvestigacion)  {
        try {
            proyectosInvestigacion = proyectosInvestigacionDAO.getProyectoInvestigacionById(proyectosInvestigacion.getIdProyectoInvestigacion());
            List<RelProyectoInvestigador> rel = new ArrayList<>(proyectosInvestigacion.getRelProyectoInvestigadors());
            
            for (RelProyectoInvestigador ac: rel){
                relProyectosInvestigadorDAO.deleteAllDataRel(ac);
            }
            
            proyectosInvestigacionDAO.deleteProyectoInvestigacion(proyectosInvestigacion);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar el proyecto");
        }
    }

    
    public void saveProyectosInvestigacion(ProyectosInvestigacion proyectosInvestigacion, List<Investigador> listaInvestigadores, Integer idLineaInvestigacion) {
        try {
           
           Integer idProyectoInvestigacion = proyectosInvestigacionDAO.saveProyectoInvestigacion(proyectosInvestigacion);
            
            
           
           if (proyectosInvestigacion!= null){
               for (Investigador inv: listaInvestigadores){
                   RelProyectoInvestigador  relProyectoInvestigador= new RelProyectoInvestigador();
                   RelProyectoInvestigadorId relProyectoInvestigadorId = new RelProyectoInvestigadorId();
                   relProyectoInvestigadorId.setIdProyectoInvestigacion(proyectosInvestigacion.getIdProyectoInvestigacion());
                   relProyectoInvestigadorId.setIdInvestigador(inv.getIdInvestigador());
                   relProyectoInvestigador.setId(relProyectoInvestigadorId);
                   relProyectosInvestigadorDAO.saveRelProyectoInvestigador(relProyectoInvestigador);
               }
           }
           
           
           
              LineaInvestigacion linea = lineaInvestigacionDAO.getLineaInvestigacionById(Integer.valueOf(idLineaInvestigacion));
              if (linea!=null){
                  proyectosInvestigacion.setLineaInvestigacion(linea);
              }

        } catch (HibernateException ex) {
           throw new BusinessException("Error al guardar el proyecto de investigación ");

        }
    }

  
    public void updateProyectoInvestigacion(ProyectosInvestigacion proyectosInvestigacion, List<Investigador> listaInvestigadores, Integer idLineaInvestigacion) {
        try {
           
            if (proyectosInvestigacion!= null){
                
                List<RelProyectoInvestigador> rel = new ArrayList<>(proyectosInvestigacion.getRelProyectoInvestigadors());
                
            
            for (RelProyectoInvestigador ac: rel){
                relProyectosInvestigadorDAO.deleteAllDataRel(ac);
            }
                
               for (Investigador inv: listaInvestigadores){
                   RelProyectoInvestigador  relProyectoInvestigador= new RelProyectoInvestigador();
                   RelProyectoInvestigadorId relProyectoInvestigadorId = new RelProyectoInvestigadorId();
                   relProyectoInvestigadorId.setIdProyectoInvestigacion(proyectosInvestigacion.getIdProyectoInvestigacion());
                   relProyectoInvestigadorId.setIdInvestigador(inv.getIdInvestigador());
                   relProyectoInvestigador.setId(relProyectoInvestigadorId);
                   relProyectosInvestigadorDAO.saveRelProyectoInvestigador(relProyectoInvestigador);
               }
           }
            
            
            
       
             
            
             
             
                LineaInvestigacion linea = lineaInvestigacionDAO.getLineaInvestigacionById(Integer.valueOf(idLineaInvestigacion));
              if (linea!=null){
                  proyectosInvestigacion.setLineaInvestigacion(linea);
              }
                
                

            
            
            
                proyectosInvestigacionDAO.updateproyectosInvestigacion(proyectosInvestigacion);

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el proyecto ");
        }
    }
    
   
    
    public ProyectosInvestigacion getProyectosInvestigacionById(Integer idProyectoInvestigacion) throws SystemException {
        try {
            
            return proyectosInvestigacionDAO.getProyectoInvestigacionById(idProyectoInvestigacion);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el proyecto ");
        }
    }
    
     public ProyectosInvestigacion getProyectosInvestigacionByIdSinRelaciones(Integer idProyectoInvestigacion) throws SystemException {
        try {
            
            return proyectosInvestigacionDAO.getProyectoInvestigacionByIdSinRelacionInv(idProyectoInvestigacion);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el proyecto ");
        }
    }
    
    
     public List<SelectItem> getItemsAllProyectosInvestigacion() throws SystemException {
        try {
           
            List<ProyectosInvestigacion> listProyectosInvestigacions = proyectosInvestigacionDAO.getAllProyectosInvestigacion();
            List<SelectItem> listItems = new ArrayList<>();
            listProyectosInvestigacions.forEach((ce) -> {
                listItems.add(new SelectItem(ce.getIdProyectoInvestigacion(), ce.getTemaProyecto()));
            });
            return listItems;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los items de proyectos de investigación");
        }
        
    }

}
