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
import java.util.Arrays;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.CentroInvestigacionDAO;
import me.carlosaguilar.gepin.dao.InvestigadorDAO;
import me.carlosaguilar.gepin.dao.LineaInvestigacionDAO;
import me.carlosaguilar.gepin.dao.RelCentroInvGrupoAcDAO;
import me.carlosaguilar.gepin.dao.RelGrupoAcLineaInvDAO;
import me.carlosaguilar.gepin.dao.RelInvestigadorLineaGrupoDAO;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.model.Investigador;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAc;
import me.carlosaguilar.gepin.model.RelGrupoAcLineaInv;
import me.carlosaguilar.gepin.model.RelInvestigadorLineaGrupo;
import me.carlosaguilar.gepin.model.RelInvestigadorLineaGrupoId;

import org.hibernate.HibernateException;

/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class InvestigadorService implements Serializable {

    @Inject
    InvestigadorDAO investigadorDAO;
    @Inject
    RelInvestigadorLineaGrupoDAO relInvestigadorLineaGrupoDAO;

    @Inject
    CentroInvestigacionDAO centroInvestigacionDAO;

    @Inject
    RelCentroInvGrupoAcDAO relCentroInvGrupoAcDAO;

    @Inject
    RelGrupoAcLineaInvDAO relGrupoAcLineaInvDAO;
    
    @Inject
    LineaInvestigacionDAO lineaInvestigacionDAO;

    /**
     * Metodo que invoca al DAO y permite obtener la lista de todos los
     * investigadores
     *
     * @return
     * @throws SystemException
     */
    public List<Investigador> getAllInvestigador() throws SystemException {
        try {

            List<Investigador> listaInvestigador = new ArrayList<>();
            listaInvestigador = investigadorDAO.getAllInvestigador();

            return listaInvestigador;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los investigadores");
        }

    }

    /**
     * Metodo que genera un multiselect que contiene los centros de
     * investigacion -> grupos academicos -> lineas de investigación
     *
     * @param idCentroInvestigacion
     * @return
     * @throws SystemException
     */
    public List<SelectItem> createMultiselectLineaInvestigacionByIdCentroInvestigacion(Integer idCentroInvestigacion) throws SystemException {
        try {
            List<SelectItem> categorias = new ArrayList<>();

            List<RelCentroInvGrupoAc> listaGrupoAcademico = relCentroInvGrupoAcDAO.getAllRelCentroInvGrupoAcByIdCentroInvestigacion(idCentroInvestigacion);
            SelectItemGroup grupoAcademico = new SelectItemGroup();
            if (!listaGrupoAcademico.isEmpty()) {
                for (RelCentroInvGrupoAc rel : listaGrupoAcademico) {
                    grupoAcademico = new SelectItemGroup(rel.getGrupoAcademico().getGrupoAcademico());

                    List<RelGrupoAcLineaInv> listaLineaInve = relGrupoAcLineaInvDAO.getAllRelRelGrupoAcLineaInvByIdGrupoAcademico(rel.getGrupoAcademico().getIdGrupoAcademico());
                    List<SelectItem> selectItemArray = new ArrayList<>();
                    SelectItem[] arrayItems = new SelectItem[listaLineaInve.size()];
                    for (RelGrupoAcLineaInv relLinea : listaLineaInve) {
                        //selectItemArray.add();
                        arrayItems[listaLineaInve.indexOf(relLinea)] = new SelectItem(relLinea.getLineaInvestigacion().getIdLineaInvestigacion(), relLinea.getLineaInvestigacion().getLineaInvestigacion());
                    }

                    grupoAcademico.setSelectItems(arrayItems);
                    categorias.add(grupoAcademico);
                }
            } else {
                
                SelectItemGroup g1 = new SelectItemGroup("No hay grupos academicos");
                g1.setSelectItems(new SelectItem[]{new SelectItem("0", "Seleccione otro centro de investigación", "", false, false, true)});

                categorias.add(g1);
            }

//                
//             List<SelectItem> cars;
//            SelectItemGroup g1 = new SelectItemGroup("German Cars");
//        g1.setSelectItems(new SelectItem[] {new SelectItem("BMW", "BMW"), new SelectItem("Mercedes", "Mercedes"), new SelectItem("Volkswagen", "Volkswagen")});
//         
//        SelectItemGroup g2 = new SelectItemGroup("American Cars");
//        g2.setSelectItems(new SelectItem[] {new SelectItem("Chrysler", "Chrysler"), new SelectItem("GM", "GM"), new SelectItem("Ford", "Ford")});
//         
//        cars = new ArrayList<SelectItem>();
//        cars.add(g1);
//        cars.add(g2);
            return categorias;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los investigadores");
        }

    }

    /**
     * Metodo que invoca al DAO para eliminar un investogador
     *
     * @param investigador
     *
     */
    public void deleteInvestigador(Investigador investigador) {
        try {
            investigador = investigadorDAO.getLineaInvestigacionById(investigador.getIdInvestigador());
            List<RelInvestigadorLineaGrupo> rel = new ArrayList<>(investigador.getRelInvestigadorLineaGrupos());

            for (RelInvestigadorLineaGrupo ac : rel) {
                relInvestigadorLineaGrupoDAO.deleteAllDataRel(ac);
            }

            investigadorDAO.deleteInvestigador(investigador);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar el investigador");
        }
    }

    /**
     * Metodo que invoca al DAO para guardar un investigador
     *
     * @param investigador
     * @param listaLineaInvestigacionSelected
     */
    public void saveInvestigador(Investigador investigador, List<String> listaLineaInvestigacionSelected) {
        try {

            Integer idInvestigador = investigadorDAO.saveInvestigador(investigador);

            if (investigador != null) {
                
                
                 List<LineaInvestigacion> listaLineaInvestigacion = new ArrayList<>();
                
                for (String selected: listaLineaInvestigacionSelected){
                    listaLineaInvestigacion.add(lineaInvestigacionDAO.getLineaInvestigacionById(Integer.valueOf(selected)));
                }
                
                for (LineaInvestigacion inv : listaLineaInvestigacion) {
                    RelInvestigadorLineaGrupo relInvestigadorLineaGrupo = new RelInvestigadorLineaGrupo();
                    RelInvestigadorLineaGrupoId relInvestigadorLineaGrupoId = new RelInvestigadorLineaGrupoId();
                    relInvestigadorLineaGrupoId.setIdLineaInvestigacion(inv.getIdLineaInvestigacion());
                    relInvestigadorLineaGrupoId.setIdInvestigador(idInvestigador);
                    relInvestigadorLineaGrupo.setId(relInvestigadorLineaGrupoId);
                    relInvestigadorLineaGrupoDAO.saveRelInvestigadorLinea(relInvestigadorLineaGrupo);
                }
            }

        } catch (HibernateException ex) {
            throw new BusinessException("Error al guardar el investigador ");

        }
    }

    /**
     * Metodo que invoca al DAO para actualizar un investigador
     *
     * @param investigador
     * @param listaLineaInvestigacionSelected
     */
    public void updateInvestigador(Investigador investigador, List<String> listaLineaInvestigacionSelected) {
        try {

            if (investigador != null) {

                List<RelInvestigadorLineaGrupo> rel = new ArrayList<>(investigador.getRelInvestigadorLineaGrupos());

                for (RelInvestigadorLineaGrupo ac : rel) {
                    relInvestigadorLineaGrupoDAO.deleteAllDataRel(ac);
                }

                
                
                List<LineaInvestigacion> listaLineaInvestigacion = new ArrayList<>();
                
                for (String selected: listaLineaInvestigacionSelected){
                    listaLineaInvestigacion.add(lineaInvestigacionDAO.getLineaInvestigacionById(Integer.valueOf(selected)));
                }
                
                
                for (LineaInvestigacion inv : listaLineaInvestigacion) {
                    RelInvestigadorLineaGrupo relInvestigadorLineaGrupo = new RelInvestigadorLineaGrupo();
                    RelInvestigadorLineaGrupoId relInvestigadorLineaGrupoId = new RelInvestigadorLineaGrupoId();
                    relInvestigadorLineaGrupoId.setIdLineaInvestigacion(inv.getIdLineaInvestigacion());
                    relInvestigadorLineaGrupoId.setIdInvestigador(investigador.getIdInvestigador());
                    relInvestigadorLineaGrupo.setId(relInvestigadorLineaGrupoId);
                    relInvestigadorLineaGrupo.setLineaInvestigacion(inv);
                    relInvestigadorLineaGrupo.setInvestigador(investigador);
                    relInvestigadorLineaGrupoDAO.saveRelInvestigadorLinea(relInvestigadorLineaGrupo);
                }
            }

            investigadorDAO.updateLineaInvestigacion(investigador);

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el investigador ");
        }
    }

    /**
     * Metodo que invoca al DAO y regresa un objeto de tipo Investigador
     *
     * @param idInvestigador
     * @return
     * @throws SystemException
     */
    public Investigador getInvestigadorById(Integer idInvestigador) throws SystemException {
        try {

            return investigadorDAO.getLineaInvestigacionById(idInvestigador);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener el investigador ");
        }
    }
    
     public List<SelectItem> getItemsAllInvestigadores() throws SystemException {
        try {
            investigadorDAO = new InvestigadorDAO();
            List<Investigador> listInvestigadors = investigadorDAO.getAllInvestigador();
            List<SelectItem> listItems = new ArrayList<>();
            listInvestigadors.forEach((ce) -> {
                listItems.add(new SelectItem(ce.getIdInvestigador(), ce.getNombreInvestigador() + " " + ce.getApPaternoInvestigador() +" "+ce.getApMaternoInvestigador()));
            });
            return listItems;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los items de investigadores");
        }
        
    }

}
