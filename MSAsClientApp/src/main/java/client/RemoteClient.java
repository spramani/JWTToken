/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import token.GenerateToken;

/**
 *
 * @author shubh
 */
//@RegisterRestClient(baseUri = "http://localhost:8080/SimpleMicroProfileApp/rest/example")
//@RegisterRestClient(configKey = "myclient")
//@ClientHeaderParam(name = "Mr.Shubh", value = "Purchase-App")
@RegisterRestClient(configKey = "myclient")
@ClientHeaderParam(name = "Authorization",value = "{generateToken}")
public interface RemoteClient {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String get();

    
    default String generateToken(){
     String token = "Bearer " +GenerateToken.generateJWT() ;
        System.out.println("Token - " +token);
        return token;
        
    }
    
}
