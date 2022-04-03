
package mx.gob.edomex.icati.siicati.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para reporteWS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reporteWS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="APROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="BAJAS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="EDAYO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HAPROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="HBAJAS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="HINSCRITOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="HNOAPROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="INSCRITOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="MAPROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="MBAJAS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="MINSCRITOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="MNOAPROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="NOAPROBADOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="TOTALGRUPOS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reporteWS", propOrder = {
    "aprobados",
    "bajas",
    "edayo",
    "haprobados",
    "hbajas",
    "hinscritos",
    "hnoaprobados",
    "inscritos",
    "maprobados",
    "mbajas",
    "minscritos",
    "mnoaprobados",
    "noaprobados",
    "totalgrupos"
})
public class ReporteWS {

    @XmlElement(name = "APROBADOS")
    protected BigDecimal aprobados;
    @XmlElement(name = "BAJAS")
    protected BigDecimal bajas;
    @XmlElement(name = "EDAYO")
    protected String edayo;
    @XmlElement(name = "HAPROBADOS")
    protected BigDecimal haprobados;
    @XmlElement(name = "HBAJAS")
    protected BigDecimal hbajas;
    @XmlElement(name = "HINSCRITOS")
    protected BigDecimal hinscritos;
    @XmlElement(name = "HNOAPROBADOS")
    protected BigDecimal hnoaprobados;
    @XmlElement(name = "INSCRITOS")
    protected BigDecimal inscritos;
    @XmlElement(name = "MAPROBADOS")
    protected BigDecimal maprobados;
    @XmlElement(name = "MBAJAS")
    protected BigDecimal mbajas;
    @XmlElement(name = "MINSCRITOS")
    protected BigDecimal minscritos;
    @XmlElement(name = "MNOAPROBADOS")
    protected BigDecimal mnoaprobados;
    @XmlElement(name = "NOAPROBADOS")
    protected BigDecimal noaprobados;
    @XmlElement(name = "TOTALGRUPOS")
    protected BigDecimal totalgrupos;

    /**
     * Obtiene el valor de la propiedad aprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAPROBADOS() {
        return aprobados;
    }

    /**
     * Define el valor de la propiedad aprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAPROBADOS(BigDecimal value) {
        this.aprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad bajas.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBAJAS() {
        return bajas;
    }

    /**
     * Define el valor de la propiedad bajas.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBAJAS(BigDecimal value) {
        this.bajas = value;
    }

    /**
     * Obtiene el valor de la propiedad edayo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEDAYO() {
        return edayo;
    }

    /**
     * Define el valor de la propiedad edayo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEDAYO(String value) {
        this.edayo = value;
    }

    /**
     * Obtiene el valor de la propiedad haprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHAPROBADOS() {
        return haprobados;
    }

    /**
     * Define el valor de la propiedad haprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHAPROBADOS(BigDecimal value) {
        this.haprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad hbajas.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHBAJAS() {
        return hbajas;
    }

    /**
     * Define el valor de la propiedad hbajas.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHBAJAS(BigDecimal value) {
        this.hbajas = value;
    }

    /**
     * Obtiene el valor de la propiedad hinscritos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHINSCRITOS() {
        return hinscritos;
    }

    /**
     * Define el valor de la propiedad hinscritos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHINSCRITOS(BigDecimal value) {
        this.hinscritos = value;
    }

    /**
     * Obtiene el valor de la propiedad hnoaprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHNOAPROBADOS() {
        return hnoaprobados;
    }

    /**
     * Define el valor de la propiedad hnoaprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHNOAPROBADOS(BigDecimal value) {
        this.hnoaprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad inscritos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSCRITOS() {
        return inscritos;
    }

    /**
     * Define el valor de la propiedad inscritos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSCRITOS(BigDecimal value) {
        this.inscritos = value;
    }

    /**
     * Obtiene el valor de la propiedad maprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMAPROBADOS() {
        return maprobados;
    }

    /**
     * Define el valor de la propiedad maprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMAPROBADOS(BigDecimal value) {
        this.maprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad mbajas.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMBAJAS() {
        return mbajas;
    }

    /**
     * Define el valor de la propiedad mbajas.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMBAJAS(BigDecimal value) {
        this.mbajas = value;
    }

    /**
     * Obtiene el valor de la propiedad minscritos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMINSCRITOS() {
        return minscritos;
    }

    /**
     * Define el valor de la propiedad minscritos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMINSCRITOS(BigDecimal value) {
        this.minscritos = value;
    }

    /**
     * Obtiene el valor de la propiedad mnoaprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMNOAPROBADOS() {
        return mnoaprobados;
    }

    /**
     * Define el valor de la propiedad mnoaprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMNOAPROBADOS(BigDecimal value) {
        this.mnoaprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad noaprobados.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNOAPROBADOS() {
        return noaprobados;
    }

    /**
     * Define el valor de la propiedad noaprobados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNOAPROBADOS(BigDecimal value) {
        this.noaprobados = value;
    }

    /**
     * Obtiene el valor de la propiedad totalgrupos.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALGRUPOS() {
        return totalgrupos;
    }

    /**
     * Define el valor de la propiedad totalgrupos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALGRUPOS(BigDecimal value) {
        this.totalgrupos = value;
    }

}
