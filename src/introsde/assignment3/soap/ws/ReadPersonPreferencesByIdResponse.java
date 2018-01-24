
package introsde.assignment3.soap.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for readPersonPreferencesByIdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="readPersonPreferencesByIdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="personPreferences" type="{http://ws.soap.assignment3.introsde/}activity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readPersonPreferencesByIdResponse", propOrder = {
    "personPreferences"
})
public class ReadPersonPreferencesByIdResponse {

    protected Activity personPreferences;

    /**
     * Gets the value of the personPreferences property.
     * 
     * @return
     *     possible object is
     *     {@link Activity }
     *     
     */
    public Activity getPersonPreferences() {
        return personPreferences;
    }

    /**
     * Sets the value of the personPreferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link Activity }
     *     
     */
    public void setPersonPreferences(Activity value) {
        this.personPreferences = value;
    }

}
