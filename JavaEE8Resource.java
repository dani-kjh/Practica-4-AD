package com.mycompany.restad.resources;

import BaseDatos.ModificacionyConsulta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 
 */
@Path("javaee8")
public class JavaEE8Resource {
    /**
     POST method to register a new image     
     * @param title    
     * @param description     
     * @param keywords         
     * @param author    
     * @param creator    
     * @param capt_date        
     * @return    
     */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(MediaType.TEXT_HTML)
    public String registerImage (@FormParam("title") String title,            
    @FormParam("description") String description,            
    @FormParam("keywords") String keywords,            
    @FormParam("author") String author,            
    @FormParam("creator") String creator,            
    @FormParam("capture") String capt_date) {
        ModificacionyConsulta connection = new ModificacionyConsulta();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");  
            LocalDateTime now = LocalDateTime.now();
            String filename = dtf.format(now);
            boolean error = connection.registrarImagen(title, description, keywords, author, creator, capt_date, filename);
            
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            
        } finally{
            connection.cerrarconexion();
        }
        String result = "La imagen se ha registrado correctamente";
        return result;
    }
    
    /**
     POST method to modify an existing image     
     * @param id
     * @param title    
     * @param description     
     * @param keywords         
     * @param author    
     * @param creator    
     * @param capt_date        
     * @return    
     */
    @POST
    @Path("modify")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
    @Produces(MediaType.TEXT_HTML)
    public String modifyImage (@FormParam("id") String id,            
    @FormParam("title") String title,             
    @FormParam("description") String description,            
    @FormParam("keywords") String keywords,            
    @FormParam("author") String author,            
    @FormParam("creator") String creator,            
    @FormParam("capture") String capt_date) {
        ModificacionyConsulta connection = new ModificacionyConsulta();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            int id_ = Integer.parseInt(id);
            boolean error = connection.updateimagen(title, description, keywords, author, capt_date, id_);
            
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            
        } finally{
            connection.cerrarconexion();
        }
        String result = "La imagen " + id + " se ha modificado correctamente"; //ir a listar???
        return result; 
    }
    
    /**
     POST method to register a new image     
     * @param id
     * @return    
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)    
    @Produces(MediaType.TEXT_HTML)
    public String deleteImage (@FormParam("id") String id) {
        ModificacionyConsulta connection = new ModificacionyConsulta();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");  
            LocalDateTime now = LocalDateTime.now();
            String filename = dtf.format(now);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            
        } finally{
            connection.cerrarconexion();
        }
        String result = "La imagen " + id + " se ha eliminado correctamente"; //ir a listar???
        return result;
    }

/**
 * GET method to list images
 * @return
 */
    @Path("list")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listImages () {
        ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.list();
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;
    }
/**
* GET method to search images by id
* @param id
* @return
*/
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByID (@PathParam("id") int id)  {
        System.out.println(id);
        ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.searchById(id);
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;
    }
/**
* GET method to search images by title
* @param title
* @return
*/
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByTitle (@PathParam("title") String title) {
        System.out.println(title);
        ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.search(title, "", "", "", "");
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;

    }
/**
* GET method to search images by creation date. DAte format should be yyyy-mm-dd
* @param date
* @return
*/
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByCreationDate (@PathParam("date") String date) {
        System.out.println(date);
        ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.search("", "", "", "", date);
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;

    }
/**
* GET method to search images by author
* @param author
* @return
*/
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByAuthor (@PathParam("author") String author) {
        System.out.println(author);
        ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.search("", "", author, "", "");
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;

    }
/**
* GET method to search images by keyword
* @param keywords
* @return
*/
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByKeywords (@PathParam("keywords") String keywords) {
        System.out.println(keywords);
         ModificacionyConsulta con = new ModificacionyConsulta();
        ResultSet rs = con.search("", "", "", keywords, "");
        String resultat = llistarImatges(rs);
        con.cerrarconexion();
        return resultat;

    }

    private String llistarImatges(ResultSet rs) {
        boolean primeraFila = true;
        String result = "";
        try {
            
            while(rs.next()){
                if(primeraFila){
                    primeraFila = false;
                    result += "";
                }
                result += "<ul>" 
                        + "<li> " + rs.getInt("id") + "</li>"
                        +"<li> Titulo:" + rs.getString("title") + "</li>"
                        + "<li> Fecha de captura:" + rs.getString("capture_date") + "</li>"
                        +"<li> Descripcion:" + rs.getString("description") + "</li>"
                        +"<li> Keywords:" + rs.getString("keywords") + "</li>"
                        +"<li> Autor:" + rs.getString("author") + "</li>"
                        + "</ul>";
                        
                       
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            result +=  "<form>"
                        + "<input type = 'button' value = 'Go back' onclick= 'history.back()' > "
                        + "</form>";
            return result;
        }
    }
    
    
    
}
