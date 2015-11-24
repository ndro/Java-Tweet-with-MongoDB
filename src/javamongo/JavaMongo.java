/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javamongo;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rafi Ramadhan / 13512075
 * @author Hendro Triokta Brianto / 13512081
 */
public class JavaMongo {

    public static void main( String args[] ) {
        TweetMethod tweetMethod;
        String IP = "localhost";    // your server location
        int port = 27017;
        String database = "pat";    // your database
        Boolean run = true;
        
        // clear log
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); 

        // connect to database
        tweetMethod = new TweetMethod(IP, port, database);
        
        Scanner in = new Scanner(System.in);
        
        while(run){
            String input = in.nextLine();
            String[] split = input.split(" ");
            
            switch(split[0]){
                case "/register":
                    if(tweetMethod.getUser().equals("unknown")){
                        tweetMethod.register(split[1], split[2]);
                    } else {
                        System.out.println("Log out first");
                    }
                    break;
                case "/login":
                    if(tweetMethod.getUser().equals("unknown")){
                        tweetMethod.login(split[1], split[2]);
                    } else {
                        System.out.println("You're logged in");
                        System.out.println("Log out first");
                    }
                    break;
                case "/follow":
                    if(!tweetMethod.getUser().equals("unknown")){
                        tweetMethod.follow(split[1]);
                    } else {
                        System.out.println("Not allowed");
                        System.out.println("Log in first");
                    }
                    break;
                case "/expand_tweet":
                    if(!tweetMethod.getUser().equals("unknown")){
                        tweetMethod.expand_tweet(split[1]);
                    } else {
                        System.out.println("Not allowed");
                        System.out.println("Log in first");
                    }
                    break;
                case "/timeline":
                    if(!tweetMethod.getUser().equals("unknown")){
                        tweetMethod.timeline();
                    } else {
                        System.out.println("Not allowed");
                        System.out.println("Log in first");
                    }
                    break;
                case "/logout":
                    if(!tweetMethod.getUser().equals("unknown")){
                        tweetMethod.logout();
                    } else {
                        System.out.println("Not allowed");
                        System.out.println("Log in first");
                    }
                    break;
                case "/exit":
                        run = false;
                    break;
                default:
                    if(!tweetMethod.getUser().equals("unknown")){
                        tweetMethod.tweet(input);
                    } else {
                        System.out.println("Not allowed");
                        System.out.println("Log in first");
                    }

            }
        }
   }
    
}
