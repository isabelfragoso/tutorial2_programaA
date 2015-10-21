/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;
import java.nio.file.*;
import java.io.*;

//CIPHER

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows 7
 */
public class Programa {

       /**
    * Gerador da chave, imprimindo-a num ficheiro
     * @param keyfilepath
     * @throws java.io.IOException
    **/
    
    public static void genkey(String keyfilepath) throws IOException{
        try {
            System.out.println("\nCreating key for RC4 CPHR\n");
            Path pth = Paths.get(keyfilepath);
            //KeyGenerator generator = KeyGenerator.getInstance("RC4");
            //generator.init(128); //128 bit key
            //SecretKey key = generator.generateKey();
           byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
        0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

            SecretKeySpec key = new SecretKeySpec(keyBytes, "RC4");
            System.out.println("Chave gerada com sucesso! Ver "+keyfilepath+"");
            Files.write(pth,key.getEncoded());
            
            
        }
        catch(Exception ex){
            System.err.println("Error"+ ex);
        }
        
    }
    
    public static void cifra(int type,String keyfilepath, String infilepath, String outfilepath ) throws IOException, NoSuchAlgorithmException, InvalidKeyException{
    
        try{
            Path ifp=Paths.get(infilepath);
            Path kfp=Paths.get(keyfilepath);
            Path ofp=Paths.get(outfilepath);
            
            System.out.println("\nReading files...");
            byte[] keyfile= Files.readAllBytes(kfp);
            byte[] infile= Files.readAllBytes(ifp);
            
            SecretKey key = new SecretKeySpec(keyfile,"RC4");  //aula
                
            System.out.println("Encrypting...");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(type,key);    
            
            Files.write(ofp,cipher.doFinal(infile));
            
         
            
        }
        catch(Exception ex){
            System.err.println("Error"+ ex);
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
      
        Boolean fail=false;
        
        if(args.length==0)
        {
            fail=true;
        }else
        {
            switch (args[0]){
                case "-genkey":
                    if(args.length==2)
                    {
                        genkey(""+args[1]);
                    }
                else{
                            fail=true;
                            System.err.println("\n Error, missing argument!");
                        }   break;
                case "-enc":
                   if(args.length==4){
                try {
                    cifra(Cipher.DECRYPT_MODE,""+args[1],""+args[2],""+args[3]);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
                }
                   }
                   else {
                            fail=true;
                            System.err.println("\n Error, missing argument!");
                   }break;
           
                   
            }
            
        }
        
    }
    
}
