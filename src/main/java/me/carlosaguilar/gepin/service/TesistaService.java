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
import me.carlosaguilar.gepin.dao.InvestigadorDAO;
import me.carlosaguilar.gepin.dao.LineaInvestigacionDAO;
import me.carlosaguilar.gepin.dao.ProyectosInvestigacionDAO;

import me.carlosaguilar.gepin.dao.RelProyectosInvestigadorDAO;
import me.carlosaguilar.gepin.dao.TesistaDAO;
import me.carlosaguilar.gepin.model.Investigador;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;
import me.carlosaguilar.gepin.model.RelProyectoInvestigador;
import me.carlosaguilar.gepin.model.RelProyectoInvestigadorId;
import me.carlosaguilar.gepin.model.Tesista;

import org.hibernate.HibernateException;

/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class TesistaService implements Serializable {

    @Inject
    TesistaDAO tesistaDAO;
   
    @Inject
    InvestigadorDAO investigadorDAO;

    public List<Tesista> getAllTesistas() throws SystemException {
        try {

            List<Tesista> listaTesistas = new ArrayList<>();
            listaTesistas = tesistaDAO.getAllTesistas();

            return listaTesistas;
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener los tesistas");
        }

    }

    public void deleteTesista(Tesista tesista) {
        try {
            tesista = tesistaDAO.getTesistaById(tesista.getIdTesista());
            

            tesistaDAO.deleteTesista(tesista);
        } catch (Exception ex) {
            throw new BusinessException("Error al eliminar al tesista");
        }
    }

    public void saveTesista(Tesista tesista) {
        try {

            tesistaDAO.saveTesista(tesista);

          
        } catch (HibernateException ex) {
            throw new BusinessException("Error al guardar al tesista");

        }
    }

    public void updateTesista(Tesista tesista) {
        try {

         

            tesistaDAO.updateTesista(tesista);

        } catch (HibernateException ex) {
            throw new BusinessException("Error al actualizar el tesista ");
        }
    }

    public Tesista getTesistaById(Integer idTesista) throws SystemException {
        try {

            return tesistaDAO.getTesistaById(idTesista);
        } catch (HibernateException ex) {
            throw new SystemException("Error al obtener al tesista ");
        }
    }

}
