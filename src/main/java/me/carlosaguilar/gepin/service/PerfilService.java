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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.dao.PerfilDAO;
import me.carlosaguilar.gepin.model.Perfil;
import org.hibernate.HibernateException;

/**
 *
 * @author CarlosAguilar
 */
@Stateless
public class PerfilService implements Serializable {

    PerfilDAO perfilDAO;

   
    /**
     * Metodo que invoca al DAo para obtener una lista de los perfiles y los regresa como selecItem
     * @return
     * @throws SystemException 
     */
    public List<SelectItem> getItemsCatPerfil() throws SystemException {
        try{
        perfilDAO = new PerfilDAO();
       List<Perfil> listaPerfil= perfilDAO.getAllPerfil();
       List<SelectItem> itemPerfil= new ArrayList<>();
       for (Perfil perfil: listaPerfil){
           SelectItem item = new SelectItem(perfil.getIdPerfil(), perfil.getPerfil());
           itemPerfil.add(item);
       }
       return itemPerfil;
        
        }catch (HibernateException ex){
            throw new SystemException("Error al obtener la lista de perfiles");
        }

    }

 
    /**
     * Método que invoca al DAO para obtener el perfil por su identificador único
     * @param idPerfil
     * @return
     * @throws SystemException 
     */
     public Perfil getPerfilById(Integer idPerfil) throws SystemException{
        try{
           return perfilDAO.getPerfilById(idPerfil);
        }catch (HibernateException ex){
            throw new SystemException("Error al obtener el perfil ");
        }
    }
}
