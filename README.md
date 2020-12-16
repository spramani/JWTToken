# JWTToken
creating our own tokens jwt tokens, public key and private key

Before doing anything we need to do some modification in resource application and client application

In resource application


3. Using your own key pair and code to generate jwt, encrypt it with private key and then using public key at the resource application

creating own public and private keys
First, it is necessary to generate a base key to be signed:

> openssl genrsa -out baseKey.pem


From the base key generate the PKCS#8 private key:

> openssl pkcs8 -topk8 -inform PEM -in baseKey.pem -out privateKey.pem -nocrypt


Using the private key you could generate a public (and distributable) key

> openssl rsa -in baseKey.pem -pubout -outform PEM -out publicKey.pem

At present they are stored in keys folder.

We gave our own classes in utilities folder in token packeage with two classes GenerateToken.java and MPJWTToken.java
To use these classes add following dependency in pom.xml

   <dependency>
      <groupId>io.vertx</groupId>
      <artifactId>vertx-auth-jwt</artifactId>
      <version>3.8.1</version>
    </dependency>
    

In GenerateToken class we have a static method generateToken() to be called. 

In GenerateToken instance of MPJWTToken is created and using using this instance populate your credentials like principal and groups.

// GenerateToken is a custom class written in the current application with a static method generateJWT. It uses private key to encript 	
//	the token. The private key with name privateKey.pem is stored in src/main/resources folder of client and resource application respectively
         
       
your rest client will be as follows

@RegisterRestClient(configKey = "myclient1")
public interface MyRestClient {
     @GET
      @ClientHeaderParam(name="authorization", value="{generateJWTToken}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get();
    
    default String generateJWTToken()
    {
        Config config = ConfigProvider.getConfig();
      
       // Token dynamically generated
         String token ="Bearer "+ GenerateToken.generateJWT();
         System.out.println("Token = "+token);
         return token;
    }
}
       
    
    5. In your resource application create payara-mp-jwt.properties file in other resources folder and in that file populate as follow
    accepted.issuer=https://server.example.com // This must be same as issuer mentioned in GenerateToken class
    
    put your public key also in the same folder.
    
    Clean and build both the application
    run both the applications
