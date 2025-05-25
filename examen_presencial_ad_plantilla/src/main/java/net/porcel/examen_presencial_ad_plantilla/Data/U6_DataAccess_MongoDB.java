package net.porcel.examen_presencial_ad_plantilla.Data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.unwind;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Indexes.ascending;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import java.util.ArrayList;
import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_MongoDB.Actor;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_MongoDB.Categoria;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_MongoDB.Film;
import org.bson.Document;
import org.bson.conversions.Bson;

/*
_id: 1
title: "ACADEMY DINOSAUR"
description: "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teach…"
releaseYear: "2005"
length: 86
category: Array (1)
    0: Object
        value: "Documentary"
        categoryId: 6
actor: Array (10)
    0: Object
        actorId: 1
        firstName: "PENELOPE"
        lastName: "GUINESS"
language: Object
    value: "English"
    languageId: 1
 */
public class U6_DataAccess_MongoDB {

    private static String url;
    private static String bbdd;
    private static String colection;

    public U6_DataAccess_MongoDB() {
        this.url = "mongodb://mporcel:e43125921r@daw.paucasesnovescifp.cat:27017/?authSource=admin";
        this.bbdd = "mporceldb";
        this.colection = "sakila";
    }

    // SELECT
    public static MongoCollection getConnection() throws PersException {
        MongoClient mongoClient = null;
        MongoDatabase mongoDatabase = null;
        MongoCollection<Document> mongoCollection = null;

        try {
            mongoClient = MongoClients.create(url); // Conectamos al servidor
            mongoDatabase = mongoClient.getDatabase(bbdd); // Obtenemos la bbdd
            mongoCollection = mongoDatabase.getCollection(colection); // obtenemos la colección
        } catch (Exception e) {
            throw new PersException("Error al conextarse al servidor: " + e.getMessage());
        }
        return mongoCollection;
    }

    public static ArrayList<Document> getDocumentsBSON() throws PersException {
        ArrayList<Document> documents = new ArrayList<>();
        MongoCollection<Document> mongoCollection = null;

        try {
            mongoCollection = getConnection();
            mongoCollection.find().into(documents);
        } catch (Exception e) {
            throw new PersException("getAllCategories: " + e.getMessage());
        } finally {
        }
        return documents;
    }

    public static List<Categoria> getCategoriesBSON() throws PersException {
        List<Categoria> categorias = new ArrayList<>(); // Lista de objetos Categoria
        List<Document> documents = new ArrayList<>(); // Lista de Document que nos devuelve MongoDB

        try {
            MongoCollection mongoCollection = getConnection();
            // Creamos un filtro para seleccionar solo los documentos que tengan una categoría con valor "Documentary"
            // Esto busca dentro del array "category" que haya al menos un objeto con ese valor
            Bson filter = Filters.eq("category.value", "valorMPCMODIFICAT");
            // Definimos una proyección para incluir solo los campos necesarios dentro del array category
            // Aquí incluimos tanto el valor como el ID de cada categoría
            Bson projection = Projections.fields(
                    Projections.include("category.value", "category.categoryId")
            );
            Bson sort = Sorts.descending("category.value");

            mongoCollection.find(filter)
                    .projection(projection)
                    .sort(sort)
                    .into(documents); // Añadimos todos los documentos a la lista de Document

            // Recorremos cada documento recuperado de MongoDB
            for (Document document : documents) { // por cada documento, creamos un POJO y lo añadimos a su lista
                // Extraemos el array "category" (es una lista de subdocumentos)
                List<Document> documentsObtained = (List<Document>) document.get("category");

                if (documentsObtained != null) {
                    // Recorremos cada subdocumento dentro del array "category"
                    for (Document cat : documentsObtained) {
                        Categoria categoria = new Categoria();
                        categoria.setId(cat.getInteger("categoryId")); // Obtenemos el campo categoryId del subdocumento y lo asignamos
                        categoria.setValor(cat.getString("value")); // Obtenemos el campo value del subdocumento y lo asignamos
                        categorias.add(categoria);
                    }
                }
            }
        } catch (Exception e) {
            throw new PersException("getCategory: " + e.getMessage());
        }
        return categorias; // devolvemos una lista de POJO
    }

    public static List<Categoria> getCategoriesAggre() throws PersException {
        List<Categoria> categorias = new ArrayList<>(); // Lista de objetos Categoria
        List<Document> documents = new ArrayList<>(); // Lista de Document que nos devuelve MongoDB

        try {
            MongoCollection mongoCollection = getConnection();

            mongoCollection.aggregate(List.of(
                    // 1. unwind: descompone el array "category" para que cada elemento sea un documento separado
                    unwind("$category"),
                    // 2. group: agrupamos por los campos "categoryId" y "value" dentro de "category"
                    // Esto permite eliminar categorías duplicadas que puedan estar en distintos documentos
                    group(new Document("categoryId", "$category.categoryId") //campo nuevo documento/BBDD
                            .append("value", "$category.value") //campo nuevo documento/BBDD
                    ),
                    // 3. sort: ordenamos alfabéticamente por el valor de la categoría (campo agrupado)
                    sort(ascending("_id.value"))
            )).into(documents);                     // Añadimos todos los documentos a la lista de Document

            for (Document document : documents) { // por cada documento, creamos un POJO y lo añadimos a su lista
                // group genera un documento con campo ID
                // Almacenamos en "cat" cada documento generado por group
                // {
                //  "_id": {
                //    "categoryId": 5,
                //    "value": "Documentary"
                //  }
                //}
                Document cat = (Document) document.get("_id");

                Categoria categoria = new Categoria();
                categoria.setId(cat.getInteger("categoryId")); // nombre que hemos indicado en group
                categoria.setValor(cat.getString("value"));
                categorias.add(categoria);
            }
        } catch (Exception e) {
            throw new PersException("getCategory: " + e.getMessage());
        }
        return categorias; // devolvemos una lista de POJO
    }

