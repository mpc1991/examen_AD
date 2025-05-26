/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.test_examen_presencial.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import java.util.ArrayList;
import java.util.List;
import net.porcel.test_examen_presencial.dto.Categoria;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
. 
Exercici 4
 * recuperi les categories de la col·lecció sakila de mongoDB. 
 * El resultat ha de ser una llista d'objectes Categoria. 
 */
public class ex3pc {
    private static String url;
    private static String bbdd;
    private static String colection;

    public ex3pc() {
        this.url = "mongodb://mporcel:e43125921r@daw.paucasesnovescifp.cat:27017/?authSource=admin";
        this.bbdd = "mporceldb";
        this.colection = "sakila";
    }
    public static MongoCollection getConnection() {
        MongoClient mongoClient = null;
        MongoDatabase mongoDatabase = null;
        MongoCollection<Document> mongoCollection = null;

        try {
            mongoClient = MongoClients.create(url); // Conectamos al servidor
            mongoDatabase = mongoClient.getDatabase(bbdd); // Obtenemos la bbdd
            mongoCollection = mongoDatabase.getCollection(colection); // obtenemos la colección
        } catch (Exception e) {
        }
        return mongoCollection;
    }
    
    public void getCategoria(){
        List<Document> documents = new ArrayList<>();
        List<Categoria> categorias = new ArrayList<>();
        
        MongoCollection mcol = getConnection();
        try {
            Bson projection = Projections.fields(
                    Projections.include("category"),
                    Projections.excludeId()
            );
            mcol.find().projection(projection).into(documents);
            
            for (Document document : documents) {
                List<Document> categoryList = (List<Document>) document.get("category");
                
                if (categoryList != null) {
                for (Document doc : categoryList) {
                    Categoria categoria = new Categoria();
                    categoria.setId(doc.getInteger("categoryId"));
                    categoria.setValue(doc.getString("value"));
                    categorias.add(categoria);
                }
                }
                
            }
            
            for (Categoria categoria: categorias) {
                System.out.println(categoria);
            }
        } finally {
        }
    }
    
}
