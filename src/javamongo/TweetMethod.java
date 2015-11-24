/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javamongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.Date;
import java.util.UUID;
import org.bson.Document;

/**
 * @author Rafi Ramadhan / 13512075
 * @author Hendro Triokta Brianto / 13512081
 */
public class TweetMethod {
    
    private MongoClient mongoClient;
    private MongoDatabase db;
    private static String username = "unknown";
    private MongoCollection<Document> collection;
    private Document doc;
    
    public TweetMethod(String IP, int port, String database){
        // To connect to mongodb server
        mongoClient = new MongoClient( IP , port );
        
        // Now connect to your databases
        db = mongoClient.getDatabase(database);
    }
    
    public void register(String username, String password){
        if(!getUser(username)){
            collection = db.getCollection("users");
            doc = new Document("username", username)
                            .append("password", password);
            collection.insertOne(doc);
            
            System.out.println("Success");
            System.out.println("Your username : " + username);
        } else {
            System.out.println("Error");
            System.out.println("This username '" + username + "' is already exist");
        }
    }
    
    public void login(String username, String password){
        if(getUser(username)){
            System.out.println("Login success");
            this.username = username;
        } else {
            System.out.println("Login error");
        }
    }
    
    public void expand_tweet(String username){

        if(getUser(username)){
            collection = db.getCollection("userline");
            try ( // find all document
                    MongoCursor<Document> cursor = collection.find(eq("username", username)).iterator()) {
                    while (cursor.hasNext()) {
                        String tweet_id = cursor.next().get("tweet_id").toString();
                        MongoCollection<Document> timeline = db.getCollection("tweets");
                        try (
                            MongoCursor<Document> cursorTimeline = timeline.find(eq("tweet_id", tweet_id)).iterator()) {
                            while(cursorTimeline.hasNext()) {
                                System.out.println(cursorTimeline.next().get("body"));
                            }
                        }
                    }
                }
        } else {
            System.out.println("Username doesn't exist");
        }

    }
    
    public void timeline(){
        collection = db.getCollection("timeline");
        try ( // find all document
            MongoCursor<Document> cursor = collection.find(eq("username", username)).iterator()) {
            while (cursor.hasNext()) {
                String tweet_id = cursor.next().get("tweet_id").toString();
                MongoCollection<Document> timeline = db.getCollection("tweets");
                try (
                    MongoCursor<Document> cursorTimeline = timeline.find(eq("tweet_id", tweet_id)).iterator()) {
                    while(cursorTimeline.hasNext()) {
                        System.out.println(cursorTimeline.next().get("body"));
                    }
                }
            }
        }
    }
    
    public void tweet(String text){
        String uuid = UUID.randomUUID().toString();
//        UUID timeuuid = UUIDs.timeBased();
        
        // insert into tweets
        MongoCollection<Document> collectionTweets = db.getCollection("tweets");
        Document docTweets = new Document("tweet_id", uuid)
                            .append("username", username)
                            .append("body", text);
        collectionTweets.insertOne(docTweets);
        
        // insert into userline
        MongoCollection<Document> collectionUserline = db.getCollection("userline");
        Document docUserline = new Document("username", username)
                            .append("tweet_id", uuid);
        collectionUserline.insertOne(docUserline);
        
        // insert into timeline
        MongoCollection<Document> collectionTimeline = db.getCollection("timeline");
        Document docTimeline = new Document("username", username)
                            .append("tweet_id", uuid);
        collectionTimeline.insertOne(docTimeline);
        
        // insert to all follower's timeline
        MongoCollection<Document> collectionFollower = db.getCollection("followers");
        try ( // find all document
            MongoCursor<Document> cursor = collectionFollower.find(eq("username", username)).iterator()) {
            while (cursor.hasNext()) {
                    String follower = cursor.next().getString("followers");
                    MongoCollection<Document> followerTimeline = db.getCollection("timeline");
                    Document docFollower = new Document("username", follower)
                                        .append("tweet_id", uuid);
                    followerTimeline.insertOne(docFollower);
                }
            }
    }
    
    public void follow(String friend){
        Date date = new Date();
        
        if(getUser(friend)){
            // insert into friends
            MongoCollection<Document> collectionFriends = db.getCollection("friends");
            Document docFriend = new Document("username", username)
                                .append("friend", friend)
                                .append("since", date.getTime());
            collectionFriends.insertOne(docFriend);

            // insert into followers
            MongoCollection<Document> collectionFollowers = db.getCollection("followers");
            Document docFollowers = new Document("username", friend)
                                .append("followers", username)
                                .append("since", date.getTime());
            collectionFollowers.insertOne(docFollowers);
        } else {
            System.out.println("Username doesn't exist");
        }
    }
    
    public void logout(){
        System.out.println("Logout success !");
        System.out.println("bye");
        
        username = "unknown";
    }
    
    public String getUser(){
        return username;
    }
    
    public boolean getUser(String username){
        collection = db.getCollection("users");
        doc = collection.find(eq("username", username)).first();
        return (doc != null);
    }
}