    public static List<Actor> getActorsAggre() throws PersException {
        List<Actor> actors = new ArrayList<>();
        List<Document> documents = new ArrayList<>();

        try {
            MongoCollection<Document> mongoCollection = getConnection();
            mongoCollection.aggregate(List.of(
                    unwind("$actor"),
                    group(new Document("actorId", "$actor.actorId")
                            .append("firstName", "$actor.firstName")
                            .append("lastName", "$actor.lastName")),
                    sort(ascending("_id.firstName"))
            )).into(documents);

            for (Document document : documents) {
                Document act = (Document) document.get("_id");

                Actor actor = new Actor();
                actor.setId(act.getInteger("actorId"));
                actor.setNom(act.getString("firstName"));
                actor.setLlinatge(act.getString("lastName"));
                actors.add(actor);
            }
        } catch (Exception e) {
            throw new PersException("getActor: " + e.getMessage());
        }
        return actors;
    }

    public static List<Film> getFilmsByActorAndCategory(Integer idActor, Integer idCategory) throws PersException {
        List<Document> documents = new ArrayList<>();
        List<Film> films = new ArrayList<>();

        try {
            MongoCollection<Document> mongoCollection = getConnection();
            //for (Document document : getDocuments()) {

            // Solo Categoria
            if (idActor == null && idCategory != null) {
                Bson filtro = Filters.eq("category.categoryId", idCategory);
                mongoCollection.find(filtro).into(documents);

                for (Document document : documents) {
                    Film film = new Film();
                    film.setId(document.getInteger("_id"));
                    film.setTitol(document.getString("title"));
                    film.setDescripcio(document.getString("description"));
                    film.setDurada(document.getInteger("length"));
                    films.add(film);
                }
            } // Solo Actor
            else if (idActor != null && idCategory == null) {
                // Filtro
                Bson filtro = Filters.eq("actor.actorId", idActor);
                mongoCollection.find(filtro).into(documents);

                for (Document document : documents) {
                    Film film = new Film();
                    film.setId(document.getInteger("_id"));
                    film.setTitol(document.getString("title"));
                    film.setDescripcio(document.getString("description"));
                    film.setDurada(document.getInteger("length"));
                    films.add(film);
                }
            } // Ambos
            else if (idActor != null && idCategory != null) {
                // Filtro
                Bson filtro = Filters.and(
                        Filters.eq("actor.actorId", idActor),
                        Filters.eq("category.categoryId", idCategory)
                );
                mongoCollection.find(filtro).into(documents);

                for (Document document : documents) {
                    Film film = new Film();
                    film.setId(document.getInteger("_id"));
                    film.setTitol(document.getString("title"));
                    film.setDescripcio(document.getString("description"));
                    film.setDurada(document.getInteger("length"));
                    films.add(film);
                }
            } // Ninguno
            else if (idActor == null && idCategory == null) {
                mongoCollection.find().into(documents);

                for (Document document : documents) {
                    Film film = new Film();
                    film.setId(document.getInteger("_id"));
                    film.setTitol(document.getString("title"));
                    film.setDescripcio(document.getString("description"));
                    film.setDurada(document.getInteger("length"));
                    films.add(film);
                }
            }
        } catch (Exception e) {
            throw new PersException("getFilmsByActorAndCategory: " + e.getMessage());
        }
        return films;
    }

    // INSERT
    public static void addCategory(Categoria categoria) throws PersException {
        List<Document> documents = new ArrayList();
        MongoCollection mcol = getConnection();

        // Construimos el subdocumento que representa un elemento del array "category"
        // NOTA: Los campos deben coincidir con la estructura esperada en MongoDB para categoría
        Document subDocument = new Document("categoryId", categoria.getId()) // Campo categoryId, igual al id del POJO
                .append("value", categoria.getValor());                             // Campo value, igual al valor del POJO

        // Creamos el documento principal que contiene el array "category" con un único elemento: el subdocumento creado arriba
        // En la base de datos, cada documento tendría una estructura con un campo "category" que es un array de documentos
        Document document = new Document("category", List.of(subDocument));

        documents.add(document);
        mcol.insertOne(document);
        mcol.insertMany(documents);
    }
    // UPDATE
    public static void updateCategoryById(Categoria categoria, String nouValor) throws PersException {
        MongoCollection mcol = getConnection();
        Bson filtro = Filters.eq("category.categoryId", categoria.getId());

        // Construimos la operación de actualización para modificar el campo "value" del subdocumento en el array "category"
        // Para actualizar un campo dentro de un array, usamos el operador positional '$' para indicar el elemento coincidente
        Bson update = Updates.combine(
                Updates.set("category.$.categoryId", categoria.getId()), //mantenemos el valor
                Updates.set("category.$.value", nouValor)
        );

        mcol.updateOne(filtro, update);
    }
    // DELETE
    public static void deleteCategory(Categoria categoria) throws PersException {
        MongoCollection mcol = getConnection();
        Bson filter = Filters.eq("category.categoryId", categoria.getId());
        mcol.deleteOne(filter);
    }
}
